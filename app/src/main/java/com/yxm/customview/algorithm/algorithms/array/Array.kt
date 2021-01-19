package com.yxm.customview.algorithm.algorithms.array

import com.yxm.customview.algorithm.algorithms.sort.swap

/**
 *@author: yxm
 *@time: 2020/11/18
 *@description:数组相关
 */
/**
 * @describe 将数组中的0放到最后，其他元素的顺序不变
 * 思路：定义一个索引k从0开始，遍历数组，如果发现不等于0，交换arr[0]和arr[k]的位置,K++
 * @param array IntArray 时间复杂度O(n)级别
 */
fun removeZero(array: IntArray) {
    var k = 0
    for (i in array.indices) {
        if (array[i] != 0) {
            if (i != k) {
                swap(array, i, k++)
            } else {
                k++
            }
        }
    }
    array.forEach {
        println(it)
    }
}

/**
 * 移除排序数组中重复的元素,返回移除后的数组长度
 * 思路：定义一个K指向第一个元素，从第二个元素开始遍历,如果不等于arr[k]元素，则将K++
 *      保证前K+1个元素都是不重复的,之后将arr[i]赋值到arr[k]上
 *      注意审题！！！！排序数组
 * @param array IntArray
 */
fun removeDuplicateNumbers(array: IntArray): Int {
    if (array.size <= 1) {
        return array.size
    }
    var k = 0
    for (i in 1 until array.size) {
        if (array[i] != array[k]) {
            k++
            if (k != i) {
                array[k] = array[i]
            }
        }
    }
    return k + 1
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

fun twoSum1(array: IntArray, target: Int): IntArray? {
    val map = HashMap<Int, Int>()
    for (i in array.indices) {
        if (map.containsKey(array[i])) {
            return intArrayOf(map[array[i]]!!, i)
        }
        map[target - array[i]] = i
    }
    return null
}
