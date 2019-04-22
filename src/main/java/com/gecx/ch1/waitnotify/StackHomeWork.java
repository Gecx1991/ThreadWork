package com.gecx.ch1.waitnotify;

import com.gecx.utils.SleepTools;

import java.util.Map;

/**
 * @author Gecx
 * @Description: 采用多线程技术，例如wait/notify，设计实现一个符合生产者和消费者问题的程序，对某一个对象（枪膛）进行操作，其最大容量是20颗子弹，
 * 生产者线程是一个压入线程，它不断向枪膛中压入子弹，消费者线程是一个射出线程，它不断从枪膛中射出子弹。
 * 请实现上面的程序。将程序运行结果发送至 446106311@qq.com。邮件标题请写好 学号，作业标题
 * @date 2019/4/20 1:20
 */
public class StackHomeWork {

    private volatile int count;
    public static final int MAX = 20;

    public int getCount() {
        return count;
    }

    public synchronized void inStack() {
        while (true) {
            if (count < MAX) {
                count = count + 1;
                System.out.println("向枪膛中压入子弹：" + count);
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notifyAll();
        }
    }


    public synchronized void outStack() {
        while (true) {
            try {
                wait();
                while (count > 0) {
                    count = count - 1;
                    System.out.println("射出子弹剩余：" + count);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notifyAll();
        }
    }
}
