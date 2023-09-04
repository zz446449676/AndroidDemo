package com.android.testdemo.function.strategy;

import com.android.testdemo.function.structure.TreeNode;

/**
 * 寻找二叉树最近公共祖先
 * 思路：递归遍历每个节点的有效汇报个数
 * 由于一个结点最多有两个孩子，它拿到的有效汇报个数也无非只有0、1、2这三种可能性
 * 1. 有效汇报个数为0，则p和q完全不存在与当前结点的后代中，当前结点一定不是最近公共祖先
 * 2. 若有效汇报个数为2，则意味着 p和q 所在的两个分支刚好在当前结点交错了，当前结点就是p和q的最近公共祖先
 * 3. 若有效汇报个数为1，这里面蕴含着三种情况：
 *    a. 当前结点的左子树/右子树中，包含了p或者q中的一个。此时我们需要将p或者q所在的那棵子树的根结点作为有效结点上报，继续向上去寻找p和q所在分支的交错点。
 *    b. 当前结点的左子树/右子树中，同时包含了 p 和 q。在有效汇报数为1的前提下，这种假设只可能对应一种情况，那就是p和q之间互为父子关系。
 *    此时我们仍然是需要将p和q所在的那个子树的根结点（其实就是p或者q中作为爸爸存在那个）作为有效结点给上报上去。
 *
 * 结论：
 * 若有效汇报个数为2，直接返回当前结点
 * 若有效汇报个数为1，返回1所在的子树的根结点
 * 若有效汇报个数为0，则返回空（空就是无效汇报）
 */
public class LowestCommonAncestor implements Strategy {
   // 要找到两个子节点
    int left = 6;
    int right = 5;
    @Override
    public void run() {
        TreeNode ancestor = dfsSearchAncestor(TreeNode.createTree());
        if (ancestor == null) {
            System.out.println("没有找到符合条件的父节点");
        } else {
            System.out.println("公共最近祖先为 ： " + ancestor.getValue());
        }
    }

    // 递归遍历
    private TreeNode dfsSearchAncestor(TreeNode root) {
        if (root == null) return null;
        // 若当前结点不存在（意味着无效）或者等于p/q（意味着找到目标），则直接返回
        if (root.getValue() == left || root.getValue() == right) return root;

        // 向左右子树去找left和right
        TreeNode leftNode = dfsSearchAncestor(root.left);
        TreeNode rightNode = dfsSearchAncestor(root.right);

        // 有效上报为2，则表示找到
        if (leftNode != null && rightNode != null) return root;

        // 如果左子树和右子树其中一个包含了p或者q，则把对应的有效子树汇报上去，等待进一步的判断；否则返回空
        if (leftNode != null) return leftNode;

        // 如果左右子树都为空，表示没找到，返回空
        return rightNode;
    }
}
