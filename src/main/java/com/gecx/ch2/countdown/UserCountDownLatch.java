package com.gecx.ch2.countdown;

import com.gecx.utils.SleepTools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/22 22:31
 */
public class UserCountDownLatch {

    static CountDownLatch latch = new CountDownLatch(6);

    private static class CountThread extends Thread {
        @Override
        public void run() {
            System.out.println("CountThread running...");
            SleepTools.second(1);
            System.out.println("CountThread end");
            latch.countDown();
        }
    }

    private static class BusThread extends Thread {
        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 4; i++) {
                System.out.println("BusThread" + Thread.currentThread().getId()
                        + " do business-----");
            }
        }
    }

    private static class afterThread implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " init something...");
            latch.countDown();
            System.out.println("init complete!");

            System.out.println("after init do something...");
            SleepTools.second(2);
            System.out.println("after end...");
            latch.countDown();

        }
    }

    public static void main(String[] args) {
        new BusThread().start();
        for (int i = 0; i < 3; i++) {
            new CountThread().start();
        }
        new Thread(new afterThread()).start();
        System.out.println("main is running...");
        SleepTools.second(3);
        System.out.println("main end!");
        latch.countDown();
    }
}
