package com.gecx.ch2.future;

import com.gecx.utils.SleepTools;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Gecx
 * @Description: Future使用及线程中断
 * @date 2019/4/24 13:54
 */
public class UserCallable {

    private static class CallThread implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("Callable子线程开始计算！");
            //SleepTools.ms(200);sleep InterruptedException异常会使isInterrupted状态重置为false
            int result = 0;
            for (int i = 0; i <= 100; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("任务中断。。。");
                    return null;
                }
                result += i;
            }
            System.out.println("Call result:" + result);
            return result;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<Integer>(new CallThread());
        new Thread(task).start();
        if (new Random().nextBoolean()) {
            task.cancel(true);
            System.out.println("main cancel...");
        } else {
            System.out.println("main result:" + task.get());
        }
    }
}
