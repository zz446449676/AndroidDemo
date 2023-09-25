package com.android.testdemo.function.strategy.dynamicProgram;

import com.android.testdemo.function.strategy.Strategy;

import java.util.Arrays;

/**
 * 最大子序列之和问题
 * 给你输入一个整数数组 nums，请你找在其中找一个和最大的子数组，返回这个子数组的和。
 * 比如说输入 nums = [-3,1,3,-1,2,-4,2]，算法返回 5，因为最大子数组 [1,3,-1,2] 的和为 5.
 * 思路：
 * 定义 dp 数组为 nums[i] 为结尾的「最大子数组和」为 dp[i]
 * 这种定义之下，想得到整个 nums 数组的「最大子数组和」，不能直接返回 dp[n-1]，而需要遍历整个 dp 数组：
 * 基于这种定义，我们有如下两种选择：
 * 可以做到，dp[i] 有两种「选择」，要么与前面的相邻子数组连接，形成一个和更大的子数组；要么不与前面的子数组连接，自成一派，自己作为一个子数组。
 * 状态转移方程为：
 * dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
 */
public class MaxSubArraySum implements Strategy {
    // 初始化数组
    int[] nums = new int[] {-3, 1, 3, -1, 2, -4, 2};

    // dp 数组以 nums[i] 为结尾的「最大子数组和」为 dp[i]
    int[] dp = new int[nums.length];

    @Override
    public void run() {
        Arrays.fill(dp, Integer.MIN_VALUE);
        System.out.println("最大子序列之和为 ： " + maxSubArraySum(nums));
    }

    private int maxSubArraySum(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        // base case
        // 第一个元素前面没有子数组
        dp[0] = nums[0];

        // 状态转移方程
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
        }
        // 得到 nums 的最大子数组
        int res = Integer.MIN_VALUE;
        for (int j = 0; j < n; j++) {
            res = Math.max(res, dp[j]);
        }
        return res;
    }
}
