package com.yxm.customview.algorithm.algorithms.string

import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf

/**
 *@author: yxm
 *@time: 2020/11/17
 *@description:字符串相关算法
 */

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

fun reverseString2(s: String): CharArray {
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

/**
 * 字符串转int
 * 注意边界
 * @param str String
 * @return Int
 */
fun stringToInteger(str: String): Int {
    /**
     * result记录结果，在下面的计算中会一直是个负数
     * 假如说，我们的字符串是一个整数"7"，那么在返回这个值之前result保存的是-7
     * 这个其实是为了保持正数和负数在下面计算的时候，有一致的逻辑（不需要给正数和负数分别编写计算逻辑）
     */
    var result = 0
    var negative = false
    var i = 0
    val len: Int = str.length

    /**
     * limit默认初始化为最大正整数的相反数
     * 加入字符串表示的是正数，那么result（在返回之前一直是负数形式）就必须和这个最大正整数的相反数来比较，判断是否溢出
     */
    var limit = -Int.MAX_VALUE
    val multmin: Int
    var digit: Int

    if (len > 0) {
        val firstChar: Char = str[0]
        //如果是负数
        if (firstChar < '0') { // Possible leading "+" or "-"
            if (firstChar == '-') {
                negative = true
                // 这里在负号的情况下，判断溢出的值就变成了负整数的最小值了
                limit = Int.MIN_VALUE
            } else if (firstChar != '+') throw NumberFormatException(str)
            if (len == 1) throw NumberFormatException(str)
            i++
        }
        // 说下multimin：
        // 假如radix进制下Integer.MAX_VALUE是用n位数字来表示的，
        // 那么radix进制下(n-1)位数字所能表示的最大的十进制数就是multimin
        multmin = limit / 10
        while (i < len) {
            // Accumulating negatively avoids surprises near MAX_VALUE
            digit = Character.digit(str[i++], 10)
            /**
             * 说下接下来两个if判断的思路：以s表示的是正数进行说明（负数也是同样的道理），i是循环变量，
             * len是字符串s的长度
             * Integer(s(0,i-1))表示截取字符串s中索引范围为[0,i-1]的子串，该子串所表示的整数
             * （1）当截取了(i-1)位时，最多能到radix进制里(n-1)位数字所能表示的最大的十进制数的相反数
             * 因为i最多能到n位
             * Integer(s(0,i-1)) <= multimin (i<=n)
             * （2）当截取了i位时，最多能到radix进制的n位数字所能表示的最大的十进制数的相反数，即-Integer.MAX
             * Integer(s(0,i)) <= limit (i<=n)，这里并没有直接去比较，而是做了转换
             * 即 Integer(s(0,i-1)) <= limit + digit
             * （为什么不写Integer(s(0,i-1)) - digit <= limit呢？因为Integer(s(0,i)) - digit可能会出现溢出）
             */

            if (digit < 0) {
                throw NumberFormatException(str)
            }
            if (result < multmin) {
                throw NumberFormatException(str)
            }
            result *= 10

            if (result < limit + digit) {
                throw NumberFormatException(str)
            }
            result -= digit
        }
    } else {
        throw NumberFormatException(str)
    }
    return if (negative) result else -result
}

/**
 * 统计一篇英文文档中各个字符出现的次数，ascii码，最大127，z是122
 * 开辟新空间，类似题目，查找1~2000每个数字出现的次数
 * @param str String
 */
fun countLetterCurrency(str: String) {
    val ascii = IntArray(123) { 0 }
    for (i in str.indices) {
        val char = str[i]
        ascii[char.toInt()]++
    }
    ascii.forEachIndexed { index, i ->
        println("${index.toChar()}->${i}")
    }
}

/**
 * 罗马数转整数
 * @param s String
 * @return Int
 */
fun romanToInt(s: String): Int {
    val map = ArrayMap<Char,Int>()
    map['I'] = 1
    map['V'] = 5
    map['X'] = 10
    map['L'] = 50
    map['C'] = 100
    map['D'] = 500
    map['M'] = 1000
    //从左到右遍历，如果右边的大于左边的，则-去，反之加上
    val chars = s.toCharArray()
    var result = 0
    for (i in 0 until chars.size - 1) {
        val k = i + 1
        if (map[chars[k]]!! > map[chars[i]]!!) {
            result -= map[chars[i]]!!
        } else {
            result += map[chars[i]]!!
        }
    }
    return result
}