package com.android.testdemo.function.strategy.sort;

import com.android.testdemo.function.strategy.Strategy;

import java.util.Arrays;

/**
 * 给定一个数组，使用快速排序算法将该数组进行升序排列
 *
 * 说明：
 * 采用递归方法，以第一个元素为基准，确定出基准数的位置
 * 即：使用左右双指针遍历，大于基准数的放右边，小于基准数的放左边，基准数放中间合适位置
 * 以该基准数为中心，将数组划分为左右两个子数组。递归调用继续对左右两个子数组进行相同操作。
 * 平均时间复杂度O(nlog(n))， 最坏情况下是O(n^2)
 */
public class QuickSort implements Strategy {
    int[] normalArr = new int[]{5, 1, 3, 18, 15, 9, 29, 100, 2, 0, 14};

    public QuickSort(int[] arr) {
        this.normalArr = arr;
    }

    public QuickSort() {}

    @Override
    public void run() {
        // 初始化数组

        // 最坏情况的数组
        final int[] worstArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // 最好情况的数组
        final int[] betterArr = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        System.out.println("最终 QuickSort 排序后的数组为：" + Arrays.toString(quickSort(normalArr, 0, normalArr.length - 1)));
    }

    public static int[] quickSort(int[] arr, int star, int end) {
        if (star > end) return arr;
        int left = star;
        int right = end;

        int temp;

        while (left < right) {
            // 左指针开始向右遍历，发现有存在比arr[star]大的数，则停止
            while (left <= right && arr[left] <= arr[star]) left++;

            // 右指针开始向左遍历，发现有存在比arr[star]小的数，则停止
            while (right >= left && arr[right] >= arr[star]) right--;

            if (left < right) {
                temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            } else {
                temp = arr[right];
                arr[right] = arr[star];
                arr[star] = temp;

                // 进入递归分别对基准数两边的数组进行快排
                quickSort(arr, star, right - 1);
                quickSort(arr, right + 1, end);
            }
        }
        return arr;
    }
}
