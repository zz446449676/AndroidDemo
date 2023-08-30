package com.android.testdemo.function;

import com.android.testdemo.function.strategy.Strategy;
import com.android.testdemo.function.strategy.StrategyFactory;

import org.testng.annotations.Test;

public class Algorithm {

    // 测试算法入口
    @Test
    public void testMain() {
        Strategy strategy = StrategyFactory.getStrategy(StrategyFactory.LINK_LIST_REVERSE);
        if (strategy != null) {
            strategy.run();
        }
    }
}
