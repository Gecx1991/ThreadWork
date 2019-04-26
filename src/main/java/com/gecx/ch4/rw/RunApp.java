package com.gecx.ch4.rw;

import com.gecx.utils.SleepTools;

import java.util.Random;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/26 11:00
 */
public class RunApp {
    //读写线程比例
    static final int readWriteRatio = 10;
    //最小线程数
    static final int minThreadCount = 3;

    //读操作线程
    private static class GetThread implements Runnable {

        private GoodsService goodsService;

        public GetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                goodsService.getNum();
            }
            System.out.println(Thread.currentThread().getName() + "读取商品耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }

    //写操作线程
    private static class SetThread implements Runnable {

        private GoodsService goodsService;

        public SetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                SleepTools.ms(50);
                goodsService.setNum(random.nextInt(100));
            }
            System.out.println(Thread.currentThread().getName() + "写操作商品耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GoodsInfo goodsInfo = new GoodsInfo("fried chicken", 10000, 1000);
//        GoodsService goodsService = new UseRwLock(goodsInfo);
        GoodsService goodsService = new UseSync(goodsInfo);
        for (int i = 0; i < minThreadCount; i++) {
            Thread setThread = new Thread(new SetThread(goodsService));
            for (int j = 0; j < readWriteRatio; j++) {
                new Thread(new GetThread(goodsService)).start();
            }
            SleepTools.ms(100);
            setThread.start();
        }
    }

}
