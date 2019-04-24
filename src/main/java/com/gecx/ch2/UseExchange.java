package com.gecx.ch2;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * 类说明：演示CyclicExchange用法
 */
public class UseExchange {
    private static final Exchanger<Set<String>> exchange = new Exchanger<Set<String>>();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setA = new HashSet<String>();//存放数据的容器
                try {
                    setA.add("A1");
                    setA.add("A2");
                    setA.add("A3");
                    setA = exchange.exchange(setA);//交换set
                    /*处理交换后的数据*/
                    Iterator<String> iterator = setA.iterator();
                    while (iterator.hasNext()) {
                        System.out.println("after exchange setA:" + iterator.next());
                    }
                } catch (InterruptedException e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setB = new HashSet<String>();//存放数据的容器
                try {
                    setB.add("B1");
                    setB.add("B2");
                    setB.add("B3");
                    setB.add("B4");
                    setB = exchange.exchange(setB);//交换set
                    /*处理交换后的数据*/
                    Iterator<String> iterator = setB.iterator();
                    while (iterator.hasNext()) {
                        System.out.println("after exchange setB:" + iterator.next());
                    }
                } catch (InterruptedException e) {
                }
            }
        }).start();

    }
}
