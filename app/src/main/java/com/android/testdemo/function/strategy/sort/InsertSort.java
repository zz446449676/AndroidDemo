package com.android.testdemo.function.strategy.sort;

import com.android.testdemo.function.strategy.Strategy;

import java.util.Arrays;

/**
 * 给定一个数组，使用直接插入排序算法将该数组进行升序排列
 *
 * 说明：
 *  核心思想是 “找到该元素在它前面那个序列(有序序列)中的正确位置”
 *  具体来说，插入排序所有的操作都基于一个这样的前提：当前元素前面的序列是有序的。基于这个前提，从后往前去寻找当前元素在前面那个序列里的正确位置。
 *  最好情况O(n) , 平均和最坏情况都为 O(n^2)
 */
public class InsertSort implements Strategy {
    @Override
    public void run() {
        // 初始化数组
        final int[] arr = new int[]{5, 1, 3, 18, 15, 9, 29, 100, 2, 0, 14};

        int temp;

        // 直接插入排序开始，需要length轮排序
        for (int i = 0; i < arr.length; i++) {
            // 每轮挑选出一个数，将其插入到前面有序队列中的合适位置（进行比较和位置交换）
            for (int j = i; j > 0; j--) {
                if (arr[j - 1] > arr[j]) {
                   temp = arr[j - 1];
                   arr[j - 1] = arr[j];
                   arr[j] = temp;
                }
            }
            System.out.println("第 " + i + " 轮排序的数组为 ：" + Arrays.toString(arr));
        }
        System.out.println("最终 InsertSort 排序后的数组为：" + Arrays.toString(arr));
    }
}
