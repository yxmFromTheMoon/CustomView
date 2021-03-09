package com.yxm.customview.algorithm.algorithms

import com.yxm.customview.algorithm.algorithms.array.*
import com.yxm.customview.algorithm.algorithms.linkedlist.*
import com.yxm.customview.algorithm.algorithms.sort.swap
import com.yxm.customview.algorithm.algorithms.string.reverseString

/**
 * Created by Myron at 2020/11/14 21:47s
 * @email yxmbest@163.com
 * @description 算法和数据结构
 */

fun main() {
    val arr = intArrayOf(1,2,3,2,2,2,5,4,2)
    println(MoreThanHalfNum_Solution(arr))
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

/**
 * 堆排序version3，优化,原地操作，空间复杂度为0（1）
 * @param arr IntArray
 * @param n Int
 */
fun heapSort(arr: IntArray, n: Int) {

    for (index in (n - 1) / 2 downTo 0) {
        shiftDown(arr, n, index)
    }
    for (i in n - 1 downTo 0) {
        swap(arr, i, 0)
        shiftDown(arr, i, 0)
    }
    arr.forEach {
        print(",$it")
    }
}


fun shiftDown(datas: IntArray, count: Int, n: Int) {
    //保证二叉树有孩子，只需判断有左孩子即可
    var index = n
    while (2 * index + 1 < count) {
        //index位置节点的左孩子
        var j = 2 * index + 1
        //如果有右孩子 && 右孩子大于左孩子，将要交换的索引变为右孩子
        //的索引
        if (j + 1 < count && datas[j + 1] > datas[j]) {
            j += 1
        }
        //该节点与左右孩子中最大的比较,如果大于左右节点则直接break
        if (datas[index] >= datas[j]) {
            break
        }
        //交换
        swap(datas, j, index)
        //更新节点的索引
        index = j
    }
}