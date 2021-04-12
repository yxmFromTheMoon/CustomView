package com.yxm.customview.algorithm.algorithms.sort

/**
 *@author: yxm
 *@time: 2020/11/17
 *@description:
 */
/**
 * 交换数组中的数据
 * @param array IntArray
 * @param i Int
 * @param j Int
 */
fun swap(array: IntArray, i: Int, j: Int) {
    val iValue = array[i]
    array[i] = array[j]
    array[j] = iValue
}

/**
 * 冒泡排序，思想：相邻的两个不断比较
 * @param array IntArray
 */
fun bubbleSort(array: IntArray) {
    val length = array.size
    for (i in 0 until length - 1) {
        var isExchange = false
        for (j in 0 until length - i - 1) {
            if (array[j] > array[j + 1]) {
                isExchange = true
                swap(array, j, j + 1)
            }
            if (!isExchange) {
                break
            }
        }
    }
    array.forEach {
        println(it)
    }
}

/**
 * 选择排序，不断的选择数组中最小的数与第一个数进行交换
 * @param array IntArray
 */
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

/**
 * 插入排序 最基础版
 * @param array IntArray
 */
fun insertSort(array: IntArray) {
    for (i in 1 until array.size) {
        for (j in i downTo 1) {
            if (array[j] < array[j - 1]) {
                swap(array, j, j - 1)
            } else {
                break
            }
        }
    }
    array.forEach {
        println(it)
    }
}

/**
 * 插入排序 优化版 属于稳定排序，对数据部分有序时效率较高
 * @param array IntArray
 */
fun insertSort2(array: IntArray) {
    if (array.isEmpty()) {
        return
    }
    val size = array.size
    for (i in 1 until size) {
        val holder = array[i]
        var j = i
        while (j > 0 && array[j - 1] > holder) {
            array[j] = array[j - 1]
            j--
        }
        array[j] = holder
    }
    array.forEach {
        println(it)
    }
}

/**
 * 希尔排序，插入排序的变种，利用gap步长来分组进行插入排序
 * @param array IntArray
 */
fun shellSort(array: IntArray) {
    val length = array.size
    var gap = length / 2
    while (gap > 0) {
        for (i in 0 until gap) {
            for (j in (i + gap) until length step gap) {
                var k = j
                val holder = array[j]
                while (k - gap >= 0 && array[k - gap] > holder) {
                    array[k] = array[k - gap]
                    k -= gap
                }
                array[k] = holder
            }
        }
        gap /= 2
    }
    array.forEach {
        println(it)
    }
}

fun quickSort(array: IntArray) {
    quickSort(array, 0, array.size - 1)
}

fun quickSort(array: IntArray, l: Int, r: Int) {
    if (l >= r) {
        return
    }
    val p = partition(array, l, r)
    quickSort(array, l, p - 1)
    quickSort(array, p + 1, r)
}

fun partition(array: IntArray, l: Int, r: Int): Int {
    val left = array[l]
    var j = l
    for (i in l + 1..r step 1) {
        if (array[i] < left) {
            j++
            swap(array, j, i)
        }
    }
    swap(array, l, j)
    return j
}

fun partitions(array: IntArray, left: Int, right: Int): Int {
    var i = left
    var j = right
    val pivot = array[(left + right) / 2]
    while (i <= j) {
        while (array[i] < pivot)
            i++
        while (array[j] > pivot)
            j--
        if (i <= j) {
            val tmp = array[i]
            array[i] = array[j]
            array[j] = tmp
            i++
            j--
        }
    }
    return i
}

/**
 * 二分查找，必须是有序数组
 * @param array IntArray
 */
fun binarySearch(array: IntArray, target: Int): Int {
    //[l...r]区间查找
    var l = 0
    var r = array.size - 1

    while (l <= r) {
        val mid = l + (r - l) / 2
        when {
            array[mid] == target -> {
                return mid
            }
            array[mid] > target -> {
                //在左边查找
                r = mid - 1
            }
            else -> {
                l = mid + 1
            }
        }
    }
    return -1
}

/**
 * 可以递归实现
 */
fun binarySearch(array: IntArray, l: Int, r: Int, target: Int): Int {
    if (l > r) {
        return -1
    }
    val mid = l + (r - l) / 2
    return when {
        array[mid] == target -> {
            mid
        }
        array[mid] > target -> {
            //在左边查找
            binarySearch(array, 0, mid - 1, target)
        }
        else -> {
            binarySearch(array, mid + 1, r, target)
        }
    }
}

