package com.yxm.customview.algorithm.algorithms

import com.yxm.customview.algorithm.datastruct.binarytree.PriorityQueue
import com.yxm.customview.algorithm.datastruct.binarytree.Tree
import kotlin.random.Random

/**
 * Created by Myron at 2020/11/14 21:47s
 * @email yxmbest@163.com
 * @description 算法和数据结构
 */

fun main() {

    val arr = IntArray(10)
    arr.forEachIndexed { index, i ->
        arr[index] = Random.nextInt(0,100)
    }
    val queue = PriorityQueue(arr,arr.size)

    for (i in 1 until queue.datas.size){
        println(queue.pop())
    }
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