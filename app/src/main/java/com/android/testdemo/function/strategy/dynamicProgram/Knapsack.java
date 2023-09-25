package com.android.testdemo.function.strategy.dynamicProgram;

import com.android.testdemo.function.strategy.Strategy;

/**
 * 背包问题
 * 给你一个可装载重量为 W 的背包和 N 个物品，每个物品有重量和价值两个属性。其中第 i 个物品的重量为 wt[i]，价值为 val[i]，现在让你用这个背包装物品，最多能装的价值是多少？
 * 例如：
 * N = 3, W = 4
 * wt = [2, 1, 3]
 * val = [4, 2, 3]
 * 结果返回 6;
 *
 * 思路：
 * 第一步要明确两点，「状态」和「选择」
 * 1. 状态：重量和价值
 * 2. 选择：对于可选择的物品。是否装入背包
 * 3. dp 数组：
 * 因为我我们的状态有两个，所以需要一个二维的dp数组，即：dp[i][w] 的定义如下：对于前 i 个物品，当前背包的容量为 w，这种情况下可以装的最大价值是 dp[i][w]。
 * 如果 dp[3][5] = 6，其含义为：对于给定的一系列物品中，若只对前 3 个物品进行选择，当背包容量为 5 时，最多可以装下的价值为 6
 *
 * 状态转移方程：
 * 如果你没有把这第 i 个物品装入背包，那么很显然，最大价值 dp[i][w] 应该等于 dp[i-1][w]，继承之前的结果。
 * 如果你把这第 i 个物品装入了背包，那么 dp[i][w] 应该等于 val[i-1] + dp[i-1][w - wt[i-1]]。
 * dp[i][w] = max(
 *             dp[i-1][w],
 *             dp[i-1][w - wt[i-1]] + val[i-1]
 *         )
 *
 * 最后套入框架：
 * for 状态1 in 状态1的所有取值：
 *     for 状态2 in 状态2的所有取值：
 *         for ...
 *             dp[状态1][状态2][...] = 择优(选择1，选择2...)
 */
public class Knapsack implements Strategy {
    int[] wt = new int[] {2, 1, 3};
    int[] val = new int[] {4, 2, 3};
    @Override
    public void run() {
        System.out.println(knapsack(4, 3, wt, val));
    }

    private int knapsack(int W, int N, int[] wt, int[] val) {
        assert N == wt.length;

        // base case 初始化
        int[][] dp = new int[N + 1][W + 1];
        for (int i = 1; i <= N; i++) {
            for (int w = 1; w <= W; w++) {
                if (w - wt[i - 1] < 0) {
                    // 这种情况下只能选择不装入背包
                    dp[i][w] = dp[i - 1][w];
                } else {
                    // 装入或者不装入背包，择优
                    dp[i][w] = Math.max(
                            dp[i - 1][w - wt[i-1]] + val[i-1],
                            dp[i - 1][w]
                    );
                }
            }
        }
        return dp[N][W];
    }
}
