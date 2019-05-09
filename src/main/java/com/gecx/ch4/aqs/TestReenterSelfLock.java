package com.gecx.ch4.aqs;

import com.gecx.utils.SleepTools;

import java.util.concurrent.locks.Lock;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/5/8 11:33
 */
public class TestReenterSelfLock {

    static final Lock lock = new ReenterSelfLock();

    public void test() {
        class Worker extends Thread {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                SleepTools.second(1);
                reenter(3);
            }
        }

        //启动3个线程
        for (int i = 0; i < 3; i++) {
            Worker w = new Worker();
            w.start();
        }

        for (int i = 0; i < 10; i++) {
            SleepTools.second(1);
            System.out.println("主线程对比。。。");
        }

    }

    //递归模拟重入
    public void reenter(int x) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":递归层级:" + x);
            int y = x - 1;
            if (y == 0) {
                return;
            } else {
                reenter(y);
            }
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        TestReenterSelfLock testMyLock = new TestReenterSelfLock();
        testMyLock.test();
    }
}
