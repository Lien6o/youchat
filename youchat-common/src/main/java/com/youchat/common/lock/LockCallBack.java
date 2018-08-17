package com.youchat.common.lock;

/**
 * @program: youchat-common
 * @description: 回调函数
 * @author: lien6o
 * @create: 2018-08-16 20:20
 **/
public interface LockCallBack<K, V> {

    /**
     * @Description: 获取分布式锁成功
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/16
     */
    V onSuccess(K param);

    /**
     * @Description: 获取分布式锁失败
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/16
     */
    V onFailure(K param);

    /**
     * @Description: 获取分布式锁异常
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/16
     */
    V onException(K param, Throwable e);
}