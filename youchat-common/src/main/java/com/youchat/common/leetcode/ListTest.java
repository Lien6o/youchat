package com.youchat.common.leetcode;

import lombok.Data;

public class ListTest {
    @Data
    public class LinkedNode{
        private Integer value;
        public LinkedNode next;

        public LinkedNode(Integer value) {
            this.value = value;
        }
    }

    public LinkedNode reverse(LinkedNode head){
        LinkedNode cur = head;
        LinkedNode pre = null;
        while (cur != null) {
            LinkedNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    public LinkedNode merge(LinkedNode l1, LinkedNode l2) {
        LinkedNode head = new LinkedNode(-1);
        LinkedNode cur = head;
        while (l1 != null && l2 != null) {
            if (l1.value > l2.value) {
                cur.next = l1;
                l2 = l2.next;
            } else {
                cur.next = l1;
                l1 = l1.next;
            }
            cur = cur.next;
        }
        cur.next = l1 != null ? l1 : l2;
        return head.next;
    }



}
