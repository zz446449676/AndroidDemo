package com.android.testdemo.function.structure;

import java.util.ArrayList;

// 二叉树结构体
public class TreeNode {
    private int val;
    public TreeNode left = null;
    public TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }

    public int getValue() { return val; }

    // 二叉树前序遍历
    public static ArrayList<Integer> firstTraversal(TreeNode root, ArrayList<Integer> arr) {
        if (root == null || arr == null) return null;
        arr.add(root.val);

        firstTraversal(root.left, arr);
        firstTraversal(root.right, arr);
        return arr;
    }

    // 二叉树中序遍历
    public static ArrayList<Integer> middleTraversal(TreeNode root, ArrayList<Integer> arr) {
        if (root == null || arr == null) return null;

        middleTraversal(root.left, arr);
        arr.add(root.val);
        middleTraversal(root.right, arr);
        return arr;
    }

    // 二叉树后序遍历
    public static ArrayList<Integer> posteriorTraversal(TreeNode root, ArrayList<Integer> arr) {
        if (root == null || arr == null) return null;

        posteriorTraversal(root.left, arr);
        posteriorTraversal(root.right, arr);
        arr.add(root.val);
        return arr;
    }

    public static TreeNode createTree() {
        TreeNode root = new TreeNode(0);
        TreeNode node_1 = new TreeNode(1);
        TreeNode node_2 = new TreeNode(2);
        TreeNode node_3 = new TreeNode(3);
        TreeNode node_4 = new TreeNode(4);
        TreeNode node_5 = new TreeNode(5);
        TreeNode node_6 = new TreeNode(6);
        TreeNode node_7 = new TreeNode(7);
        TreeNode node_8 = new TreeNode(8);
        TreeNode node_9 = new TreeNode(9);
        TreeNode node_10 = new TreeNode(10);

        root.left = node_1;
        root.right = node_2;

        node_1.left = node_3;
        node_1.right = node_4;

        node_2.left = node_5;
        node_2.right = node_6;

        node_3.left = node_7;
        node_3.right = node_8;

        node_4.left = node_9;
        node_4.right = node_10;
        return root;
    }
}
