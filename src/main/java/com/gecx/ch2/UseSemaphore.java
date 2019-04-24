package com.gecx.ch2;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author Gecx
 * @Description: 多线程访问较少资源，对一定数目的资源进行分配
 * @date 2019/4/24 0:33
 */
public class UseSemaphore {

    static Semaphore useful = new Semaphore(10);
    static Semaphore useless = new Semaphore(0);
    /**
     * 存放资源的容器
     */
    private static LinkedList<Integer> list = new LinkedList<>();


    /**
     * 释放资源
     */
    public void release() {
        try {
            /**
             * acquire()拿不到许可证 时会等待，拿到了继续执行
             */
            useless.acquire();
            synchronized (list) {
                list.addLast(new Random().nextInt(100));
            }
            useful.release();
            System.out.println(Thread.currentThread().getId() + " 释放了资源。。。" + useless.availablePermits() + "---" + useful.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取资源
     */
    public void fetch() {
        try {
            useful.acquire();
            synchronized (list) {
                if (list.size() > 0) {
                    list.removeFirst();
                }
            }
            useless.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 将许可证放回,释放资源通知 拿不到的一方执行
         */
        System.out.println(Thread.currentThread().getId() + " 获取资源。。。" + useful.availablePermits() + "---" + useless.availablePermits());
    }


    private static class BusThread implements Runnable {
        private UseSemaphore semaphore;

        public BusThread(UseSemaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            semaphore.fetch();
            semaphore.release();
        }
    }

    public static void main(String[] args) {

        UseSemaphore semaphore = new UseSemaphore();
        for (int i = 0; i < 50; i++) {
            new Thread(new BusThread(semaphore)).start();
        }
    }

}
