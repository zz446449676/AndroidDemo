package com.android.testdemo.function.strategy.backTrace;

import com.android.testdemo.function.strategy.Strategy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 回溯算法生成括号
 * 你写一个算法，输入是一个正整数 n，输出是 n 对儿括号的所有合法组合
 * 比如说，输入 n=3，输出为如下 5 个字符串：
 * "((()))",
 * "(()())",
 * "(())()",
 * "()(())",
 * "()()()"
 */
public class GenerateParenthesis implements Strategy {
    private final List<String> res = new LinkedList<>();
    // 最初的括号集合，对里面的括号进行全排列
    private char[] initParenthesis;

    // 全排列过程中的路径记录
    LinkedList<Character> track = new LinkedList<>();

    // 记录该括号是否被使用
    boolean[] used;
    @Override
    public void run() {
        System.out.println(generateParenthesis(3));
    }

    private List<String> generateParenthesis(int n) {
        init(n);
        backTrace(initParenthesis);
        return res;
    }

    // 对括号列表进行初始化
    private void init(int n) {
        initParenthesis = new char[2*n];
        used = new boolean[2*n];
        for (int i = 0; i < n; i++) {
            initParenthesis[i] = '(';
            initParenthesis[i+3] = ')';
        }
    }

    // 开始回溯
    private void backTrace(char[] parenthesis) {
        // 判断是否符合括号规范,退出回溯
        if (track.size() == parenthesis.length) {
            if (isValid(track)) {
                StringBuilder sb = new StringBuilder();
                for (Character ch : track) {
                    sb.append(ch);
                }
                res.add(sb.toString());
            }
            return;
        }

        for (int i = 0; i < parenthesis.length; i++) {
            // 如果括号已经被使用，则跳过
            if (used[i]) continue;

            // 因为有相同元素, 所以需要新添加的剪枝逻辑，固定相同的元素在排列中的相对位置
            if (i > 0 && parenthesis[i] == parenthesis[i - 1] && !used[i - 1]) {
                continue;
            }

            // 做选择，记录路径
            used[i] = true;
            track.add(parenthesis[i]);

            // 进入下一层回溯
            backTrace(parenthesis);

            // 回退
            used[i] = false;
            track.removeLast();
        }
    }

    private boolean isValid(List<Character> arrChar) {
        // 结合题意，空字符串无条件判断为 true
        if (arrChar == null || arrChar.size() == 0) return true;

        // 用于括号匹配
        HashMap<Character, Character> matchMap = new HashMap<>();
        matchMap.put(')', '(');

        // 初始化一个栈 stack
        Stack<Character> stack = new Stack<>();

        char temp;

        // 开始遍历字符串
        for (int i = 0; i < arrChar.size(); i++) {
            temp = arrChar.get(i);
            // 判断是否是左括号,是则把括号入栈。若不是左括号，则必须是和栈顶的左括号相配对的右括号
            if (temp == '(') {
                stack.push(temp);
            } else {
                // 若栈为空，或栈顶的左括号没有和当前字符匹配上，那么判为无效
                if (stack.empty() || stack.pop() != matchMap.get(temp)) {
                    return false;
                }
            }
        }
        // 若所有的括号都能配对成功，那么最后栈应该是空的
        return stack.empty();
    }
}
