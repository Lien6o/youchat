package com.youchat.common.leetcode.sort;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/3/4 6:42 下午
 * @version: v1.0
 */
public class TreeSortDFS {

      static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    void preOrder(Node root) {
        if (root == null) return;
        System.out.println(root);
        preOrder(root.left);
        preOrder(root.right);
    }

    void inOrder(Node root) {
        if (root == null) return;
        inOrder(root.left);
        System.out.println(root);
        inOrder(root.right);
    }

    void postOrder(Node root) {
        if (root == null) return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root);

    }

}
