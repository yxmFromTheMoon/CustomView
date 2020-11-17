package com.yxm.customview.algorithm.algorithms.sort

/**
 *@author: yxm
 *@time: 2020/11/17
 *@description:
 */

fun bubbleSort(array: IntArray) {
    val length = array.size
    for (i in 0 until length - 1) {
        for (j in 0 until length - i - 1) {
            if (array[j] > array[j + 1]) {
                swap(array, j, j + 1)
            }
        }
    }
    array.forEach {
        println(it)
    }
}

fun swap(array: IntArray, i: Int, j: Int) {
    val iValue = array[i]
    array[i] = array[j]
    array[j] = iValue
}
