package com.gecx.ch3.answer;

import com.gecx.utils.SleepTools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 类说明：有一个残缺A类实现了线程安全的：
 * get方法和compareAndSet()方法
 * 自行实现它的递增方法
 */
public class HalfAtomicInt {
    private AtomicInteger atomicI = new AtomicInteger(0);

    static CountDownLatch latch = new CountDownLatch(50);

    /*请完成这个递增方法*/
    public void increament() {
        for (; ; ) {
            int current = getCount();
            int next = current + 1;
            if (compareAndSet(current++, next)) {
                System.out.println("执行成功！当前值为：" + getCount());
                break;
            } else {
                System.out.println("执行失败！当前值为：" + getCount() + " 尝试继续执行");
            }
        }
    }

    public int getCount() {
        return atomicI.get();
    }

    public boolean compareAndSet(int oldValue, int newValue) {
        return atomicI.compareAndSet(oldValue, newValue);
    }


    private static class AtomicIncrThread implements Runnable {

        private HalfAtomicInt atomicInt;

        public AtomicIncrThread(HalfAtomicInt atomicInt) {
            this.atomicInt = atomicInt;
        }

        @Override
        public void run() {
            atomicInt.increament();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        HalfAtomicInt atomicInt = new HalfAtomicInt();
        int count = 200;
        for (int i = 0; i < count; i++) {
            new Thread(new AtomicIncrThread(atomicInt)).start();
        }
        SleepTools.second(3);
        System.out.println("最终结果为：" + atomicInt.getCount());
    }

}
