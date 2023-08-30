package com.android.testdemo.function.strategy;

import com.android.testdemo.function.structure.LinkNode;

// 用快慢指针移除单链表中的倒数第 N 个数
public class RemoveNthFromEnd implements Strategy {
    @Override
    public void run() {
        // 构造链表
        LinkNode head = LinkNode.createRandomList();
        System.out.println("删除前的链表如下：");
        LinkNode.traversal(head);

        deleteTarget(head, 4);
    }
    private void deleteTarget(LinkNode head, int target) {
        if (head == null || target <= 0) {
            System.out.println("数据异常");
            return;
        }

        // 计数器
        int count = 0;
        // 慢指针
        LinkNode slow = head;
        // 快指针
        LinkNode fast = head;

        // 快指针先走target个节点,每走一个节点，计数器加一，当计数器大于或等于target时，快指针走一个，慢指针跟着走一个
        // 最终快指针到末尾后，慢指针指向的元素正是倒数第target元素的前一个元素。
        while (fast.hasNext()) {
            fast = fast.next;
            count++;
            if (count > target) {
                slow = slow.next;
            }
        }
        // 处理异常，当target大于链表长度
        if (count < target) {
            System.out.println("所删除的数据超出长度");
            return;
        } else {
            // 删除节点
            LinkNode temp = slow.next;
            slow.next = temp.next;
            temp.next = null;
        }
        System.out.println("删除倒数第 " + target + " 个元素后的链表如下 ：");
        LinkNode.traversal(head);
    }
}
