package com.android.testdemo.function.strategy;

import java.util.Arrays;

/**
 * 给定一个数组，使用选择排序算法将该数组进行升序排列
 */
public class SelectSort implements Strategy{
    @Override
    public void run() {
        // 初始化数组
        final int[] arr = new int[]{5, 1, 3, 18, 15, 9, 29, 100, 2, 0, 14};

        // 初始定义一个无穷小的整数
        int min_index;
        int temp;

        // 开始排序, 需要length - 1 轮排序
        for (int i = 0; i < arr.length - 1; i++) {
            min_index = i;
            // 每一轮排序时，找到最小值的下标索引，进行换位赋值
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min_index]) {
                    min_index = j;
                }
            }
            if (arr[i] > arr[min_index]) {
                temp = arr[i];
                arr[i] = arr[min_index];
                arr[min_index] = temp;
            }
        }
        System.out.println("SelectSort 排序后的数组为：" + Arrays.toString(arr));
    }
}
