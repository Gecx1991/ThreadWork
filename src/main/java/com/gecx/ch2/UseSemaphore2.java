package com.gecx.ch2;

import com.gecx.utils.SleepTools;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author Gecx
 * @Description: 多线程访问较少资源，对一定数目的资源进行分配
 * @date 2019/4/24 0:33
 */
public class UseSemaphore2 {

    static Semaphore semaphore = new Semaphore(10);
    /**
     * 存放资源的容器
     */
    private static LinkedList<Integer> list = new LinkedList<>();

    /**
     * 释放资源
     */
    public void release() {

        synchronized (list) {
            list.addLast(new Random().nextInt(100));
        }
        /**
         * 将许可证放回,释放资源  通知没有拿到资源的一方执行
         */
        semaphore.release();
        System.out.println(Thread.currentThread().getId() + " 释放了资源。。。" + semaphore.availablePermits());
    }


    /**
     * 获取资源
     */
    public void fetch() {

        /**
         * acquire()拿不到许可证 时会等待，拿到了继续执行
         * 获取到许可证后 许可证总量减少
         */
        try {
            semaphore.acquire();
            SleepTools.second(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (list) {
            if (list.size() > 0) {
                list.removeFirst();
            }
        }
        System.out.println(Thread.currentThread().getId() + " 获取资源。。。" + semaphore.availablePermits());
    }


    private static class BusThread implements Runnable {
        private UseSemaphore2 semaphore;

        public BusThread(UseSemaphore2 semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            semaphore.fetch();
            semaphore.release();
        }
    }

    public static void main(String[] args) {

        UseSemaphore2 semaphore = new UseSemaphore2();
        for (int i = 0; i < 50; i++) {
            new Thread(new BusThread(semaphore)).start();
        }
    }

}
