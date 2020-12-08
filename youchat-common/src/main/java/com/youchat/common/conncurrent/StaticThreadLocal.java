package com.youchat.common.conncurrent;

/**
 * ThreadLocal是为解决多线程程序的并发问题而提出的，可以称之为线程局部变量。与一般的变量的区别在于，生命周期是在线程范围内的。
 * static变量是的生命周期与类的使用周期相同，即只要类存在，那么static变量也就存在。
 * 那么一个 static 的 ThreadLocal会是什么样的呢？
 *
 * 可以发现，static的ThreadLocal变量是一个与线程相关的静态变量，
 * 即一个线程内，static变量是被各个实例共同引用的，但是不同线程内，static变量是隔开的。
 *
 * Thread-0 --> 1
 * Thread-0 --> 2
 * Thread-0 --> 3
 *
 * main --> 1
 * main --> 2
 *
 * Thread-1 --> 1
 * Thread-1 --> 2
 * Thread-1 --> 3
 *
 * main --> 3
 * main --> 4
 *
 * Thread-2 --> 1
 * Thread-2 --> 2
 * Thread-2 --> 3
 *
 * main --> 5
 * main --> 6
 * main --> 7
 * main --> 8
 * main --> 9
 */
public class StaticThreadLocal {

    private static ThreadLocal<Integer> seqNum = ThreadLocal.withInitial(() -> 0);

    public int getNextNum(){
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    public static void main(String[] args){
        StaticThreadLocal sn = new StaticThreadLocal();
        TestClient t1  = new TestClient(sn);
        TestClient t2  = new TestClient(sn);
        TestClient t3  = new TestClient(sn);

        t1.start();
        t2.start();
        t3.start();

        t1.print();
        t2.print();
        t3.print();


    }

    private static class TestClient extends Thread{
        private StaticThreadLocal sn ;
        public TestClient(StaticThreadLocal sn ){
            this.sn = sn;
        }

        public void run(){
            for(int i=0; i< 3; i++){
                System.out.println( Thread.currentThread().getName()  + " --> " + sn.getNextNum());
            }
        }

        public void print(){
            for(int i=0; i< 3; i++){
                System.out.println( Thread.currentThread().getName()  + " --> " + sn.getNextNum());
            }
        }
    }
}
