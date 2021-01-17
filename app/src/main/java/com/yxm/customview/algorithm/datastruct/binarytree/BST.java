package com.yxm.customview.algorithm.datastruct.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Myron at 2021/1/11 21:00
 *
 * @email yxmbest@163.com
 * @description 二叉搜索树
 */
public class BST<K, V> {

    private TreeNode root = null;
    private int count = 0;

    public BST() {
        root = null;
        count = 0;
    }

    public class TreeNode {
        public K key;
        public V value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(K key, V value) {
            this.value = value;
            this.key = key;
            this.left = this.right = null;
        }

        public TreeNode(TreeNode node) {
            this.key = node.key;
            this.value = node.value;
            this.left = node.left;
            this.right = node.right;
        }
    }

    /**
     * 前序遍历
     */
    public void preOrderTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.value);
        preOrderTraverse(root.left);
        preOrderTraverse(root.right);
    }

    /**
     * 中序遍历
     */
    public void midOrderTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        midOrderTraverse(root.left);
        System.out.println(root.value);
        midOrderTraverse(root.right);
    }

    /**
     * 后序遍历
     */
    public void postOrderTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrderTraverse(root.left);
        postOrderTraverse(root.right);
        System.out.println(root.value);
    }

    /**
     * 层次遍历
     */
    public void levelOrderTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode front = queue.poll();
            System.out.println(front.value);
            if (front.left != null) {
                queue.offer(front.left);
            }
            if (front.right != null) {
                queue.offer(front.right);
            }
        }
    }

    public int getTreeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = getTreeDepth(root.left);
        int rightDepth = getTreeDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    public boolean isBalanceTree(TreeNode root) {
        //空树是平衡二叉树
        if (root == null)
            return true;
        //左右子树高度相差不能大于1
        int leftDepth = getTreeDepth(root.left);
        int rightDepth = getTreeDepth(root.right);

        //左右两边子树都是平衡二叉树
        return Math.abs(leftDepth - rightDepth) <= 1 &&
                isBalanceTree(root.left) && isBalanceTree(root.right);
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    public boolean contains(K key) {
        return contains(root, key);
    }

    public TreeNode search(K key) {
        return search(root, key);
    }

    public TreeNode min() {
        return min(root);
    }

    public TreeNode max() {
        return max(root);
    }

    public void removeMin() {
        if (root != null) {
            removeMin(root);
        }
    }

    public void removeMax() {
        if (root != null) {
            removeMax(root);
        }
    }

    public void remove(K key) {
        remove(root, key);
    }

    /**
     * 删除节点
     *
     * @param root
     */
    private TreeNode remove(TreeNode root, K key) {
        if (root == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) key;
        int cmp = k.compareTo(root.key);
        if (cmp < 0) {
            root.left = remove(root.left, key);
            return root;
        } else if (cmp > 0) {
            root.right = remove(root.right, key);
            return root;
        } else {
            if (root.left == null) {
                count--;
                return root.right;
            }
            if (root.right == null) {
                count--;
                return root.left;
            }
            //左右节点都不为空
            //找到后继节点，待删除节点的右子树的最小节点，复制一份
            TreeNode successor = new TreeNode(min(root.right));
            count++;
            //删除待删除节点的右子树的最小节点，将successor的右子树指向它
            successor.right = removeMin(root.right);
            successor.left = root.left;
            count--;
            return successor;
        }
    }

    /**
     * 删除最小节点
     *
     * @param node
     * @return
     */
    private TreeNode removeMin(TreeNode node) {
        //找到最小值
        if (node.left == null) {
            count--;
            return node.right;
        }
        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 删除最大节点
     *
     * @param node
     * @return
     */
    private TreeNode removeMax(TreeNode node) {
        //找到最小值
        if (node.right == null) {
            count--;
            return node.left;
        }
        node.right = removeMin(node.right);
        return node;
    }

    /**
     * 找到最大值,也可递归实现
     *
     * @param root
     * @return
     */
    private TreeNode max(TreeNode root) {
        if (root == null) {
            return null;
        }
        //递归
//        if(root.left == null){
//            return root.value;
//        }
//        return max(root.right);
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    /**
     * 找到最小值
     *
     * @param root
     * @return
     */
    private TreeNode min(TreeNode root) {
        if (root == null) {
            return null;
        }
        //递归
//        if(root.left == null){
//            return root.value;
//        }
//        return max(root.left);
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    /**
     * 向以root为根的二叉搜索书中插入节点（key,value）
     *
     * @param root
     * @param key
     * @param value
     * @return
     */
    private TreeNode insert(TreeNode root, K key, V value) {
        if (root == null) {
            return new TreeNode(key, value);
        }
        if (key == root.key) {
            root.value = value;
        } else {
            @SuppressWarnings("unchecked")
            Comparable<? super K> k = (Comparable<? super K>) key;
            int cmp = k.compareTo(root.key);
            if (cmp < 0)
                root.left = insert(root.left, key, value);
            else if (cmp > 0)
                root.right = insert(root.right, key, value);
        }
        count++;
        return root;
    }

    /**
     * 是否包含某个元素
     *
     * @param node
     * @param key
     * @return
     */
    private boolean contains(TreeNode node, K key) {
        if (node == null) {
            return false;
        }
        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) key;
        int cmp = k.compareTo(root.key);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return contains(node.left, key);
        } else {
            return contains(node.right, key);
        }
    }


    /**
     * 查找某个元素
     *
     * @param root
     * @param key
     * @return
     */
    private TreeNode search(TreeNode root, K key) {
        if (root == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) key;
        int cmp = k.compareTo(root.key);
        if (cmp == 0) {
            return root;
        } else if (cmp < 0) {
            return search(root, key);
        } else {
            return search(root, key);
        }
    }

}
