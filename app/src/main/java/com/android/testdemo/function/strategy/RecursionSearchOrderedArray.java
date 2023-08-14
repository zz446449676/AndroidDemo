package com.android.testdemo.function.strategy;

/**
 * 递归遍历查找某一个有序数组,找到则返回数组下标，没找到则返回 -1；
 */
public class RecursionSearchOrderedArray {
    public int run() {
        // 需要查找的值
        final int[] recursionArray = new int[]{2, 4, 6, 8, 10, 12, 14};
        int target = 12;
        return recursion(recursionArray, target, 0, recursionArray.length - 1);
    }

    private int recursion(int[] array, int target, int start, int end) {
        if (array.length == 0 || start > end) return -1;
        // 向下取整
        int mid = (int)Math.floor((start + end) / 2);
        if (target == array[mid]) {
            return mid;
        }
        else if (target > array[mid]) {
            return recursion(array, target, mid + 1, end);
        } else {
            return recursion(array, target, start, mid - 1);
        }
    }
}
