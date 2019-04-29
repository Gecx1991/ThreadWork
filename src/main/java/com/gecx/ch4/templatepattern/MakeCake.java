package com.gecx.ch4.templatepattern;

/**
 * @author Gecx
 * @Description: 制作蛋糕
 * @date 2019/4/29 10:27
 */
public class MakeCake {

    public static void main(String[] args) {
        AbstractCake cake = new CheeseCake();
        cake.run();
        AbstractCake smallCake = new SmallCake();
        smallCake.run();
    }
}
