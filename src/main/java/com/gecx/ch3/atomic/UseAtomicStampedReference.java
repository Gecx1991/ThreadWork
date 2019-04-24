package com.gecx.ch3.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/24 23:57
 */
public class UseAtomicStampedReference {
    static AtomicStampedReference<String> asr = new AtomicStampedReference<>("hello", 0);

    public static void main(String[] args) throws InterruptedException {
        int oldStamp = asr.getStamp();
        final String oldReference = asr.getReference();

        Thread oldThread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean b = asr.compareAndSet(oldReference, oldReference + "world", oldStamp, oldStamp + 1);
                System.out.println("threadOld:" + b);
            }
        });

        Thread currentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String currentReference = asr.getReference();
                System.out.println(currentReference + "==" + asr.getStamp());
                boolean b = asr.compareAndSet(currentReference, currentReference + "Atomic", oldStamp, oldStamp + 1);
                System.out.println("threadCurrent:" + b);
            }
        });

        Thread currentThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String currentReference = asr.getReference();
                boolean b = asr.compareAndSet(currentReference, currentReference + "Atomic2", asr.getStamp(), asr.getStamp() + 1);
                System.out.println("threadCurrent2:" + b);
            }
        });

        oldThread.start();
        oldThread.join();
        currentThread.start();
        currentThread.join();
        currentThread2.start();
        currentThread2.join();
        System.out.println(asr.getReference() + "===" + asr.getStamp());

    }
}
