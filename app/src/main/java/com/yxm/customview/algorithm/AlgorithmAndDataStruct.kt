package com.yxm.customview.algorithm

/**
 * Created by Myron at 2020/11/14 21:47
 * @email yxmbest@163.com
 * @description 算法和数据结构
 */

fun main() {
    println(reverseString2("abcdeefg"))
}

/**
 * 反转字符串，源码算法
 * @param s String
 * @return CharArray
 */
fun reverseString(s: String): CharArray {
    val n: Int = s.length - 1
    val value = s.toCharArray()
    for (j in n - 1 shr 1 downTo 0) {
        val k = n - j
        val cj: Char = value[j]
        val ck: Char = value[k]
        value[j] = ck
        value[k] = cj
    }
    return value
}

fun reverseString2(s: String):CharArray {
    val n = s.length
    val value = s.toCharArray()
    for (i in 0 until n / 2) {
        val k = n - 1 - i
        val ci: Char = value[i]
        val ck: Char = value[k]
        value[i] = ck
        value[k] = ci
    }
    return value
}