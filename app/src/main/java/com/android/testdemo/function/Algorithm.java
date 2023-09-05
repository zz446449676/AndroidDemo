package com.android.testdemo.function;

import com.android.testdemo.function.strategy.Strategy;
import com.android.testdemo.function.strategy.StrategyFactory;

import org.testng.annotations.Test;

public class Algorithm {

    // 测试算法入口
    @Test
    public void testMain() throws InterruptedException {
        Strategy strategy = StrategyFactory.getStrategy(StrategyFactory.VALID_BRACKET);
        if (strategy != null) {
            strategy.run();
        }
    }
}
