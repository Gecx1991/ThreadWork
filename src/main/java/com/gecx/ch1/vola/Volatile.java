package com.gecx.ch1.vola;

import com.gecx.utils.SleepTools;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/19 9:24
 */
public class Volatile {

    private static int num;
    private volatile static boolean flag = true;

    private static class PrintThread extends Thread {
        @Override
        public void run() {
            System.out.println("thread running!");
            while (flag) {
                System.out.println("number:" + num);
                System.out.println("flag:" + flag);
            }
            System.out.println("number:" + num);
            System.out.println("flag:" + flag);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new PrintThread().start();
        SleepTools.second(2);
        flag = false;
        num = 233;
        SleepTools.second(5);
        System.out.println("main is end!");
    }
}
