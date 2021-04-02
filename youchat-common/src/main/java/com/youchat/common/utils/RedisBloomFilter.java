package com.youchat.common.utils;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.DoubleMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.io.Serializable;
import java.math.RoundingMode;

import javax.annotation.Nullable;

public class RedisBloomFilter<T>{
    private final RedisBitmaps bits;
    private final int numHashFunctions;
    private final RedisBloomFilter.Strategy strategy;

    private RedisBloomFilter(RedisBitmaps bits, int numHashFunctions  , RedisBloomFilter.Strategy strategy) {
        Preconditions.checkArgument(numHashFunctions > 0, "numHashFunctions (%s) must be > 0", numHashFunctions);
        Preconditions.checkArgument(numHashFunctions <= 255, "numHashFunctions (%s) must be <= 255", numHashFunctions);
        this.bits = Preconditions.checkNotNull(bits);
        this.numHashFunctions = numHashFunctions;
        this.strategy = Preconditions.checkNotNull(strategy);
    }

//    public RedisBloomFilter<T> copy() {
//        return new RedisBloomFilter(this.bits.copy(), this.numHashFunctions, this.funnel, this.strategy);
//    }

    public boolean mightContain(String object) {
        return this.strategy.mightContain(object, this.numHashFunctions, this.bits);
    }


    @CanIgnoreReturnValue
    public boolean put(String object) {
        return this.strategy.put(object, this.numHashFunctions, this.bits);
    }

    public double expectedFpp() {
        return Math.pow((double)this.bits.bitCount() / (double)this.bitSize(), (double)this.numHashFunctions);
    }

    public long approximateElementCount() {
        long bitSize = this.bits.bitSize();
        long bitCount = this.bits.bitCount();
        double fractionOfBitsSet = (double)bitCount / (double)bitSize;
        return DoubleMath.roundToLong(-Math.log1p(-fractionOfBitsSet) * (double)bitSize / (double)this.numHashFunctions, RoundingMode.HALF_UP);
    }

    @VisibleForTesting
    long bitSize() {
        return this.bits.bitSize();
    }

    public boolean isCompatible(RedisBloomFilter<T> that) {
        Preconditions.checkNotNull(that);
        return this != that && this.numHashFunctions == that.numHashFunctions && this.bitSize() == that.bitSize() && this.strategy.equals(that.strategy);
    }

  //  public void putAll(RedisBloomFilter<T> that) {
  //      Preconditions.checkNotNull(that);
  //      Preconditions.checkArgument(this != that, "Cannot combine a BloomFilter with itself.");
  //      Preconditions.checkArgument(this.numHashFunctions == that.numHashFunctions, "BloomFilters must have the same number of hash functions (%s != %s)", this.numHashFunctions, that.numHashFunctions);
  //      Preconditions.checkArgument(this.bitSize() == that.bitSize(), "BloomFilters must have the same size underlying bit arrays (%s != %s)", this.bitSize(), that.bitSize());
  //      Preconditions.checkArgument(this.strategy.equals(that.strategy), "BloomFilters must have equal strategies (%s != %s)", this.strategy, that.strategy);
  //      Preconditions.checkArgument(this.funnel.equals(that.funnel), "BloomFilters must have equal funnels (%s != %s)", this.funnel, that.funnel);
  //      this.bits.putAll(that.bits);
  //  }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof RedisBloomFilter)) {
            return false;
        } else {
            RedisBloomFilter<?> that = (RedisBloomFilter)object;
            return this.numHashFunctions == that.numHashFunctions &&   this.bits.equals(that.bits) && this.strategy.equals(that.strategy);
        }
    }

    public int hashCode() {
        return Objects.hashCode(this.numHashFunctions,  this.strategy, this.bits);
    }

//    public static <T> Collector<T, ?, RedisBloomFilter<T>> toBloomFilter(Funnel<? super T> funnel, long expectedInsertions) {
//        return toBloomFilter(funnel, expectedInsertions, 0.03D);
//    }

