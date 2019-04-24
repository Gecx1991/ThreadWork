package com.gecx.ch3.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/24 23:50
 */
public class UseAtomicArray {
    static int[] arr = new int[]{1, 2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(arr);

    public static void main(String[] args) {
        ai.getAndAdd(1, 3);
        System.out.println(ai.get(1));
        System.out.println(arr[1]);
    }
}
