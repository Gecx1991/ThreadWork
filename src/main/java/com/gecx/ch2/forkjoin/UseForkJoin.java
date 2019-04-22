package com.gecx.ch2.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author Gecx
 * @Description: 计算从1-100的
 * @date 2019/4/22 21:36
 */
public class UseForkJoin {

    public static final int MAX = 100;

    private static class SumTask extends RecursiveTask<Integer> {

        /**
         * 自定义的任务大小
         */
        private int perSize = MAX / 10;
        /**
         * 起始数
         */
        private int fromIndex;
        /**
         * 结尾数
         */
        private int toIndex;

        public SumTask(int fromIndex, int toIndex) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected Integer compute() {
            if (toIndex - fromIndex < perSize) {
                int count = 0;
                for (int i = fromIndex; i <= toIndex; i++) {
                    count = count + i;
                }
                return count;
            } else {
                int mid = (fromIndex + toIndex) / 2;
                SumTask left = new SumTask(fromIndex, mid);
                SumTask right = new SumTask(mid + 1, toIndex);
                invokeAll(left, right);
                return left.join() + right.join();
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //范式
        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(0, 100);
//        Integer invoke = pool.invoke(task);
//        pool.invoke(task);
        ForkJoinTask<Integer> submit = pool.submit(task);
        Integer join = task.join();
        System.out.println(submit.get());
        System.out.println(join);

    }
}
