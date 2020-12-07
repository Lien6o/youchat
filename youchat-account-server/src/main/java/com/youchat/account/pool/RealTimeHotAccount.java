package com.youchat.account.pool;


public class RealTimeHotAccount extends AbstractAccount {

    private Long balance;

    private Double qps;

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Double getQps() {
        return qps;
    }

    public void setQps(Double qps) {
        this.qps = qps;
    }
}
