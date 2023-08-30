package com.android.testdemo.function.strategy;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 三数求和问题
 * 示例： 给定数组 nums = [-1, 0, 1, 2, -1, -4]， 要求找到 a+b+c = 0 的组合，则满足要求的三元组集合为： [ [-1, 0, 1], [-1, -1, 2] ]
 * 明天实现
 */
public class ThreeSum implements Strategy {
    @Override
    public void run() {
        int[] arr = new int[] {-1, 0, 1, 2, -1, -4};
        ArrayList<int[]> result = new ArrayList<>();

        // 先使用快排对数组进行排序
        QuickSort quickSort = new QuickSort(arr);
        arr = quickSort.quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后的数组为 ： " + Arrays.toString(arr));
    }
}
