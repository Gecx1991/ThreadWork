package com.gecx.ch4.templatepattern;

/**
 * @author Gecx
 * @Description: 模板方法模式
 * @date 2019/4/29 10:07
 */
public abstract class AbstractCake {

    /**
     * 模型
     */
    protected abstract void shape();

    /**
     * 涂抹
     */
    protected abstract void apply();

    /**
     * 烘焙
     */
    protected abstract void bake();

    /**
     * final修饰的成员方法不能被子类重写
     * 模板方法
     */
    public final void run() {
        this.shape();
        if (this.shouldApply()) {
            this.apply();
        }
        this.bake();
    }

    public boolean shouldApply() {
        return true;
    }
}
