package com.youchat.account.pool;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 * init pool  set accounts
 *
 * when :
 *
 * qps > config_expansion_qps
 *
 *  pool expansion
 *
 *
 * qps < config_shrink_qps
 *
 *  pool shrink
 *
 *
 *
 *
 *
 * qps -> interface -> log -> kafka -> flink -> redis zset(qps , available)
 *
 *
 *
 *
 *
 */
public class StrategyAccountPool implements AccountPool{

    private long accountId;

    private ConfigProperty configProperty;

    private int poolingCount = 0;

    private AccountCreator creator;

    private volatile long[] accounts ;

    protected ReentrantLock lock;

    public StrategyAccountPool(ConfigProperty configProperty,AccountCreator creator) {
        this.configProperty = configProperty;
        this.creator = creator;
        init();
    }

    public StrategyAccountPool(ConfigProperty configProperty  ) {
        this.configProperty = configProperty;
        init();
    }

    private void init() {
        accounts = new long[configProperty.getInitAccountPoolSize()];
        while (poolingCount < configProperty.getInitAccountPoolSize()) {
            creator.create(accountId).forEach(x -> accounts[poolingCount++] = x);
        }
    }


    public void expansion() {
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }

    }

    public void shrink() {

    }

    @Override
    public AbstractAccount getAccount(Long accountId) {
        return null;
    }


    /**
     * get account by strategy
     */
    class Strategy {



    }

}
