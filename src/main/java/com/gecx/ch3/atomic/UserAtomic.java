package com.gecx.ch3.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/24 23:43
 */
public class UserAtomic {
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        atomicInteger.incrementAndGet();
        atomicInteger.getAndIncrement();
        atomicInteger.addAndGet(1);
        int i = 0;
        System.out.println(atomicInteger.compareAndSet(i, ++i));
    }
}
