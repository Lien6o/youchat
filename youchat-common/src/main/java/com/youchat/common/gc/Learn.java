package com.youchat.common.gc;

public class Learn {
    /**
     *理论部分
     *
     *
     * 想法：和想象的不一样，JVM不是收集和清理掉无用的对象，而是反过来，找到存活的有用的对象，然后回收剩下的对象，这就导致堆上存活的对象越多，GC的效率越低
     * 堆上的对象由引用关系组成了一棵树，树的根节点可以分为4类
     *    - 栈上局部变量
     *
     *    - 活跃的Java 线程
     *
     *    - 静态变量
     *
     *    - JNI Reference
     *
     *
     *
     * GC的过程：
     *
     *     从Root对象开始把遍历到的对象标记为活跃 (Mark)
     *
     *     清理未标记的对象 (Sweep)
     *
     *     压缩 (不一定每次GC都做)
     *
     * 内存分配带来碎片问题，解决办法
     *    - GC过程中做压缩，负面影响是GC时间更长了, 一次压缩消耗几秒或者更长时间很常见，所以
     *
     *       》有些JVM不是每次GC都压缩而是在碎片化达到一定程度在压缩
     *
     *       》压缩到能有一定的连续内存就停止
     *
     *       》用copy来取代压缩：把内存分为2个区域(A, B)，A用来分配内存给对象，B保持空白，当A出现碎片时，把A中活跃对象copy到B，然后标记A为空
     *
     * 并发情况下的内存分配
     *     - 一个JVM下的线程在同一片空间上做内存分配，为了避免冲突，为不同线程分配内存这个步骤需要同步，在高并发下，这个同步带来严重性能问题
     *
     *     - 解决办法：给每个线程一个排他的局部堆(TLH Thread-Local-Heap, 注意不等于Thread Local Variable), 好处->提高并发，坏处->消耗更多内存
     *
     *
     *
     * 新生代，老生代，持久代
     *
     *    - 两个现象
     *
     *           》大多数对象的生命周期都很短
     *
     *           》存活比较久的对象对短生命周期的对象引用很少
     *
     *    - 基于上述两个现象，把堆分成两类
     *
     *          》新生代：
     *
     *               > Oracle Hotspot: 分为Eden(E), Survivor1(S1), Survivor2(S2), 创建对象时在Eden中申请，满了GC E->S1, S1也满了就GC E+S1->S2, 然后S1标记为全空，S2满了再反过来GC E+S2->S1，S1, S2来回倒，一个对象能在多次循环中存活下来进老生代
     *
     *               > IBM WebSphere JVM: 等分为Allocate区和Survivor区，对象的转移过程是 Allocate->Survivor->老生代。另外如果对象比较大直接在老生代里申请，原因是挪动大对象代价比较高
     *
     *               > Oracle JRocket: 划出一片专门区域叫Keep Area(KA), 先在新生代的KA外分配内存，最后在KA内分配内存，这样KA中自然是最新创建的对象，GC时把KA区外的对象copy到老生代，然后重新划分出一片区域做KA (背后的逻辑是KA里面的对象刚创建出来被GC掉的可能性不大，这个策略也避免GC时在新生代内copy对象的开销)
     *
     *          》老生代：主要是放长期存活的对象
     *
     *          》持久代：Oracle Hotspot独有的，用来存放类定义和常量，一般不用GC(但现在在有些App Server，OSGI容器上，这个也需要GC)
     *
     *     - 新生代太小会导致新生代GC太频繁，老生代涨的很快然后带来Major GC，新生代设的太大会导致新生代上的Minor GC也耗时比较长，所以要找一个平衡
     *
     *
     *
     * Minor GC -> GC in Young Generation
     * Major GC -> GC in Old Generation （很多地方都会把Major GC等价于Full GC，甚至一些知名内存监控工具都有这个概念混淆的问题）
     * Full GC -> GC in Young + Old + Permanent (Oracle Hotspot only)
     *
     *
     * GC Hang：所有的GC都会导致GC期间程序hang住(即使是CMS也有2个小的全Hang的窗口)，Minor GC也不例外(只是它一般很快所以被忽视)，其实原因也好理解，GC过程中一般都会涉及对象地址的变化，不hang住重新算完地址再开放控制权给应用程序，很容易就不work了.
     *
     * 减小GC导致的全Hang窗口的办法
     *    - 优化 mark-sweep的GC算法
     *
     *       》多个GC线程并行(Parallel)，充分利用全Hang窗口的CPU
     *
     *       》让GC线程和应用程序线程并发(Concurrent)(CMS ->Concurrent Mark-Sweep)
     *
     *           1. 短暂全Hang：先mark 所有root object
     *
     *           2. GC线程和应用程序线程并行，在此过程中，GC线程从root出发，Mark可触达的对象
     *
     *           3. 再次全hang，分析#2这一步的时间窗内新创建的对象并Mark
     *
     *           4. Sweep
     *
     *    - 减少堆上的活跃对象
     *
     *
     *
     * RMI对GC的影响：
     *
     *       - RMI调用方有一个local object(stub)，代表remote server上的一个对象，在调用方的local object还存在时，remote server对应的对象不能被GC掉，到底Server上的对象啥时能能被GC掉呢？问题变复杂了，
     *
     *        - 解法1：
     *
     *              - 每个Stub申请一个lease，lease有个过期时间
     *
     *              - lease过期前，Server上的对象不能清除
     *
     *              - stub通过心跳不断renew lease
     *
     *              - 一旦lease过期，Server上的object就可以被GC了
     *
     *         - 问题：如果调用方stub未被GC掉，Server上的对象就不能被GC；解法：RMI会定期Force client Major GC，但又会带来client的性能问题
     *
     *         - 解法2：尽量用Stateless RMI调用，这样Server上就只需要一个服务对象的实例了
     *
     * 实战部分
     *
     *
     * 内存分析工具：JConsole, jStat,  Java VisualVM, JRockit Mission Control，JVM Tool Interface， dynaTrace(要花钱)，直接dump 堆
     *
     * GC策略和配置
     *    - 响应时间很关键：全hang(stop-the-world)窗口需要尽量小，所以新生代总是用并行算法(Parallel, 充分利用CPU)，老生代用并发算法(Concurrent, CMS)
     *
     *    - 吞吐量很关键：GC要尽量快，所以新生代总是用并行算法(Paralle, 充分利用CPU)，老生代先用并行算法(Parallel)，如果还是耗时太长，改用并发算法(CMS)
     *
     *    - 并发很高：开TLH (Thread Local Heap)
     *
     * 常见问题：
     *    - 频繁GC -》内存不足，或者新生代太小，或者内存泄露
     *
     *    - 如果即使有GC，内存消耗还是不断涨 —>内存泄露了，用内存分析工具看一下什么对象的数量涨的很快
     *
     *    - 老生代涨的太快，响应时间被GC影响很大 -> 要么新生代太小，要么代码有问题短时间内创建了大量对象
     *
     *    - 老生代每次GC前后内存波动很大 —> 要么新生代太小，要么代码有问题短时间内创建了大量对象，要么Transaction占用了太多内存
     *
     *    - 程序执行到某个函数时导致的GC次数远高于其它函数 —>很可能这个函数有问题，短时间内创建了大量对象，吃光了新生代
     *
     *    - 随着load增长，GC时间也会增长，但一般不应该超过10%的CPU时间占用，如果GC耗时太多 —>要么JVM配置有问题，要么程序有问题
     *
     *    - 在循环中创建临时变量，导致短时间迅速创建大量对象 —>在循环外面创建对象，在循环内复用这个对象，这种问题通过内存分析很容易看出来
     *
     *    - 测试环境中没问题，一上线就出了GC问题  —>测试环境做压力测试时用的数据和实际线上数据差异太大
     *
     *    - 一段代码在测试环境执行不会创建很多对象，但是在生产环境中迅速创建了大量对象 —>看看这段代码在线上是不是处在高并发执行路径上，然后看看对象是否可复用
     *
     *    - 没有内存泄露，就是内存使用的特别特别多 —>对象引用关系树里面，可能有某些root对象下面挂了太多节点，树的深度和宽度都很大
     *
     *
     *
     * 内存泄露专项分析
     *    - 内存使用迅速飙升 —>分析内存，看什么对象的数量涨的很快
     *
     *    - 内存使用缓慢持续上升 —> 分析内存，看什么对象吃掉了大量内存
     *
     *    - 滥用可修改的静态Collection —>静态变量是root 对象,如果只往collection加对象不清理，那么肯定内存泄露了，所以要使用可修改的静态collection，一定记得自己清理。实际上最好是在代码里面永远也不要用可修改的collection，一般总是有其他办法的
     *
     *    - ThreadLocal 变量 —>如果一个Thread长期存活(比如ThreadPool)，而Thread又是root对象，所以对长期存活Thread的Thread Local 和静态变量就差不多了，一定要记得在代码里面做清理
     *
     *    - 循环或复杂的双向引用，下面的代码看起来doc对象可以被GC，但实际上child对象有一个到parent的引用，所以实际只要child活着doc就不能被GC
     *
     *         org.w3c.dom.Document doc = readXmlDocument();
     *
     *         org.w3c.dom.Node child = doc.getDocumentElement().getFirstChild();
     *
     *         doc.removeNode(child);
     *
     *         doc = null;
     *
     *     - JNI内存泄露：native方法里面创建了Java Object，只要native方法没有返回这些对象就不能被GC，如果Native方法要run很久或者就永不返回永远运行，那么就可能内存泄露，这个问题可以通过dump heap看Native Reference找到
     *
     *
     *
     * 内存占用太高专项分析
     *    - cache策略问题：由于SoftReference的特性(http://javarevisited.blogspot.hk/2014/03/difference-between-weakreference-vs-softreference-phantom-strong-reference-java.html)，使得其在做缓存时特别合适，但如果缓存策略不合理，就会导致缓存迅速吃光内存，然后GC，然后缓存被清空，然后内存又被吃光，又GC，如此反复，或者是缓存策略没问题，但是新生代配置的太小也容易出现这个问题
     *
     *    - session 使用不合理：做web开发的同学对这个应该很熟悉了，如果在session里面放了太多东西(比如Hibernate Session), 然后又由于session一般都有个不短的过期时间(比如30分钟或者更长)，并发一高服务器就迅速OOM了，而且如果集群间要再做个session复制，就更加崩溃了
     *
     *    - Equal和Hashcode函数的实现有问题：一个对象的Hash Code被用来hashmap中做插入和查找操作，但hashcode不总是能保证唯一，所以一个hashcode可能命中一个桶，里面多个对象可能都是要查找的对象，此时就需要用Equal函数来做比较。如果Hashcode或者Equal函数有问题，我们可能在hashmap中找不到对象而不得不不断插入新对象，导致内存使用不断增加
     *
     *
     *
     * ClassLoader专项分析
     *    - 大class：比如一个字典类把某个语言的所有词条都load到静态变量，很有可能就把持久代撑爆了，解决办法是结合应用程序的逻辑按需加载数据
     *
     *    - 一个类定义被多次加载：class loader彼此独立隔离，在一些应用服务器上或者OSGI容器上，同时可能会部署多个应用，然后同样的类定义就可能被不用的应用多次加载，这类问题一般改一下服务器配置就可以解决
     *
     *    - ClassLoader泄露：一个ClassLoader只有在没人引用时才可以被GC，而一个classLoader加载的类实例都对它有一个引用，当我们在一个应用服务器上卸载掉一个应用，但是如果该应用中的某些对象还在存活(比如被cache)，那么这个对象指向的classLoader就不能被GC，解决办法是先卸载应用，然后做一个full heap dump，然后看heap dump中是否还有该应用的对象
     *
     *    - 同样的一个Class被一遍一遍地反复加载：作者的开发环境是Oracle Hotspot JVM，类定义被放在了持久代，没问题，但是线上服务器是IBM JVM(没有持久代，类定义和其他对象被同等对待)，所以每次GC这些class定义就被GC掉了，然后使用的时候又被重新加载导致了一些性能问题。解决办法也很简单，给这些类定义加一个cache或者其他reference让它不能被GC就可以了
     */
}
