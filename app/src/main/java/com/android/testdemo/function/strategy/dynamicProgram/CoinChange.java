package com.android.testdemo.function.strategy.dynamicProgram;

import com.android.testdemo.function.strategy.Strategy;

import java.util.Arrays;

/**
 * 凑零钱问题（动态规划，自顶向下解法）
 * 给你 k 种面值的硬币，面值分别为 c1, c2 ... ck，每种硬币的数量无限，再给一个总金额 amount，问你最少需要几枚硬币凑出这个金额，如果不可能凑出，算法返回 -1.
 * 比如说 k = 3，面值分别为 1，2，5，总金额 amount = 11。那么最少需要 3 枚硬币凑出，即 11 = 5 + 5 + 1
 * 思路：
 * 明确 base case -> 明确「状态」-> 明确「选择」 -> 定义 dp 数组/函数的含义。
 * 1. base case : amount 为 0 时算法返回 0，因为不需要任何硬币就已经凑出目标金额了。
 * 2. 状态： 也就是原问题和子问题中会变化的变量。由于硬币数量无限，硬币的面额也是题目给定的，只有目标金额会不断地向 base case 靠近，所以唯一的「状态」就是目标金额 amount
 * 3. 选择： 也就是导致「状态」产生变化的行为。目标金额为什么变化呢，因为你在选择硬币，你每选择一枚硬币，就相当于减少了目标金额。所以说所有硬币的面值，就是你的「选择」
 * 定义dp 也就是(coinChange)数组：
 * 我们这里讲的是自顶向下的解法，所以会有一个递归的 dp 函数。
 * 一般来说函数的参数就是状态转移中会变化的量，也就是上面说到的「状态」；函数的返回值就是题目要求我们计算的量。就本题来说，状态只有一个，即「目标金额」，题目要求我们计算凑出目标金额所需的最少硬币数量。
 * 所以我们可以这样定义 dp 函数：dp(n) 表示，输入一个目标金额 n，返回凑出目标金额 n 所需的最少硬币数量。
 */
public class CoinChange implements Strategy {
    int[] memo;
    @Override
    public void run() {
        int amount = 11;
        int[] coins = new int[] {1, 2, 5};
        // 备忘录优化
        memo = new int[amount + 1];
        Arrays.fill(memo, -10);

        System.out.println(coinChange(coins, amount));
    }

    // dp 数组
    private int coinChange(int[] coins, int amount) {
        // Base case
        if (amount == 0) return 0;
        if (amount < 0) return -1;

        // 检查备忘录，如果有，说明已经计算过，直接返回结果
        if (memo[amount] != -10) return memo[amount];

        int result = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 计算子问题结果
            int subProblem = coinChange(coins, amount - coin);
            if (subProblem == -1) continue;
            result = Math.min(result, subProblem + 1);
        }
        memo[amount] = result == Integer.MAX_VALUE ? -1 : result;
        return memo[amount];
    }
}
