package com.android.testdemo.function.strategy;

import com.android.testdemo.function.structure.LinkNode;

/**
 * K 个一组翻转链表
 * 示例： 给你这个链表：1->2->3->4->5
 * 当 k = 2 时，应当返回: 2->1->4->3->5
 * 当 k = 3 时，应当返回: 3->2->1->4->5
 *
 * 和 LinkListReverse 翻转差不多，就是多了个对 K 的计数
 */
public class ReverseKGroupLinkList implements Strategy {
    @Override
    public void run() {
        LinkNode head = LinkNode.createRandomList();
        System.out.println("翻转前的链表为 ： ");
        LinkNode.traversal(head);

        reverseKGroup(head, 3);
    }

    private void reverseKGroup(LinkNode head, int k) {
        LinkNode dummy = new LinkNode();
        dummy.next = head;
        // pre 用来缓存当前这一截k个结点的链表前驱的那个结点（不丢头）
        LinkNode pre = dummy;
        // start 指向k个一组的局部链表中的第一个
        LinkNode start = head.next;
        // end 指向k个一组的局部链表中的最后一个
        LinkNode end = head.next;
        // next用来缓存当前这一截k个结点的链表后继的那个结点（不丢尾）
        LinkNode next = head.next;

        // 当后继结点存在时，持续遍历
        while (next != null) {
            // 找到k个结点中的最后一个
            for (int i = 1; i < k; i++) {
                if (end != null) end = end.next;
            }
            // 如果不满k个，则直接返回
            if (end == null) break;

            // 缓存这k个结点的后继结点
            next = end.next;
            //为了配合reverse方法，需要把end.next值为空
            end.next = null;
            // 手动把end指向start（因为下面reverse完start就会改变）
            end = start;
            // 以start为起点翻转k个结点
            start = reverse(start);
            // 接上尾巴
            end.next = next;
            // 接上头
            pre.next = start;
            // pre、start、end一起前进，为下一次翻转做准备
            pre = end;
            start = next;
            end = start;
        }
        // 处理头结点
        head.next = dummy.next;
        System.out.println("以 " + k + " 为一组翻转后的链表为 ：");
        // dummy.next指向的永远是链表的第一个结点
        LinkNode.traversal(head);
    }

    // 指针翻转
    private LinkNode reverse(LinkNode head) {
        if (head == null) return null;
        // 初始化 pre, cur, next指针
        LinkNode pre = null;
        LinkNode cur = head;
        LinkNode next = null;

        while (cur != null) {
            next = cur.next;
            // 进行指针翻转
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        // 翻转到最后，pre会指向最末尾的结点，也就是翻转后的第一个结点
        return pre;
    }
}
