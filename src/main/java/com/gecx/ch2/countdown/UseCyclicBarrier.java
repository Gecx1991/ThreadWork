package com.gecx.ch2.countdown;

import com.gecx.utils.SleepTools;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Gecx
 * @Description: 演示CyclicBarrier用法, 共4个子线程，他们全部完成工作后，交出自己结果，
 * 再被统一释放去做自己的事情，而交出的结果被另外的线程拿来拼接字符串
 * @date 2019/4/23 23:40
 */
public class UseCyclicBarrier {

    //    static CyclicBarrier barrier = new CyclicBarrier(4);
    static CyclicBarrier barrier = new CyclicBarrier(4, new CollectThread());

    private static ConcurrentHashMap<String, Long> resultMap = new ConcurrentHashMap<>();

    /**
     * 汇总线程（barrierAction） 当屏障都执行（触发）完成后执行该线程任务
     * the command to execute when the barrier is tripped
     */
    private static class CollectThread implements Runnable {

        @Override
        public void run() {
            StringBuffer result = new StringBuffer();
            for (Map.Entry<String, Long> workResult : resultMap.entrySet()) {
                result.append(workResult.getValue() + ",");
            }
            System.out.println(" the result = " + result);
            System.out.println("do other thing........");
        }
    }

    /**
     * 相互等待的线程,所有的线程调用await()以后才一同执行之后的业务逻辑
     * barrier可重复调用，在次调用 完成 会再次触发 CollectThread
     */
    private static class SubThread implements Runnable {

        @Override
        public void run() {
            long id = Thread.currentThread().getId();
            resultMap.put(Thread.currentThread().getId() + "", id);

            try {
                SleepTools.ms(1000);
                System.out.println("Thread " + id + "is running...");
                barrier.await();
                System.out.println("Thread " + id + "is end...");
                //barrier可重复调用，在次调用 完成 会再次触发 CollectThread
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        /**
         * 主线程中并没有调用CollectThread
         */
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }
}
