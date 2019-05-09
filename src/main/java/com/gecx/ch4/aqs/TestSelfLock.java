package com.gecx.ch4.aqs;

import com.gecx.utils.SleepTools;

import java.util.concurrent.locks.Lock;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/5/8 10:42
 */
public class TestSelfLock {

    public void test() {
//        final Lock lock = new SelfLock();
        final Lock lock = new TrinityLock();

        class Worker extends Thread {
            @Override
            public void run() {
                lock.lock();
                System.out.println(Thread.currentThread().getName());
                try {
                    SleepTools.second(3);
                } finally {
                    lock.unlock();
                }

            }
        }

        /**
         * 启动4个子线程
         */
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.start();
        }

        for (int i = 0; i < 10; i++) {
            SleepTools.second(1);
            System.out.println("主线程对比。。。");
        }
    }

    public static void main(String[] args) {
        TestSelfLock testMyLock = new TestSelfLock();
        testMyLock.test();
    }

}
