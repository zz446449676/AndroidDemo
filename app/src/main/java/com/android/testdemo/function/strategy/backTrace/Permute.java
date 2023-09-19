package com.android.testdemo.function.strategy.backTrace;

import com.android.testdemo.function.strategy.Strategy;
import com.android.testdemo.function.strategy.sort.QuickSort;

import java.util.LinkedList;
import java.util.List;

/**
 * 回溯算法全排列 （元素无重不可复选）
 * 给定一个不含重复数字的数组 nums，返回其所有可能的全排列。
 * 比如输入 nums = [1,2,3]，函数的返回值应该是：
 * [
 *     [1,2,3],[1,3,2],
 *     [2,1,3],[2,3,1],
 *     [3,1,2],[3,2,1]
 * ]
 * 思路：
 * 刚才讲的组合/子集问题使用 start 变量保证元素 nums[start] 之后只会出现 nums[start+1..] 中的元素，通过固定元素的相对位置保证不出现重复的子集。
 * 但排列问题本身就是让你穷举元素的位置，nums[i] 之后也可以出现 nums[i] 左边的元素，所以之前的那一套玩不转了，需要额外使用 used 数组来标记哪些元素还可以被选择。
 * 我们用 used 数组标记已经在路径上的元素避免重复选择，然后收集所有叶子节点上的值，就是所有全排列的结果：
 */
public class Permute implements Strategy {
    int[] arr = new int[] {1, 2, 3};
    List<List<Integer>> res = new LinkedList<>();
    // 记录回溯算法的递归路径
    LinkedList<Integer> track = new LinkedList<>();
    // track 中的元素会被标记为 true
    boolean[] used;
    @Override
    public void run() {
        System.out.println(permute(arr));
    }

    private List<List<Integer>> permute(int[] nums) {
        // 如果题目要求允许有相同元素，则先排序
//        QuickSort.quickSort(nums, 0, nums.length - 1);
        used = new boolean[nums.length];
        backtrack(nums);
        return res;
    }

    private void backtrack(int[] nums) {
        // base case，到达叶子节点
        // 注意，这里可以有变体，如果题目要求给出元素个数为 k 的排列，则修改条件，收集第K层的数据就行，即：track.size() == k
        if (track.size() == nums.length) {
            // 收集叶子节点上的值
            res.add(new LinkedList<>(track));
            return;
        }

        // 回溯算法标准框架
        for (int i = 0; i < nums.length; i++) {
            // 已经存在 track 中的元素，不能重复选择
            if (used[i]) {
                continue;
            }

            // 如果题目要求允许有相同元素, 新添加的剪枝逻辑，固定相同的元素在排列中的相对位置
//            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
//                continue;
//            }

            // 做选择
            used[i] = true;
            track.addLast(nums[i]);
            // 进入下一层回溯树
            backtrack(nums);
            // 取消选择
            track.removeLast();
            used[i] = false;
        }
    }
}
