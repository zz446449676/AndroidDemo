package com.android.testdemo.function.strategy;

import com.android.testdemo.function.structure.LinkNode;

// 链表反转，本质是对链表的指针进行修改
public class LinkListReverse implements Strategy {
    @Override
    public void run() {
        LinkNode head = LinkNode.createRandomList();
        System.out.println("删除前的链表如下：");
        LinkNode.traversal(head);

        reverse(head);
    }

    // 准备三个指针，分别对中间指针，和前驱指针进行反转
    private void reverse(LinkNode head) {
        if (head == null) return;
        LinkNode per = null;
        // 头节点需要单独记录一个，最后放到末尾
        LinkNode head_node = head;
        LinkNode cur = head.next;
        LinkNode next = head.next;

        while (cur != null) {
            next = next.next;
            cur.next = per;
            per = cur;
            cur = next;
        }
        // 处理头结点
        head_node.next = per;
        System.out.println("反转后的链表为： ");
        LinkNode.traversal(head_node);
    }
}
