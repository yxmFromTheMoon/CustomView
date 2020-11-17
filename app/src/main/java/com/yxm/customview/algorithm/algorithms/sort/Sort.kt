package com.yxm.customview.algorithm.algorithms.sort

/**
 *@author: yxm
 *@time: 2020/11/17
 *@description:
 */

fun swap(array: IntArray, i: Int, j: Int) {
    val iValue = array[i]
    array[i] = array[j]
    array[j] = iValue
}

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

fun selectSort(array: IntArray) {
    val length = array.size
    for (i in 0 until length) {
        var minIndex = i
        for (j in i + 1 until length) {
            if (array[j] < array[minIndex]) {
                minIndex = j
            }
        }
        swap(array, i, minIndex)
    }
    array.forEach {
        println(it)
    }
}

