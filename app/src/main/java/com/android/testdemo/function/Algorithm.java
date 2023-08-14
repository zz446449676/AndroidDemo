package com.android.testdemo.function;

import com.android.testdemo.function.strategy.RecursionSearchOrderedArray;

import org.testng.annotations.Test;

public class Algorithm {

    // 测试算法入口
    @Test
    public void testMain() {
        // RecursionSearchOrderedArray 递归查找有序数组的值
        System.out.println("Yikw Result : " + new RecursionSearchOrderedArray().run());
    }
}
