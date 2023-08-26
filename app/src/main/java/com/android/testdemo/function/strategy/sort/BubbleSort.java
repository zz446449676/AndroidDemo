package com.android.testdemo.function.strategy.sort;

import com.android.testdemo.function.strategy.Strategy;

import java.util.Arrays;

/**
 * 给定一个数组，使用冒泡排序算法将该数组进行升序排列
 *
 * 说明：
 * 就是从第一个元素开始，“重复比较相邻的两个项”
 * 若第一项比第二项更大，则交换两者的位置；反之不动。
 * 然后缩小范围进行新一轮冒泡，每一轮操作，都会将这一轮中最大的元素放置到数组的末尾。
 * 假如数组的长度是 n，那么当我们重复完 n 轮的时候，整个数组就有序了。最好情况O(n) , 平均和最坏情况都为 O(n^2)
 */
public class BubbleSort implements Strategy {
    // 最好情况的数组
    private final int[] betterArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    // 最坏情况的数组
    private final int[] worstArr = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    // 常规数组
    private final int[] normalArr = new int[]{5, 1, 3, 18, 15, 9, 29, 100, 2, 0, 14};
    @Override
    public void run() {
        // 初始化数组
        final int[] arr = normalArr;

        // 定义一个标记,标记是否发生过冒泡，如果当轮没有发生冒泡，说明已经有序，直接停止算法。
        boolean tag;
        int temp;

        // 需要经过length轮的冒泡
        for (int i = 0; i < arr.length; i++) {
            tag = false;
            // 开始冒泡
            for (int j = 0; j < arr.length -i -1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    tag = true;
                }
            }
            if (!tag) {
                System.out.println("冒泡排序第 " + i + " 轮未发生冒泡，所以有序数组为 ：" + Arrays.toString(arr));
                return;
            }
            System.out.println("第 " + i + " 轮排序的数组为 ：" + Arrays.toString(arr));
        }
        System.out.println("最终 BubbleSort 排序后的数组为：" + Arrays.toString(arr));
    }
}
