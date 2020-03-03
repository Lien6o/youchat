package com.youchat.common.leetcode;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/3/2 5:39 下午
 * @version: v1.0
 * 1. 找出两个链表的交点
 * 2. 链表反转
 * 3. 归并两个有序的链表
 * 4. 从有序链表中删除重复节点
 * 5. 删除链表的倒数第 n 个节点
 * 6. 交换链表中的相邻结点
 * 7. 链表求和
 * 8. 回文链表
 * 9. 分隔链表
 * 10. 链表元素按奇偶聚集
 */
public class List {

     class ListNode {
         int val;
         ListNode next;

     }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode l1 = headA, l2 = headB;
        while (l1 != l2) {
            l1 = (l1 == null) ? headB : l1.next;
            l2 = (l2 == null) ? headA : l2.next;
        }
        return l1;
    }
}
