package com.yxm.customview.algorithm.algorithms.array

import com.yxm.customview.algorithm.algorithms.sort.swap
import java.util.*
import kotlin.collections.HashMap

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

/**
 * 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。
说明:
返回的下标值（index1 和 index2）不是从零开始的。
你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
示例:
输入: numbers = [2, 7, 11, 15], target = 9
输出: [1,2]
解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
fun twoSumII(array: IntArray, target: Int):IntArray {
    var l = 0
    var r = array.size - 1
    while (l < r) {
        when {
            array[l] + array[r] == target -> {
                return intArrayOf(l + 1, r + 1)
            }
            array[l] + array[r] < target -> {
                l++
            }
            else -> {
                r--
            }
        }
    }
    return intArrayOf(0, 0)
}
/**
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
说明：本题中，我们将空字符串定义为有效的回文串。
示例 1:
输入: "A man, a plan, a canal: Panama"
输出: true
示例 2:
输入: "race a car"
输出: false
来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/valid-palindrome
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
fun isPalindrome(s: String?): Boolean {
    if(s.isNullOrEmpty()){
        return true
    }
    val charArray = s.replace(Regex("[^a-zA-Z0-9\\u4E00-\\u9FA5]")) {
        ""
    }.toLowerCase(Locale.ROOT).toCharArray()
    var l = 0
    var r = charArray.size - 1
    while (l <= r){
        if(charArray[l] == charArray[r]){
            l++
            r--
        }else{
            return false
        }
    }
    return true
}
