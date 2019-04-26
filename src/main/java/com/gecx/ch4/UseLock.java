package com.gecx.ch4;

import com.gecx.utils.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/26 9:37
 */
public class UseLock {

    private Lock lock = new ReentrantLock();
    private int num = 100000;

    private int getNum() {
        return num;
    }

    public void add() {
        lock.lock();
        try {
            num++;
        } finally {
            lock.unlock();
        }
    }

    public void remove() {
        lock.lock();
        try {
            num--;
        } finally {
            lock.unlock();
        }
    }


    private static class LockThread extends Thread {

        private UseLock useLock;

        public LockThread(UseLock useLock, String name) {
            super(name);
            this.useLock = useLock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                useLock.add();
            }
            System.out.println(Thread.currentThread().getName() + " the num is:" + useLock.getNum());
        }
    }
    public static void main(String[] args) {
        UseLock useLock = new UseLock();
        new LockThread(useLock, "userLockThread").start();
        for (int i = 0; i < 100000; i++) {
            useLock.remove();
        }
        SleepTools.second(5);
        System.out.println(Thread.currentThread().getName() + " the num is:" + useLock.getNum());
    }
}
