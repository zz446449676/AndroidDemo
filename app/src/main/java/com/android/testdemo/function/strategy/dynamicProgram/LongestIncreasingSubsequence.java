package com.android.testdemo.function.strategy.dynamicProgram;

import com.android.testdemo.function.strategy.Strategy;

import java.util.Arrays;

/**
 * 最长递增子序列问题
 * 输入一个无序的整数数组，请你找到其中最长的严格递增子序列的长度
 * 比如 输入 nums=[10,9,2,5,3,7,101,18]，其中最长的递增子序列是 [2,3,7,101]，所以算法的输出应该是 4.
 */
public class LongestIncreasingSubsequence implements Strategy {
    // 初始数组
    int[] arr = new int[] {10, 9, 2, 5, 3, 7, 101, 18};
    // dp 数组 表示以 arr[i] 这个数结尾的最长递增子序列的长度
    int[] dp = new int[arr.length];
    @Override
    public void run() {
        // base case：dp 数组全都初始化为 1
        Arrays.fill(dp, 1);
        System.out.println(longestIncreasingSubsequence(arr));
    }

    private int longestIncreasingSubsequence(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                // 寻找 nums[0..j-1] 中比 nums[i] 小的元素
                if (nums[i] > nums[j]) {
                    // 把 nums[i] 接在后面，即可形成长度为 dp[j] + 1，
                    // 且以 nums[i] 为结尾的递增子序列
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }
}
