package com.yxm.customview.algorithm.datastruct.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Myron at 2021/1/11 21:00
 * @email yxmbest@163.com
 * @description 二叉树
 */
public class Tree {

    public static class TreeNode<T> {
        public T data;
        public TreeNode<T> left;
        public TreeNode<T> right;

        public TreeNode(T data) {
            this.data = data;
        }
    }

    /**
     * 前序遍历
     */
    public static void preOrderTraverse(TreeNode<String> root) {
        if (root == null) {
            return;
        }
        System.out.println(root.data);
        preOrderTraverse(root.left);
        preOrderTraverse(root.right);
    }

    /**
     * 中序遍历
     */
    public static void midOrderTraverse(TreeNode<String> root) {
        if (root == null) {
            return;
        }
        midOrderTraverse(root.left);
        System.out.println(root.data);
        midOrderTraverse(root.right);
    }

    /**
     * 后序遍历
     */
    public static void postOrderTraverse(TreeNode<String> root) {
        if (root == null) {
            return;
        }
        postOrderTraverse(root.left);
        postOrderTraverse(root.right);
        System.out.println(root.data);
    }

    /**
     * 层次遍历
     */
    public static void levelOrderTraverse(TreeNode<String> root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode<String>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            TreeNode<String> front = queue.poll();
            System.out.println(front.data);
            if(front.left != null){
                queue.offer(front.left);
            }
            if(front.right != null){
                queue.offer(front.right);
            }
        }
    }

    public static int getTreeDepth(TreeNode<String> root){
        if(root == null){
            return 0;
        }
        int leftDepth = getTreeDepth(root.left);
        int rightDepth = getTreeDepth(root.right);
        return Math.max(leftDepth,rightDepth) + 1;
    }

    public static boolean isBalanceTree(TreeNode<String> root){
        //空树是平衡二叉树
        if(root == null)
            return true;
        //左右子树高度相差不能大于1
        int leftDepth = getTreeDepth(root.left);
        int rightDepth = getTreeDepth(root.right);

        //左右两边子树都是平衡二叉树
        return Math.abs(leftDepth - rightDepth) <= 1 &&
                isBalanceTree(root.left) && isBalanceTree(root.right);
    }
}
