package com.gecx.ch4.condition;

import com.gecx.utils.SleepTools;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/26 13:48
 */
public class RunCondApp {

    private static class checkKmThread implements Runnable {
//        private ExpressCond expressCond;
        private ExpressCondOneLock expressCond;

//        public checkKmThread(ExpressCond expressCond) {
//            this.expressCond = expressCond;
//        }

        public checkKmThread(ExpressCondOneLock oneLock) {
            this.expressCond = oneLock;
        }

        @Override
        public void run() {
            expressCond.waitKm();
        }
    }

    private static class checkSiteThread implements Runnable {
//        private ExpressCond expressCond;
        private ExpressCondOneLock expressCond;

//        public checkSiteThread(ExpressCond expressCond) {
//            this.expressCond = expressCond;
//        }


        public checkSiteThread(ExpressCondOneLock expressCond) {
            this.expressCond = expressCond;
        }

        @Override
        public void run() {
            expressCond.waitSite();
        }
    }

    public static void main(String[] args) {
//        ExpressCond expressCond = new ExpressCond(90, ExpressCond.CITY);
        ExpressCondOneLock expressCond = new ExpressCondOneLock(90, ExpressCond.CITY);
        for (int i = 0; i < 3; i++) {
            new Thread(new checkKmThread(expressCond)).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(new checkSiteThread(expressCond)).start();
        }
        SleepTools.second(1);
        expressCond.changeSite();
    }
}
