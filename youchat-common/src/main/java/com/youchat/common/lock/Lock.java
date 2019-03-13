package com.youchat.common.lock;

/**
 * @program: youchat-common
 * @description:
 * @author: lien6o
 * @create: 2018-08-16 20:24
 **/
public class Lock  {

    /**
     * redis 锁是有问题的。etcd ！
     * @param redisKey
     * @param lockCallBack
     * @param <V>
     * @return
     */
    public static<V> V acquireLock(String redisKey, LockCallBack<V> lockCallBack) {
        // long rLock = WRedisUtil.simpleRLock(redisKey, 5);
        long rLock = System.currentTimeMillis();
        if (rLock == 0L) {
            return lockCallBack.onFailure( );
        }
        try {
            return lockCallBack.onSuccess( );
        } finally {
            // WRedisUtil.simpleRLockDel(redisKey, rLock);
        }
    }

//    private K param;
//
//    /**
//     * 创建锁并尝试获取锁
//     *
//     * @param lockName
//     *            锁名称
//     * @param acquireTime
//     *            锁时间
//     * @param lockCallBack
//     *            回调
//     * @return
//     */
//    public V createLockAndGetLock(String lockName, int acquireTime, LockCallBack<K, V> lockCallBack) {
//        if (lockName == null || lockCallBack == null) {
//            throw new NullPointerException();
//         }
////        try (DisLock lock = DisLockSingleton.getInstance().newLock(lockName)) {
////               if (lock.acquire(acquireTime <= 0 ? 5 : acquireTime)) {
////                   return lockCallBack.onSuccess(null);
////               }
////        } catch (Exception e) {
////              return lockCallBack.onException(param, e);
////           }
//       return lockCallBack.onFailure(null);
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//        Lock<String, String> openLock = new Lock<>();
//        // ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
//        // newFixedThreadPool.submit(() -> {
//        // try (ZZLock lock = getClient().newLock("lock_name")) {
//        // if (lock.acquire(10)) {
//        // Thread.sleep(10000);
//        // }
//        // } catch (Exception e) {
//        // e.printStackTrace();
//        // }
//        // });
//        // Thread.sleep(1000);
//        String ctreateLockAndGetLock = openLock.createLockAndGetLock("lock_name", 2, new LockCallBack<String, String>() {
//
//            @Override
//            public String onSuccess(String param) {
//                System.out.println("onSuccess");
//                return "onSuccess";
//            }
//
//            @Override
//            public String onFailure(String param) {
//                System.out.println("onFailure");
//                return "onFailure";
//            }
//
//            @Override
//            public String onException(String param, Throwable e) {
//                System.out.println("onException" + e.toString());
//                return "onException";
//            }
//        });
//
//        System.err.println(ctreateLockAndGetLock);
//        // newFixedThreadPool.shutdown();
//    }
}

