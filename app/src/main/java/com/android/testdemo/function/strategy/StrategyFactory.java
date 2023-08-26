package com.android.testdemo.function.strategy;

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

    // 字符串反转
    public static final int REVERSAL_STRING = 6;

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

            case REVERSAL_STRING:
                mStrategy = new ReversalString();
                break;
        }
        return mStrategy;
    }
}
