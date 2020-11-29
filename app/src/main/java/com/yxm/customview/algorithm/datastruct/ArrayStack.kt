package com.yxm.customview.algorithm.datastruct

/**
 * Created by Myron at 2020/11/29 18:42
 * @email yxmbest@163.com
 * @description 数组实现栈
 */
class ArrayStack<E> {

    private var top: Int = -1
    private var size = 10
    private var array: Array<E> = arrayOfNulls<Any>(size) as Array<E>

    /**
     * 弹栈
     * @return E
     */
    fun pop(): E {
        if (top != -1) {
            return array[top--]
        }
        throw StackOverflowError("stack is empty")
    }

    /**
     * 是否为空
     * @return Boolean
     */
    fun isEmpty(): Boolean {
        return top == -1
    }

    /**
     * 去栈顶元素
     * @return E
     */
    fun peek(): E {
        if (top != -1) {
            return array[top]
        }
        throw StackOverflowError("stack is empty")
    }

    /**
     * 压栈
     * @param e E
     */
    fun push(e: E) {
        if (top >= size - 1) {
            growArray()
        }
        top++
        array[top] = e

    }

    /**
     * 扩容
     */
    private fun growArray() {
        size += size shr 1
        val tempArray = arrayOfNulls<Any>(size) as Array<E>
        System.arraycopy(array, 0, tempArray, 0,
                array.size)
        array = tempArray
    }

}