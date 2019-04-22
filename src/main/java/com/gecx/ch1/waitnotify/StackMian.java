package com.gecx.ch1.waitnotify;

import com.gecx.utils.SleepTools;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/20 12:54
 */
public class StackMian {

    private static StackHomeWork homeWork = new StackHomeWork();

    private static class inThread extends Thread {
        @Override
        public void run() {
            homeWork.inStack();
        }
    }

    private static class outThread extends Thread {
        @Override
        public void run() {
            homeWork.outStack();
        }
    }

    public static void main(String[] args) {
        new outThread().start();
        new inThread().start();

    }
}
