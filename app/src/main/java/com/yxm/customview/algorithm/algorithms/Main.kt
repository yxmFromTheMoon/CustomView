package com.yxm.customview.algorithm.algorithms

import com.yxm.customview.algorithm.algorithms.array.twoSum
import com.yxm.customview.algorithm.algorithms.array.twoSum1

/**
 * Created by Myron at 2020/11/14 21:47
 * @email yxmbest@163.com
 * @description
 */

fun main() {
    val array = intArrayOf(1, 3, 4, 5, 12)
    val result = twoSum1(array, 9)
    Hanoi(3,'a','b','c')
}

fun Hanoi(n: Int, a: Char = 'a', b: Char = 'b', c: Char = 'c') {

    if (n == 1) {
        println()
    } else {
        Hanoi(n - 1, a, b, c)

        Hanoi(n - 1, b, c, a)
    }
}