package com.gecx.ch1.waitnotify;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/22 10:48
 */
public class TestWait {

    private static class WaitThread extends Thread {

        @Override
        public void run() {
            new TestWait().waitTest();
        }
    }


    private synchronized void waitTest() {
        System.out.println("Wait running...");
        try {
            wait(5000);
//            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Wait end...");
    }

    public static void main(String[] args) {

        new WaitThread().start();

    }
}
