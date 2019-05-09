package com.gecx.ch4.aqs;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Gecx
 * @Description: 实现独占，可重入锁
 * @date 2019/5/8 11:17
 */
public class ReenterSelfLock implements Lock {

    /**
     * 操作代理到sync上
     */
    private final Sync sync = new Sync();

    private static class Sync extends AbstractQueuedSynchronizer {
        /**
         * 当状态为0时获取锁
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                //设置排他锁
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            } else if (getExclusiveOwnerThread() == Thread.currentThread()) {
                //判断为重入
                setState(getState() + 1);
                return true;
            }
            return false;
        }

        /**
         * 释放锁，将状态设置为0
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            if (getExclusiveOwnerThread() != Thread.currentThread()) {
                throw new IllegalMonitorStateException();
            }
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setState(getState() - 1);
            if (getState() == 0) {
                setExclusiveOwnerThread(null);
            }
            return true;
        }

        /**
         * 判断是否处于占用状态
         *
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() > 0;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }

    @Override
    public void lock() {
        System.out.println(Thread.currentThread().getName() + " ready get lock");
        sync.acquire(1);
        System.out.println(Thread.currentThread().getName() + " already get lock");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName() + " ready release lock");
        sync.release(1);
        System.out.println(Thread.currentThread().getName() + " already released lock");
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }
}
