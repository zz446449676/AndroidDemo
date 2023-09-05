package com.android.testdemo.function.strategy;

/**
 * 求柱状图中的最大矩形
 * 题目描述：给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 *
 * 示例:
 * 输入: [2,1,5,6,2,3]
 * 输出: 10
 *
 * 思路：遍历每一个数，遍历过程中固定住这个数的高度，然后向左右两边去探索宽度的上限
 */
public class LargestRectangleArea implements Strategy {
    @Override
    public void run() {
        int[] arr = new int[]{2, 1, 5, 6, 2, 3};
        System.out.println("该柱状图最大矩形面积为 ： " + countArea(arr));
    }

    private int countArea(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        // 初始化最大值
        int max = 0;
        // 遍历每根柱子
        for (int i = 0; i < arr.length; i++) {
            // 如果遍历完了所有柱子，或者遇到了比前一个矮的柱子，则停止遍历，开始回头计算
            if (i == arr.length - 1 || arr[i] > arr[i+1]) {
                // 初始化前i个柱子中最矮的柱子
                int minHeight = arr[i];
                // "回头看"
                for (int j = i; j >= 0; j--) {
                    // 若遇到比当前柱子更矮的柱子，则以更矮的柱子为高进行计算
                    minHeight = Math.min(minHeight, arr[j]);
                    // 计算当前柱子对应的最大宽度的矩形面积，并及时更新最大值
                    max = Math.max(max, minHeight * (i-j+1));
                }
            }
        }
        return max;
    }
}
