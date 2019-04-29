package com.gecx.ch4.templatepattern;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/29 10:15
 */
public class CheeseCake extends AbstractCake {

    @Override
    protected void shape() {
        System.out.println("芝士蛋糕塑型");
    }

    @Override
    protected void apply() {
        System.out.println("芝士蛋糕涂抹");
    }

    @Override
    protected void bake() {
        System.out.println("芝士蛋糕烘焙");
    }
}
