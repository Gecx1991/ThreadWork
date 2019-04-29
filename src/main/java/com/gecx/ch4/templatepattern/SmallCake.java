package com.gecx.ch4.templatepattern;

/**
 * @author Gecx
 * @Description: 小蛋糕制作
 * @date 2019/4/29 10:30
 */
public class SmallCake extends AbstractCake {

    private boolean flag = false;

    public void setFlag(boolean shouldApply) {
        this.flag = shouldApply();
    }

    @Override
    public boolean shouldApply() {
        return this.flag;
    }

    @Override
    protected void shape() {
        System.out.println("小蛋糕造型");
    }

    @Override
    protected void apply() {
        System.out.println("小蛋糕涂抹");
    }

    @Override
    protected void bake() {
        System.out.println("小蛋糕烘焙");
    }
}
