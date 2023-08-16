package com.android.testdemo.function.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个数组，使用归并排序算法将该数组进行升序排列
 *
 * 说明：
 * 采用递归方法，将需要被排序的数组从中间分割为两半，然后再将分割出来的每个子数组各分割为两半。
 * 重复以上操作，直到单个子数组只有一个元素为止，然后再两两合并。
 * 归并排序的时间复杂度为 O(nlog(n))
 */
public class MergeSort implements Strategy{
    @Override
    public void run() {
        // 初始化数组
        final List<Integer> normalArr = new ArrayList<>(Arrays.asList(5, 1, 3, 18, 15, 9, 29, 100, 2, 0, 14));

        System.out.println("最终 MergeSort 排序数组为 ：" + mergeSort(normalArr, 0, normalArr.size()));
    }

    private List<Integer> mergeSort(List<Integer> arr, int start, int end) {
        // 拆分到了最小单元
        if (arr.subList(start, end).size() <= 1) return arr.subList(start, end);

        // 递归进行切割
        int mid = (start + end) / 2;

        // 对数组进行递归拆分
        List<Integer> leftArr = mergeSort(arr, start, mid);
        List<Integer> rightArr = mergeSort(arr, mid, end);

        // 将数组进行两两合并
        int leftArrLink = 0;
        int rightArrLink = 0;
        List<Integer> resultArr = new ArrayList<>();
        while (leftArrLink < leftArr.size() && rightArrLink < rightArr.size()) {
            if (leftArr.get(leftArrLink) < rightArr.get(rightArrLink)) {
                resultArr.add(leftArr.get(leftArrLink++));
            } else {
                resultArr.add(rightArr.get(rightArrLink++));
            }

            // 若其中一个子数组首先被合并完全，则直接拼接另一个子数组的剩余部分
            if (leftArrLink == leftArr.size()) {
                resultArr.addAll(rightArr.subList(rightArrLink, rightArr.size()));
            } else if (rightArrLink == rightArr.size()) {
                resultArr.addAll(leftArr.subList(leftArrLink, leftArr.size()));
            }
        }
        return resultArr;
    }
}
