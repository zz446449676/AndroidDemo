package com.android.testdemo.function.strategy.backTrace;

import com.android.testdemo.function.strategy.Strategy;
import com.android.testdemo.function.strategy.sort.QuickSort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 输入 candidates 和一个目标和 target，从 candidates 中找出中所有和为 target 的组合。
 * candidates 可能存在重复元素，且其中的每个数字最多只能使用一次。
 * 只要额外用一个 trackSum 变量记录回溯路径上的元素和，然后将 base case 改一改即可解决这道题：
 */
public class CombineToSum implements Strategy {
    int[] candidates = new int[] {1, 2, 3, 2, 4, 7, 1};
    List<List<Integer>> res = new LinkedList<>();
    // 记录回溯的路径
    LinkedList<Integer> track = new LinkedList<>();
    // 记录 track 中的元素之和
    int trackSum = 0;
    @Override
    public void run() {
        System.out.println(combineToSum(candidates, 7));
    }

    private List<List<Integer>> combineToSum(int[] candidates, int target) {
        if (candidates.length == 0) return res;

        // 先排序，让相同元素靠在一起，方便剪枝操作
        QuickSort.quickSort(candidates, 0, candidates.length - 1);
        System.out.println("排序后 candidates : " + Arrays.toString(candidates));
        backtrack(candidates, 0, target);
        return res;
    }

    private void backtrack(int[] nums, int start, int target) {
        // base case，达到目标和，找到符合条件的组合
        if (trackSum == target) {
            res.add(new LinkedList<>(track));
            return;
        }

        // base case, 超过目标和，直接结束
        if (trackSum > target) return;

        // 回溯算法标准框架
        for (int i = start; i < nums.length; i++) {
            // 剪枝逻辑，值相同的树枝减去，只遍历第一条
            if (i > start && nums[i] == nums[i - 1]) continue;
            // 做选择
            track.add(nums[i]);
            trackSum += nums[i];
            // 递归遍历下一层回溯
            backtrack(nums, i + 1, target);
            // 撤销选择
            track.removeLast();
            trackSum -=nums[i];
        }
    }
}
