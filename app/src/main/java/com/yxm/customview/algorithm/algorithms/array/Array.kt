package com.yxm.customview.algorithm.algorithms.array

import com.yxm.customview.algorithm.algorithms.sort.swap

/**
 *@author: yxm
 *@time: 2020/11/18
 *@description:数组相关
 */
/**
 * @describe 将数组中的0放到最后，其他元素的顺序不变
 * @param array IntArray 时间复杂度O(n)级别
 */
fun removeZero(array: IntArray) {
    var k = 0
    for (i in array.indices) {
        if (array[i] != 0) {
            swap(array, i, k)
            k++
        }
    }
    array.forEach {
        println(it)
    }
}

/**
 * 可优化
 * @param array IntArray
 * @param sum Int
 * @return IntArray
 */
fun twoSum(array: IntArray, sum: Int): IntArray {
    val result = IntArray(2) { 0 }
    for (i in array.indices) {
        for (j in i until array.size) {
            if (array[i] + array[j] == sum) {
                result[0] = i
                result[1] = j
            }
        }
    }
    return result
}
