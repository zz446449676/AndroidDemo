package com.android.testdemo.function.strategy;

/**
 * 算法工厂，动态创建算法实例
 */
public class StrategyFactory {
    // 递归遍历对一个有序数组进行查找
    public static final int RECURSION_SEARCH_ORDERED_ARRAY = 0;

    // 选择排序算法对数组进行排序
    public static final int SELECT_SORT = 1;

    public static Strategy getStrategy(int strategy) {
        Strategy mStrategy = null;
        switch (strategy) {
            case RECURSION_SEARCH_ORDERED_ARRAY:
                mStrategy = new RecursionSearchOrderedArray();
                break;

            case SELECT_SORT:
                mStrategy = new SelectSort();
                break;
        }
        return mStrategy;
    }
}
