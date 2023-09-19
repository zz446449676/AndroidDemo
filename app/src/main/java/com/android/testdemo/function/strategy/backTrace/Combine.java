package com.android.testdemo.function.strategy.backTrace;

import com.android.testdemo.function.strategy.Strategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 回溯算法 全排列组合问题
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。（元素无重不可复选）
 * 在 nums = [1,2,3]，combine(3, 2) 的返回值应该是：[ [1,2],[1,3],[2,3] ]
 *
 * 思路：
 * 稍微想想就会发现，大小为 2 的所有组合，不就是所有大小为 2 的子集嘛。
 * 所以我说组合和子集是一样的：大小为 k 的组合就是大小为 k 的子集。
 * 还是以 nums = [1,2,3] 为例，刚才让你求所有子集，就是把所有节点的值都收集起来；现在你只需要把第 2 层（根节点视为第 0 层）的节点收集起来，就是大小为 2 的所有组合：
 */
public class Combine implements Strategy {
    int[] arr = new int[] {1, 2, 3};
    List<List<Integer>> res = new LinkedList<>();
    // 记录回溯算法的递归路径
    LinkedList<Integer> track = new LinkedList<>();
    @Override
    public void run() {
        System.out.println(combine(3, 2));
    }

    private List<List<Integer>> combine(int n, int k) {
        backTrace(1, n, k);
        return res;
    }

    private void backTrace(int start, int n, int k) {
        // base case
        if (k == track.size()) {
            // 遍历到了第 k 层，收集当前节点的值
            res.add(new LinkedList<>(track));
            return;
        }

        // 回溯算法标准框架
        for (int i = start; i <= n; i++) {
            // 选择
            track.addLast(i);
            // 通过 start 参数控制树枝的遍历，避免产生重复的子集
            backTrace(i + 1, n, k);
            // 撤销选择
            track.removeLast();
        }
    }
}
