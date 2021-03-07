package com.yxm.customview.algorithm.algorithms.getoffer

import java.util.*

/**
 * Created by Myron at 2021/3/5 20:10
 * @email yxmbest@163.com
 * @description 剑指offer 题集
 */

/**
 * 01 二维数组中的查找
 * 在一个二维数组中（每个一维数组的长度相同），
 * 每一行都按照从左到右递增的顺序排序，
 * 每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 *
 * 二分查找思路，从左下角开始
 */
fun find(target: Int, array: Array<IntArray>): Boolean {
    // write code here
    val length = array.size
    if (length == 0) {
        return false
    }
    val cols = array[0].size
    if (cols == 0) {
        return false
    }
    var column = 0
    var row = array.size - 1
    while (row >= 0 && column < array[0].size) {
        when {
            array[row][column] == target -> {
                return true
            }
            array[row][column] > target -> {
                row--
            }
            else -> {
                column++
            }
        }
    }
    return false
}

/**
 * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。
 * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 * @param s String
 * @return String
 */
fun replaceSpace(s: String): String {
    // write code here
    val sb = StringBuilder()
    for (c in s) {
        if (c == ' ') {
            sb.append("%20")
        }else{
            sb.append(c)
        }
    }
    return sb.toString()
}

/**
 * 牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。
 * 同事Cat对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，但却读不懂它的意思。
 * 例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，
 * 正确的句子应该是“I am a student.”。Cat对一一的翻转这些单词顺序可不在行，
 * 你能帮助他么？
 * @param str String
 * @return String?
 *
 * 还可用滑动窗口,遇到“ ”就反转字符串
 */
fun ReverseSentence(str: String): String? {
    if (str.isEmpty() || str.trim { it <= ' ' } == "") {
        return str
    }
    val result = java.lang.StringBuilder()
    val stack = Stack<String>()
    val array = str.split(" ".toRegex()).toTypedArray()
    for (s in array) {
        stack.push(s)
    }
    while (!stack.isEmpty()) {
        result.append(stack.pop()).append(" ")
    }
    return result.toString().substring(0, result.length - 1)
}


fun main() {
    print(ReverseSentence("student. a am I"))
}