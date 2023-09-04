package com.android.testdemo.function.strategy;

/**
 * 接雨水问题
 * 示例：
 * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出: 6
 *
 * 思路：用对撞指针
 * 1. 什么情况下能接到雨水 ？ （存在一个区间，该区间的值都小于对撞指针的值），在直方图中就是存在一个凹槽；
 * 2. 接到的雨水的量的多少是由谁来决定的？ （对于凹槽来说，决定它高度的不是与它相邻的那个柱子，而是左侧最高柱子和右侧最高柱子中，较矮的那个柱子）
 * 3. 接到多少水？ （由对撞指针中，用最短板的值分别与区间之内的值做减法，并把这些分别相减后的值做累加，就是积水量）
 * 如果不理解，可以自己画个直方图帮助理解
 *
 * 因此我们在指针对撞的过程中，主要任务有两个：
 *
 * 1. 维护一对leftCur（左指针）和rightCur（右指针，以对撞的形式从两边向中间遍历所有的柱子
 * 2. 在遍历的过程中，维护一对 leftMax 和 rightMax，时刻记录当前两侧柱子高度的最大值。
 * 以便在遇到“凹槽”时，结合leftCur与rightCur各自指向的柱子高度，完成凹槽深度（也就是蓄水量）的计算。
 */
public class CatchRainWater implements Strategy {
    private int[] arr = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
    @Override
    public void run() {
        System.out.println("蓄水量为 ： " + trap(arr));
    }

    private int trap(int[] arr) {
        // 此状态不能积水
        if (arr == null || arr.length < 3) return 0;

        // 初始化结果
        int result = 0;

        // 初始化左右指针
        int leftCur = 0;
        int rightCur = arr.length - 1;

        // 初始化对撞指针中最高的柱子
        int leftMax = 0;
        int rightMax = 0;

        // 对撞指针开始遍历
        while (leftCur < rightCur) {
            // 缓存对撞指针所指向的柱子高度
            int left = arr[leftCur];
            int right = arr[rightCur];

            // 以对撞指针中较矮的那个为基准，选定计算目标
            if (left < right) {
                // 更新leftMax
                leftMax = Math.max(left, leftMax);
                // 累加蓄水量
                result += leftMax - left;
                // 移动左指针
                leftCur++;
            } else {
                // 更新rightMax
                rightMax = Math.max(right, rightMax);
                // 增加蓄水量
                result += rightMax - right;
                // 移动右指针
                rightCur--;
            }
        }
        return result;
    }
}
