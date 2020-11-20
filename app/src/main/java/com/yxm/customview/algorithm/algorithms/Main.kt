package com.yxm.customview.algorithm.algorithms

import com.yxm.customview.algorithm.algorithms.array.twoSum
import com.yxm.customview.algorithm.algorithms.array.twoSum1

/**
 * Created by Myron at 2020/11/14 21:47
 * @email yxmbest@163.com
 * @description 算法和数据结构
 */

fun main() {
    val array = intArrayOf(1,3,4,5,12)
    val result = twoSum1(array,9)
    result?.forEach {
        println(it)
    }
}