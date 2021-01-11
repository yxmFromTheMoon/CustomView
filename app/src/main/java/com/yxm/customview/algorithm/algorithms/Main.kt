package com.yxm.customview.algorithm.algorithms

import com.yxm.customview.algorithm.datastruct.binarytree.Tree

/**
 * Created by Myron at 2020/11/14 21:47s
 * @email yxmbest@163.com
 * @description 算法和数据结构
 */

fun main() {

    val A = Tree.TreeNode("a")
    val B = Tree.TreeNode("b")
    val C = Tree.TreeNode("c")
    val D = Tree.TreeNode("d")
    val E = Tree.TreeNode("e")
    val F = Tree.TreeNode("f")
    val G = Tree.TreeNode("g")

    A.left = B
    A.right = C
    B.left = D
    B.right = E
    C.right = F
    F.right = G

    //Tree.preOrderTraverse(A)
    //Tree.midOrderTraverse(A)
    //Tree.postOrderTraverse(A)
    println(Tree.isBalanceTree(A))
}

/**
 * 汉诺塔问题
 * @param n Int
 * @param a Char
 * @param b Char
 * @param c Char
 */
fun Hanoi(n: Int, a: Char = 'a', b: Char = 'b', c: Char = 'c') {

    if (n == 1) {
        println()
    } else {
        Hanoi(n - 1, a, b, c)

        Hanoi(n - 1, b, c, a)
    }
}