package com.youchat.common.db;

public class Learn {
    /**
     * 怎么减少主从延迟
     * 主从同步问题永远都是一致性和性能的权衡，得看实际的应用场景，若想要减少主从延迟的时间，可以采取下面的办法：
     *
     * 1.降低多线程大事务并发的概率，优化业务逻辑
     * 2.优化SQL，避免慢SQL，减少批量操作，建议写脚本以update-sleep这样的形式完成。
     * 3.提高从库机器的配置，减少主库写binlog和从库读binlog的效率差。
     * 4.尽量采用短的链路，也就是主库和从库服务器的距离尽量要短，提升端口带宽，减少binlog传输的网络延时。
     * 5.实时性要求的业务读强制走主库，从库只做灾备，备份。
     */
}