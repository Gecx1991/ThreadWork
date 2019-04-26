package com.gecx.ch4.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Gecx
 * @Description: 快递类
 * @date 2019/4/26 11:54
 */
public class ExpressCond {

    public final static String CITY = "HangZhou";
    //运输里程数
    private int km;
    //到达地点
    private String site;
    private Lock kmLock = new ReentrantLock();
    private Lock siteLock = new ReentrantLock();
    private Condition kmCon = kmLock.newCondition();
    private Condition siteCon = siteLock.newCondition();

    public ExpressCond() {
    }

    public ExpressCond(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public void changKm() {
        kmLock.lock();
        try {
            this.km = 123;
            //通知其他在锁上等待的线程
            kmCon.signal();
//            kmCon.signalAll();
        } finally {
            kmLock.unlock();
        }
    }

    public void changeSite() {
        siteLock.lock();
        try {
            this.site = "YunNan";
//            siteCon.signal();
            siteCon.signalAll();
        } finally {
            siteLock.unlock();
        }
    }

    /**
     * 快递里程大于100时更新数据
     */
    public void waitKm() {
        kmLock.lock();
        try {
            while (this.km < 100) {
                try {
                    kmCon.await();
                    //对应的业务处理
                    System.out.println(Thread.currentThread().getName() + "Check Km changed and be notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            kmLock.unlock();
        }
    }


    /**
     * 快递到达指定城市后修改数据
     */
    public void waitSite() {
        siteLock.lock();
        try {
            while (this.site.equals(CITY)) {
                try {
                    siteCon.await();
                    //对应的业务处理
                    System.out.println(Thread.currentThread().getName() + "Check Site changed and be notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            siteLock.unlock();
        }
    }

}
