package com.android.testdemo.function.strategy;

import com.android.testdemo.function.strategy.sort.QuickSort;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 三数求和问题
 * 示例： 给定数组 nums = [-1, 0, 1, 2, -1, -4]， 要求找到 a+b+c = 0 的组合，则满足要求的三元组集合为： [ [-1, 0, 1], [-1, -1, 2] ]
 * 注意：不允许有重复的三元组
 */
public class ThreeSum implements Strategy {
    // 所要求和的数组
    private static final int target = 0;
    @Override
    public void run() {
        int[] arr = new int[] {-1, 0, 1, 2, -1, -4};

        // 先使用快排对数组进行排序
        arr = QuickSort.quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后的数组为 ： " + Arrays.toString(arr));

        System.out.println("符合 target = " + target + " 的三元组为 ： ");
        for (int[] result : threeSum(arr)) {
            System.out.println(Arrays.toString(result));
        }
    }

    // 先固定一个数，然后使用对撞指针进行遍历
    // 如果两个指针的数字之和，加上所固定的数字之和大于所求数字，则右指针向左移动，否则左指针向右移动
    // 如果对撞指针相撞，则表示没有找到合适的三元组
    private ArrayList<int[]> threeSum(int[] arr) {
        ArrayList<int[]> result = new ArrayList<>();
        if (arr == null || arr.length == 0) return result;
        for (int i = 0; i < arr.length - 2; i++) {
            // 所固定的数字
            int num = arr[i];
            if (i > 0 && num == arr[i-1]) continue;
            // 左右对撞指针 
            int left = i + 1;
            int right = arr.length - 1;

            while (left < right) {
                if (num + arr[left] + arr[right] == target) {
                    result.add(new int[]{num, arr[left], arr[right]});
                    right--;
                } else if (num + arr[left] + arr[right] < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}
