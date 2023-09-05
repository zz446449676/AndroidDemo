package com.android.testdemo.function.strategy;

import java.util.HashMap;
import java.util.Stack;

/**
 * 有效括号匹配问题, 题目中若涉及括号问题，则很有可能和栈相关
 * 题目描述：给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * 示例 1:
 * 输入: "()[]{}", "{[]}"
 * 输出: true
 */
public class ValidBracket implements Strategy {
    @Override
    public void run() {
        String str1 = "()[]{}";
        String str2 = "{[]}";
        String str3 = "{()[}]";
        System.out.println("该字符串括号是否有效 ： " + isValid(str2));
    }

    private boolean isValid(String str) {
        // 结合题意，空字符串无条件判断为 true
        if (str == null || str.length() == 0) return true;

        // 用于括号匹配
        HashMap<Character, Character> matchMap = new HashMap<>();
        matchMap.put(')', '(');
        matchMap.put('}', '{');
        matchMap.put(']', '[');

        // 把String转为char[]
        char[] arrChar = str.toCharArray();

        // 初始化一个栈 stack
        Stack<Character> stack = new Stack<>();

        char temp;

        // 开始遍历字符串
        for (int i = 0; i < arrChar.length; i++) {
            temp = arrChar[i];
            // 判断是否是左括号,是则把括号入栈。若不是左括号，则必须是和栈顶的左括号相配对的右括号
            if (temp == '(' || temp == '{' || temp == '[') {
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
