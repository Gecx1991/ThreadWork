package com.gecx.ch4.templatepattern;

/**
 * @author Gecx
 * @Description: 模板方法模式
 * 模板方法模式的意图是，定义一个操作中的算法的骨架，而将一些步骤的实现延迟到子类中。
 * 模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。我们最常见的就是Spring框架里的各种Template
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
