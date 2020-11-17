package com.yxm.customview.algorithm.algorithms

import com.yxm.customview.algorithm.algorithms.string.countLetterCurrency
import com.yxm.customview.algorithm.datastruct.LinkedList

/**
 * Created by Myron at 2020/11/14 21:47
 * @email yxmbest@163.com
 * @description 算法和数据结构
 */

fun main() {
    println(countLetterCurrency("There"))
    val linkedList = LinkedList<Int>()
    linkedList.push(1)
    linkedList.push(2)
    linkedList.push(3)
    linkedList.insert(1, 4)
    println(linkedList.get(2))
}