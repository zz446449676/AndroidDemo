package com.android.testdemo.function.strategy.backTrace;

import com.android.testdemo.function.strategy.Strategy;

import java.util.LinkedList;

/**
 * 回溯算法单词拆分
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典，请判断是否可以利用字典中出现的单词拼接出 s；
 * 例如：s = "applepenapple", wordDict = ["apple", "pen"]  输出 true；
 * 思路：
 * 因为可以重复使用字典里的单词，所以用回溯可重复的全排列，以 s = helloword 为结束条件进行回溯算法
 */
public class WordSplit implements Strategy {
    // 回溯轨迹
    LinkedList<String> trace = new LinkedList<>();
    LinkedList<String> wordDict = new LinkedList<>();

    // 结果
    LinkedList<String> result = new LinkedList<>();
    @Override
    public void run() {
        wordSplit("applepenapple");
    }

    private void wordSplit(String target) {
        // 初始化
        wordDict.add("apple");
        wordDict.add("pen");

        // 执行拆分
        backTrace(wordDict, target);

        // 输出结果
        if (result.size() > 0) {
            System.out.println("结果字符串为 ：" + result.getFirst());
            System.out.println("拆分结果 ：" + result.getFirst().equals(target));
        } else {
            System.out.println("拆分结果 ：" + false);
        }
    }

    private void backTrace(LinkedList<String> wordDict, String target) {
        // 结束逻辑
        StringBuilder sb = new StringBuilder();
        for (String str : trace) {
            sb.append(str);
        }
        // 拼接成功
        if (sb.toString().equals(target)) {
            result.add(sb.toString());
        }
        // 拼接失败
        if (sb.toString().length() > target.length()) return;

        for (int i = 0; i < wordDict.size(); i++) {
            // 做选择
            trace.add(wordDict.get(i));

            // 回溯
            backTrace(wordDict, target);

            // 回退选择
            trace.removeLast();
        }
    }
}
