package com.youchat.common;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents a function that accepts one argument(biz args, scan key , scan size) and consumer.
 *
 * condition  the database table has primary key and increasing trend , so the scan key can used to scan;
 *
 * @param <T> the type of the input to the function  (biz args type)
 * @param <R> the type of the result of the function (biz result type)
 * @author  lien6o
 * @date 2020-11-05 16:25:17
 */
@FunctionalInterface
public interface DatabaseTableScanSupport<T, R> {
    /**
     * scan database table, by sql .
     *
     * eg.  select * from t_order where id > 0:
     * @param data biz args
     * @param lastId scan key last max id
     * @param scanSize scan size
     * @return result
     */
    List<R> scan(T data, Long lastId, Integer scanSize);

    /**
     * help organization {@link DatabaseTableScanSupport#scan} ,maintain 。
     * @param data biz args
     * @param keyMapper who is the scan key
     * @param scanSize scan size
     * @param consumer Performs this operation on the given result.
     */
    default void doScan(T data, Function<R, Long> keyMapper, Integer scanSize, Consumer<R> consumer) {
        if (scanSize < 0) {
            throw new IllegalArgumentException("you scan a lonely!");
        }
        Long lastId = 0L;
        List<R> result;
        do {
            result = scan(data, lastId, scanSize);
            if (CollectionUtil.isNotEmpty(result)) {
                lastId = keyMapper.apply(result.get(result.size() - 1));
                for (R r : result) {
                    consumer.accept(r);
                }
            }
        } while (CollectionUtil.size(result) == scanSize);
    }
}
