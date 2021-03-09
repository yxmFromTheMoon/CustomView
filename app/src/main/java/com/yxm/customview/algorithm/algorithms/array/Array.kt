package com.yxm.customview.algorithm.algorithms.array

import com.yxm.customview.algorithm.algorithms.sort.swap
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.min

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

/**                   以下都可用双指针思想            **/

/**
 * 只有0，1，2三个元素的无序数组，对其进行排序
 * 三路快排的思路，
 * 0..zero左闭右开全是0,
 * zero+1..i-1左闭右开都是1,
 * two..array.size-1左闭右开都是2
 */
fun sortColor(array: IntArray) {
    var zero = -1
    var two = array.size
    var i = 0
    while (i < two) {
        when {
            array[i] == 1 -> {
                i++
            }
            array[i] == 2 -> {
                two--
                swap(array, i, two)
            }
            else -> {
                zero++
                swap(array, zero, i)
                i++
            }
        }
    }
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
fun twoSumII(array: IntArray, target: Int): IntArray {
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
    if (s.isNullOrEmpty()) {
        return true
    }
    val charArray = s.replace(Regex("[^a-zA-Z0-9\\u4E00-\\u9FA5]")) {
        ""
    }.toLowerCase(Locale.ROOT).toCharArray()
    var l = 0
    var r = charArray.size - 1
    while (l <= r) {
        if (charArray[l] == charArray[r]) {
            l++
            r--
        } else {
            return false
        }
    }
    return true
}

/**
 * 反转字符串
 */
fun reverseStr(s: String?): String? {
    if (s.isNullOrEmpty()) {
        return s
    }
    var l = 0
    val charArray = s.toCharArray()
    var r = charArray.size - 1
    while (l < r) {
        val leftChar = charArray[l]
        charArray[l] = charArray[r]
        charArray[r] = leftChar
        l++
        r--
    }
    return charArray.concatToString()
}


/**                            end                        **/

/**                        滑动窗口                             **/
/**
 * 最短子数组的长度
 * 给定一个值和数组，找出数组中子数组的和>=s的最小子数组长度
 */
fun minSubArrayLen(target: Int, array: IntArray): Int {
    var l = 0
    var r = -1
    var sum = 0
    var result = array.size + 1
    while (l < array.size) {
        if (r + 1 < array.size && sum < target) {
            r++
            sum += array[r]
        } else {
            sum -= array[l]
            l++
        }
        if (sum >= target) {
            result = min(result, r - l + 1)
        }
    }
    if (result == array.size + 1) {
        return 0
    }
    return result
}

/**
 * 两个数组的相同的元素
 */
fun interSectionOfTwoArrays(arr1: IntArray, arr2: IntArray): IntArray {
    val result = HashSet<Int>()
    val setRecord = arr1.toHashSet()
    arr2.forEach {
        if (setRecord.contains(it)) {
            result.add(it)
        }
    }
    return result.toIntArray()
}

/**
 *数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 *例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，
 *超过数组长度的一半，因此输出2。如果不存在则输出0。
 */

fun MoreThanHalfNum_Solution(array: IntArray): Int {
    if (array.size == 1) {
        return array[0]
    }
    val map: HashMap<Int, Int> = HashMap()
    for (i in array.indices) {
        if (map.containsKey(array[i])) {
            if (map[array[i]] == array.size / 2) {
                return array[i]
            } else {
                map[array[i]] = map[array[i]]!! + 1
            }
        } else {
            map[array[i]] = 1
        }
    }
    return 0
}

/**
在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置,
如果没有则返回 -1（需要区分大小写）.（从0开始计数）
 */
fun FirstNotRepeatingChar(str: String?): Int? {
    if (str.isNullOrEmpty())
        return -1
    var result = Int.MAX_VALUE
    val map = HashMap<Char, Int>()
    val array = str.toCharArray()
    for (c in array) {
        if (map.containsKey(c)) {
            map[c] = -1
        } else {
            map[c] = array.indexOf(c)
        }
    }
    for (key in map.keys) {
        if (map[key] != -1) {
            result = min(map[key]!!, result)
        }
    }
    return result
}