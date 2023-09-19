package com.android.testdemo.function.strategy.backTrace;

import com.android.testdemo.function.strategy.Strategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 全排列求出给定数列的子集 （元素无重不可复选）
 * 比如输入 nums = [1,2,3]，算法应该返回如下子集：
 * [ [],[1],[2],[3],[1,2],[1,3],[2,3],[1,2,3] ]
 */
public class SubSet implements Strategy {
    int[] arr = new int[] {1, 2, 3};
    List<List<Integer>> res = new LinkedList<>();
    // 记录回溯算法的递归路径
    LinkedList<Integer> track = new LinkedList<>();
    @Override
    public void run() {
        System.out.println(solution(arr));
    }

    private List<List<Integer>> solution(int[] nums) {
        backTrace(nums, 0);
        return res;
    }

    // 回溯算法核心函数，遍历子集问题的回溯树
    // 我们使用 start 参数控制树枝的生长避免产生重复的子集，用 track 记录根节点到每个节点的路径的值，同时在前序位置把每个节点的路径值收集起来，完成回溯树的遍历就收集了所有子集
    private void backTrace(int[] nums, int start) {
        // 前序位置，每个节点的值都是一个子集
        res.add(new LinkedList<>(track));

        // 回溯算法标准框架
        for (int i = start; i < nums.length; i++) {
            // 做选择
            track.addLast(nums[i]);
            // 通过 start 参数控制树枝的遍历，避免产生重复的子集
            backTrace(nums, i + 1);
            // 撤销选择
            track.removeLast();
        }
    }
}
