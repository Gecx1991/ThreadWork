package com.gecx.ch1;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/17 10:37
 */
public class ThreadNew {

    private static class UseThread extends Thread {

        private long num;

        UseThread(long num) {
            this.num = num;
        }

        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " isInterrupted :" + isInterrupted());
            while (!isInterrupted()) {
                System.out.println(threadName + "is running!");
                System.out.println(threadName + " isInterrupted :" + isInterrupted());
            }
            System.out.println(threadName + " isInterrupted :" + isInterrupted());
        }
    }

    private static class UseRunnable implements Runnable {
        private long num;

        UseRunnable(long num) {
            this.num = num;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(threadName + "is running!");
                System.out.println(threadName + " isInterrupted :" + Thread.currentThread().isInterrupted());
            }
            System.out.println("I am implements Runnable:" + num);
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        UseThread thread = new UseThread(23);
//        UseRunnable runnable = new UseRunnable(33);
//        thread.start();
//        new Thread(runnable).start();

//        UseThread thread = new UseThread("Test isInterrupted");
//        thread.start();
//        Thread.sleep(5000L);
//        thread.interrupt();

        UseRunnable runnable = new UseRunnable(23);
        Thread threadRunnable = new Thread(runnable);
        threadRunnable.setName("Runnable");
        threadRunnable.start();
        Thread.sleep(500L);
        threadRunnable.interrupt();


    }
}
