package com.android.testdemo.function.strategy;

import com.android.testdemo.function.strategy.backTrace.Combine;
import com.android.testdemo.function.strategy.backTrace.CombineToSum;
import com.android.testdemo.function.strategy.backTrace.GenerateParenthesis;
import com.android.testdemo.function.strategy.backTrace.Permute;
import com.android.testdemo.function.strategy.backTrace.RepeatCombineToSum;
import com.android.testdemo.function.strategy.backTrace.SubSet;
import com.android.testdemo.function.strategy.backTrace.WordSplit;
import com.android.testdemo.function.strategy.dynamicProgram.CoinChange;
import com.android.testdemo.function.strategy.dynamicProgram.Knapsack;
import com.android.testdemo.function.strategy.dynamicProgram.LongestIncreasingSubsequence;
import com.android.testdemo.function.strategy.dynamicProgram.MaxSubArraySum;
import com.android.testdemo.function.threadDemo.ThreadSync;
import com.android.testdemo.function.strategy.sort.BubbleSort;
import com.android.testdemo.function.strategy.sort.InsertSort;
import com.android.testdemo.function.strategy.sort.MergeSort;
import com.android.testdemo.function.strategy.sort.QuickSort;
import com.android.testdemo.function.strategy.sort.SelectSort;
import com.android.testdemo.function.strategy.string.ReversalString;

/**
 * 算法工厂，动态创建算法实例
 */
public class StrategyFactory {
    // 递归遍历对一个有序数组进行查找
    public static final int RECURSION_SEARCH_ORDERED_ARRAY = 0;

    // 用选择排序算法对数组进行排序
    public static final int SELECT_SORT = 1;

    // 用冒泡排序算法对数组进行排序
    public static final int BUBBLE_SORT = 2;

    // 用直接插入排序算法对数组进行排序
    public static final int INSERT_SORT = 3;

    // 用快速排序算法对数组进行排序
    public static final int QUICK_SORT = 4;

    // 用归并排序算法对数组进行排序
    public static final int MERGE_SORT = 5;

    // 三数求和问题
    public static final int THREE_SUM = 7;

    // 移除单链表倒是第N个数
    public static final int REMOVE_N_TH_FROM_END = 8;

    // 链表反转
    public static final int LINK_LIST_REVERSE = 9;

    // 线程同步，线程使用案例
    public static final int THREAD_SYNC = 10;
    // 字符串反转
    public static final int REVERSAL_STRING = 6;

    // 寻找二叉树最近公共祖先
    public static final int LOWEST_COMMON_ANCESTOR = 11;

    // 接雨水问题
    public static final int CATCH_RAIN_WATER = 12;

    // K 个一组翻转链表
    public static final int REVERSE_K_GROUP_LINK_LIST = 13;

    // 求柱状图中的最大矩形
    public static final int LARGEST_RECTANGLE_AREA = 14;

    // 括号匹配问题
    public static final int VALID_BRACKET = 15;

    // 回溯全排列子集
    public static final int BACK_TRACE_SUBSET = 16;

    // 回溯全排列组合
    public static final int BACK_TRACE_COMBINE = 17;

    // 回溯全排列问题
    public static final int BACK_TRACE_PERMUTE = 18;

    // 回溯算法 从数组中找出和为 Target 的组合或者子集
    public static final int BACK_TRACE_COMBINE_TO_SUM = 19;

    // 回溯算法，可重复使用数组内的数，找出和为Target的组合或者子集
    public static final int BACK_TRACE_REPEAT_COMBINE_TO_SUM = 20;

    // 回溯算法生成括号
    public static final int BACK_TRACE_GENERATE_PARENTHESIS = 21;

    // 回溯单词拆分问题
    public static final int BACK_WORD_SPLIT = 22;

    // 凑零钱问题
    public static final int DYNAMIC_COIN_CHANGE = 23;

    // 最长递增子序列
    public static final int DYNAMIC_LONGEST_INCREASING_SUBSEQUENCE = 24;

    // 求最大子序列之和
    public static final int DYNAMIC_MAX_SUB_ARRAY_SUM = 25;

    // 背包问题
    public static final int DYNAMIC_KNAPSACK = 26;

    public static Strategy getStrategy(int strategy) {
        Strategy mStrategy = null;
        switch (strategy) {
            case RECURSION_SEARCH_ORDERED_ARRAY:
                mStrategy = new RecursionSearchOrderedArray();
                break;

            case SELECT_SORT:
                mStrategy = new SelectSort();
                break;

            case BUBBLE_SORT:
                mStrategy = new BubbleSort();
                break;

            case INSERT_SORT:
                mStrategy = new InsertSort();
                break;

            case QUICK_SORT:
                mStrategy = new QuickSort();
                break;

            case MERGE_SORT:
                mStrategy = new MergeSort();
                break;

            case THREE_SUM:
                mStrategy = new ThreeSum();
                break;

            case REMOVE_N_TH_FROM_END:
                mStrategy = new RemoveNthFromEnd();
                break;

            case LINK_LIST_REVERSE:
                mStrategy = new LinkListReverse();
                break;

            case THREAD_SYNC:
                mStrategy = new ThreadSync();
                break;

            case REVERSAL_STRING:
                mStrategy = new ReversalString();
                break;

            case LOWEST_COMMON_ANCESTOR:
                mStrategy = new LowestCommonAncestor();
                break;

            case CATCH_RAIN_WATER:
                mStrategy = new CatchRainWater();
                break;

            case REVERSE_K_GROUP_LINK_LIST:
                mStrategy = new ReverseKGroupLinkList();
                break;

            case LARGEST_RECTANGLE_AREA:
                mStrategy = new LargestRectangleArea();
                break;

            case VALID_BRACKET:
                mStrategy = new ValidBracket();
                break;

            case BACK_TRACE_SUBSET:
                mStrategy = new SubSet();
                break;

            case BACK_TRACE_COMBINE:
                mStrategy = new Combine();
                break;

            case BACK_TRACE_PERMUTE:
                mStrategy = new Permute();
                break;

            case BACK_TRACE_COMBINE_TO_SUM:
                mStrategy = new CombineToSum();
                break;

            case BACK_TRACE_REPEAT_COMBINE_TO_SUM:
                mStrategy = new RepeatCombineToSum();
                break;

            case BACK_TRACE_GENERATE_PARENTHESIS:
                mStrategy = new GenerateParenthesis();
                break;

            case DYNAMIC_COIN_CHANGE:
                mStrategy = new CoinChange();
                break;

            case DYNAMIC_LONGEST_INCREASING_SUBSEQUENCE:
                mStrategy = new LongestIncreasingSubsequence();
                break;

            case BACK_WORD_SPLIT:
                mStrategy = new WordSplit();
                break;

            case DYNAMIC_MAX_SUB_ARRAY_SUM:
                mStrategy = new MaxSubArraySum();
                break;

            case DYNAMIC_KNAPSACK:
                mStrategy = new Knapsack();
                break;
        }
        return mStrategy;
    }
}
