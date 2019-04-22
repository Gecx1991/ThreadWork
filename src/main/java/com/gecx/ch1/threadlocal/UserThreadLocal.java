package com.gecx.ch1.threadlocal;

/**
 * @author Gecx
 * @Description: ThreadLocal 基本使用
 * @date 2019/4/19 10:13
 */
public class UserThreadLocal {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            //默认返回null
            return 1;
        }
    };

    static Integer count = new Integer(1);

    /**
     * 运行3个线程
     */
    public void StartThreadArray() {
        Thread[] runs = new Thread[3];
        for (int i = 0; i < runs.length; i++) {
            runs[i] = new Thread(new TestThread(i));
        }
        for (int i = 0; i < runs.length; i++) {
            runs[i].start();
        }
    }

    /**
     * 类说明：测试线程，线程的工作是将ThreadLocal变量的值变化，并写回，看看线程之间是否会互相影响
     */
    public static class TestThread implements Runnable {
        int id;

        public TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            Integer s = threadLocal.get();
            threadLocal.set(s + id);
            System.out.println(Thread.currentThread().getName() + "--id:" + threadLocal.get());
//            count = count + id;
//            System.out.println(Thread.currentThread().getName() + "--id:" + count);
//            threadLocal.remove();
        }
    }

    public static void main(String[] args) {
        UserThreadLocal test = new UserThreadLocal();
        test.StartThreadArray();
    }
}
