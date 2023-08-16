package com.android.testdemo.function.strategy;

import java.util.Arrays;

/**
 * 给定一个数组，使用选择排序算法将该数组进行升序排列
 *
 * 说明：
 * 选择排序的关键字是“最小值”，循环遍历数组，每次都找出当前范围内的最小值，把它放在当前范围的头部
 * 即：（需要记录下当前范围内最小元素的索引下标，然后根据下标把最小值元素和当前范围内头部元素位置进行交换）
 * 然后缩小排序范围，继续重复以上操作，直至数组完全有序为止。所有情况均为 O(n^2)
 */
public class SelectSort implements Strategy{
    @Override
    public void run() {
        // 初始化数组
        final int[] arr = new int[]{5, 1, 3, 18, 15, 9, 29, 100, 2, 0, 14};

        // 记录选择的最小值的下标
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
            System.out.println("第 " + i + " 轮排序的数组为 ：" + Arrays.toString(arr));
        }
        System.out.println("最终 SelectSort 排序后的数组为：" + Arrays.toString(arr));
    }
}