//    public static <T> Collector<T, ?, RedisBloomFilter<T>> toBloomFilter(Funnel<? super T> funnel, long expectedInsertions, double fpp) {
//        Preconditions.checkNotNull(funnel);
//        Preconditions.checkArgument(expectedInsertions >= 0L, "Expected insertions (%s) must be >= 0", expectedInsertions);
//        Preconditions.checkArgument(fpp > 0.0D, "False positive probability (%s) must be > 0.0", fpp);
//        Preconditions.checkArgument(fpp < 1.0D, "False positive probability (%s) must be < 1.0", fpp);
//        return Collector.of(() -> {
//            return create(funnel, expectedInsertions, fpp);
//        }, BloomFilter::put, (bf1, bf2) -> {
//            bf1.putAll(bf2);
//            return bf1;
//        }, Characteristics.UNORDERED, Characteristics.CONCURRENT);
//    }

    public static <T> RedisBloomFilter<T> create(  int expectedInsertions, double fpp) {
        return create((long) expectedInsertions, fpp);
    }

     public static <T> RedisBloomFilter<T> create(  long expectedInsertions, double fpp) {
         return create(expectedInsertions, fpp, RedisBloomFilterStrategies.MURMUR128_MITZ_64);
     }

    @VisibleForTesting
    static <T> RedisBloomFilter<T> create(long expectedInsertions, double fpp, RedisBloomFilter.Strategy strategy) {
        Preconditions.checkArgument(expectedInsertions >= 0L, "Expected insertions (%s) must be >= 0", expectedInsertions);
        Preconditions.checkArgument(fpp > 0.0D, "False positive probability (%s) must be > 0.0", fpp);
        Preconditions.checkArgument(fpp < 1.0D, "False positive probability (%s) must be < 1.0", fpp);
        Preconditions.checkNotNull(strategy);
        if (expectedInsertions == 0L) {
            expectedInsertions = 1L;
        }

        long numBits = optimalNumOfBits(expectedInsertions, fpp);
        int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);

        try {
            return new RedisBloomFilter(new RedisBitmaps(numBits), numHashFunctions, strategy);
        } catch (IllegalArgumentException var10) {
            throw new IllegalArgumentException("Could not create BloomFilter of " + numBits + " bits", var10);
        }
    }

    public static <T> RedisBloomFilter<T> create(int expectedInsertions) {
        return create((long) expectedInsertions);
    }

    public static <T> RedisBloomFilter<T> create( long expectedInsertions) {
        return create(expectedInsertions, 0.03D);
    }

    @VisibleForTesting
    static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int)Math.round((double)m / (double)n * Math.log(2.0D)));
    }

    @VisibleForTesting
    static long optimalNumOfBits(long n, double p) {
        if (p == 0.0D) {
            p = 4.9E-324D;
        }

        return (long)((double)(-n) * Math.log(p) / (Math.log(2.0D) * Math.log(2.0D)));
    }

    public void resetBitmap() {
        bits.reset();
    }

    //  private Object writeReplace() {
 //      return new RedisBloomFilter.SerialForm(this);
 //  }

 //  public void writeTo(OutputStream out) throws IOException {
 //      DataOutputStream dout = new DataOutputStream(out);
 //      dout.writeByte(SignedBytes.checkedCast((long)this.strategy.ordinal()));
 //      dout.writeByte(UnsignedBytes.checkedCast((long)this.numHashFunctions));
 //      dout.writeInt(this.bits.data.length());

 //      for(int i = 0; i < this.bits.data.length(); ++i) {
 //          dout.writeLong(this.bits.data.get(i));
 //      }

 //  }

  // public static <T> RedisBloomFilter<T> readFrom(InputStream in, Funnel<? super T> funnel) throws IOException {
  //     Preconditions.checkNotNull(in, "InputStream");
  //     Preconditions.checkNotNull(funnel, "Funnel");
  //     int strategyOrdinal = -1;
  //     int numHashFunctions = -1;
  //     byte dataLength = -1;

  //     try {
  //         DataInputStream din = new DataInputStream(in);
  //         strategyOrdinal = din.readByte();
  //         int numHashFunctions = UnsignedBytes.toInt(din.readByte());
  //         int dataLength = din.readInt();
  //         BloomFilter.Strategy strategy = RedisBloomFilterStrategies.values()[strategyOrdinal];
  //         long[] data = new long[dataLength];

  //         for(int i = 0; i < data.length; ++i) {
  //             data[i] = din.readLong();
  //         }

  //         return new RedisBloomFilter(new RedisBitmaps(data), numHashFunctions, funnel, strategy);
  //     } catch (RuntimeException var9) {
  //         String message = "Unable to deserialize BloomFilter from InputStream. strategyOrdinal: " + strategyOrdinal + " numHashFunctions: " + numHashFunctions + " dataLength: " + dataLength;
  //         throw new IOException(message, var9);
  //     }
  // }

  //  private static class SerialForm<T> implements Serializable {
  //      final long[] data;
  //      final int numHashFunctions;
  //      final Funnel<? super T> funnel;
  //      final RedisBloomFilter.Strategy strategy;
  //      private static final long serialVersionUID = 1L;
//
  //      SerialForm(RedisBloomFilter<T> bf) {
  //          this.data = RedisBitmaps.toPlainArray(bf.bits.data);
  //          this.numHashFunctions = bf.numHashFunctions;
  //          this.funnel = bf.funnel;
  //          this.strategy = bf.strategy;
  //      }
//
  //      Object readResolve() {
  //          return new RedisBloomFilter(new RedisBitmaps(this.data), this.numHashFunctions, this.funnel, this.strategy);
  //      }
  //  }

    interface Strategy extends Serializable {

        boolean put(String string, int numHashFunctions, RedisBitmaps bits);

        boolean mightContain(String object, int numHashFunctions, RedisBitmaps bits);

        }
}
