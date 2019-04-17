package com.gecx.ch1;

/**
 * @author Gecx
 * @Description: join()测试
 * @date 2019/4/17 11:45
 */
public class UseJoin {

    private static class JoinThread implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "is start!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "is end!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread main = Thread.currentThread();
        Thread joinThread = new Thread(new JoinThread());
        joinThread.setName("Join Thread");
        joinThread.start();
//        joinThread.join();
        System.out.println("main start...");
        Thread.sleep(2000);
        System.out.println("main end ...");
    }
}
