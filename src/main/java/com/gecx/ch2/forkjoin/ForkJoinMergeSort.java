package com.gecx.ch2.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author Gecx
 * @Description: Fork/Join实现归并排序
 * @date 2019/4/23 16:59
 */
public class ForkJoinMergeSort {

    private static class MyTask extends RecursiveTask<int[]> {

        int perSize = 2;
        private int[] arr;

        public MyTask(int[] arr) {
            this.arr = arr;
        }

        @Override
        protected int[] compute() {
            if (arr.length <= perSize) {
                //对数组进行升序操作
                return InsertionSort.sort(arr);
            } else {
                int mid = arr.length / 2;
                //截取生成新的数组
                MyTask leftTask = new MyTask(Arrays.copyOfRange(arr, 0, mid));
                MyTask rightTask = new MyTask(Arrays.copyOfRange(arr, mid, arr.length));
                invokeAll(leftTask, rightTask);
                return MergeSort.merge(leftTask.join(), rightTask.join());
            }
        }
    }


    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] array = MakeArray.makeArray();
        MyTask task = new MyTask(array);
        pool.invoke(task);
        int[] join = task.join();
        for (int i : join) {
            System.out.print(i + ",");
        }
    }
}
