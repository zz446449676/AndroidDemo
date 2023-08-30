package com.android.testdemo.function.structure;

// 链表节点结构体
public class LinkNode {
    private int val;
    public LinkNode next;

    public LinkNode(int val) {
        this.val = val;
    }

    public int getValue() {
        return val;
    }

    public boolean hasNext() {
        return next != null;
    }

    // 遍历链表
    public static void traversal(LinkNode head) {
        if (head == null) return;
        LinkNode flag = head;
        StringBuffer sb = new StringBuffer();

        while (flag.hasNext()) {
            if (flag.getValue() != Integer.MIN_VALUE) {
                sb.append(" --> ");
            }
            flag = flag.next;
            sb.append(flag.val);
        }
        System.out.println("链表遍历为 ： " + sb);
    }

    // 创建个乱序链表
    public static LinkNode createRandomList() {
        // 链表头结点
        LinkNode head = new LinkNode(Integer.MIN_VALUE);
        // 链表节点
        LinkNode node_0 = new LinkNode(10);
        LinkNode node_1 = new LinkNode(13);
        LinkNode node_2 = new LinkNode(2);
        LinkNode node_3 = new LinkNode(6);
        LinkNode node_4 = new LinkNode(4);
        LinkNode node_5 = new LinkNode(11);
        LinkNode node_6 = new LinkNode(9);
        LinkNode node_7 = new LinkNode(7);
        LinkNode node_8 = new LinkNode(15);
        LinkNode node_9 = new LinkNode(5);

        head.next = node_0;
        node_0.next = node_1;
        node_1.next = node_2;
        node_2.next = node_3;
        node_3.next = node_4;
        node_4.next = node_5;
        node_5.next = node_6;
        node_6.next = node_7;
        node_7.next = node_8;
        node_8.next = node_9;
        node_9.next = null;
        return head;
    }
}
