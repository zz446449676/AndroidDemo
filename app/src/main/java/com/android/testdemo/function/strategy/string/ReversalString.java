package com.android.testdemo.function.strategy.string;

import com.android.testdemo.function.strategy.Strategy;

/**
 * 反转字符串算法
 * 输入 abc 返回 cba
 */
public class ReversalString implements Strategy {
    @Override
    public void run() {
        String origString = "zhangy";

//        最直接的写法
//        StringBuffer sb = new StringBuffer(origString);
//        sb.reverse();

//        普通算法反转
        char[] charArr = origString.toCharArray();
        int start = 0;
        int end = charArr.length - 1;
        char temp;
        while (start < end) {
            temp = charArr[start];
            charArr[start] = charArr[end];
            charArr[end] = temp;
            start++;
            end--;
        }
        System.out.println("反转前的数组为 ： " + origString);
        System.out.println("反转后的数组为 ： " + String.valueOf(charArr));
    }
}
