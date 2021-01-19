package com.youchat.common.linux;

public class System {
    /**
     * CPU负载和 CPU使用率
     * 这两个从一定程度上都可以反映一台机器的繁忙程度.
     *
     * *** cpu使用率反映的是当前cpu的//繁忙程度//，忽高忽低的原因在于占用cpu处理时间的进程可能处于io等待状态但却还未释放进入wait。
     * CPU使用率其实就是你运行的程序占用的CPU资源，表示你的机器在某个时间点的运行程序的情况。使用率越高，说明你的机器在这个时间上运行了很多程序，反之较少。
     *
     * *** 平均负载（load average）是指（某段时间内）（占用cpu时间的进程）和（等待cpu时间的进程）（数），这里等待cpu时间的进程是指等待被唤醒的进程，不包括处于wait状态进程。
     *
     * 以上分析可以看出，一台机器很有可能处于低cpu使用率高负载的情况，因此看机器的繁忙程度应该结合两者，
     * 从实际的使用情况观察，自己的一台双核志强2.8GHZ，2G内存的机器在平均负载到50左右，cpu使用率才接近100%（应用有不少io操作），
     * 这种情况下应用还算流畅，实际访问延迟不是很高。因此在cpu还空闲的情况下，如何提高io响应是减少负载的关键
     * ，很多人认为负载到几十了机器就非常繁忙了，我倒觉得如果这个时候cpu使用率比较低，则负载高可能不能很好说明问题，
     * 一旦cpu处理的进程处理完后，那些等待的进程也能立刻得到响应，这种情况下应该优化io读写速度。真到cpu使用率一直90%以上，
     * 即使平均负载只有个位数（比如某一个进程一直在运算），那机器其实也已经繁忙了~
     *
     * 其实，在前面的文章中，也有写到cpu使用率低负载高，原因分析 cpu使用率低，但是load很高，load很高的可能是IO
     *
     * CPU负载的一个类比
     * 判断系统负荷是否过重，必须理解load average的真正含义。下面，我根据"Understanding Linux CPU Load"这篇文章，尝试用最通俗的语言，解释这个问题。
     * 首先，假设最简单的情况，你的电脑只有一个CPU，所有的运算都必须由这个CPU来完成。
     * 那么，我们不妨把这个CPU想象成一座大桥，桥上只有一根车道，所有车辆都必须从这根车道上通过。（很显然，这座桥只能单向通行。）
     * 系统负荷为0，意味着大桥上一辆车也没有。
     *
     * 系统负荷为0.5，意味着大桥一半的路段有车。
     *
     * 系统负荷为1.0，意味着大桥的所有路段都有车，也就是说大桥已经"满"了。但是必须注意的是，直到此时大桥还是能顺畅通行的。
     *
     * 系统负荷为1.7，意味着车辆太多了，大桥已经被占满了（100%），后面等着上桥的车辆为桥面车辆的70%。以此类推，系统负荷2.0，意味着等待上桥的车辆与桥面的车辆一样多；系统负荷3.0，意味着等待上桥的车辆是桥面车辆的2倍。总之，当系统负荷大于1，后面的车辆就必须等待了；系统负荷越大，过桥就必须等得越久。
     *
     * CPU的系统负荷，基本上等同于上面的类比。大桥的通行能力，就是CPU的最大工作量；桥梁上的车辆，就是一个个等待CPU处理的进程（process）。
     * 如果CPU每分钟最多处理100个进程，那么系统负荷0.2，意味着CPU在这1分钟里只处理20个进程；系统负荷1.0，意味着CPU在这1分钟里正好处理100个进程；系统负荷1.7，意味着除了CPU正在处理的100个进程以外，还有70个进程正排队等着CPU处理。
     * 为了电脑顺畅运行，系统负荷最好不要超过1.0，这样就没有进程需要等待了，所有进程都能第一时间得到处理。很显然，1.0是一个关键值，超过这个值，系统就不在最佳状态了，你要动手干预了。
     *
     * CPU负载-多处理器
     * 上面，我们假设你的电脑只有1个CPU。如果你的电脑装了2个CPU，会发生什么情况呢？
     * 2个CPU，意味着电脑的处理能力翻了一倍，能够同时处理的进程数量也翻了一倍。
     * 还是用大桥来类比，两个CPU就意味着大桥有两根车道了，通车能力翻倍了。
     *
     * 所以，2个CPU表明系统负荷可以达到2.0，此时每个CPU都达到100%的工作量。推广开来，n个CPU的电脑，可接受的系统负荷最大为n.0。
     *
     * CPU负载-多核处理器
     * 芯片厂商往往在一个CPU内部，包含多个CPU核心，这被称为多核CPU。
     * 在系统负荷方面，多核CPU与多CPU效果类似，所以考虑系统负荷的时候，必须考虑这台电脑有几个CPU、每个CPU有几个核心。然后，把系统负荷除以总的核心数，只要每个核心的负荷不超过1.0，就表明电脑正常运行。
     * 怎么知道电脑有多少个CPU核心呢？
     * "cat /proc/cpuinfo"命令，可以查看CPU信息。"grep -c 'model name' /proc/cpuinfo"命令，直接返回CPU的总核心数。
     *
     * 系统负荷的经验法则
     * 1.0是系统负荷的理想值吗？
     * 不一定，系统管理员往往会留一点余地，当这个值达到0.7，就应当引起注意了。经验法则是这样的：
     * 当系统负荷持续大于0.7，你必须开始调查了，问题出在哪里，防止情况恶化。
     * 当系统负荷持续大于1.0，你必须动手寻找解决办法，把这个值降下来。
     * 当系统负荷达到5.0，就表明你的系统有很严重的问题，长时间没有响应，或者接近死机了。你不应该让系统达到这个值。
     *
     * 对于我的机器，有24个core，那么，load多少合适呢？
     *
     * [root@jiangyi01.sqa.zmf /home/ahao.mah/ALIOS_QA]
     * #grep 'model name' /proc/cpuinfo | wc -l
     * 24
     * 答案是：
     *
     * [root@jiangyi01.sqa.zmf /home/ahao.mah/ALIOS_QA]
     * #echo "0.7*24" |bc
     * 16.8
     * 最佳观察时长
     * 最后一个问题，"load average"一共返回三个平均值----1分钟系统负荷、5分钟系统负荷，15分钟系统负荷，----应该参考哪个值？
     * 如果只有1分钟的系统负荷大于1.0，其他两个时间段都小于1.0，这表明只是暂时现象，问题不大。
     * 如果15分钟内，平均系统负荷大于1.0（调整CPU核心数之后），表明问题持续存在，不是暂时现象。所以，你应该主要观察"15分钟系统负荷"，将它作为电脑正常运行的指标。
     *
     * REF
     *
     *
     *
     * 11、Load误解
     *
     * 　a：系统load高一定是性能有问题。
     *
     * 　　　真相：Load高也许是因为在进行cpu密集型的计算
     *
     *   b：系统Load高一定是CPU能力问题或数量不够。
     *
     * 　　　真相：Load高只是代表需要运行的队列累计过多了。但队列中的任务实际可能是耗Cpu的，也可能是耗i/0及其他因素的。
     *
     *   c：系统长期Load高，首先增加CPU
     *
     * 　　真相：Load只是表象，不是实质。增加CPU个别情况下会临时看到Load下降，但治标不治本。
     *
     *  d：在Load average 高的情况下如何鉴别系统瓶颈。
     *
     * 　　是CPU不足，还是io不够快造成或是内存不足?
     */
}
