package com.yxm.customview.algorithm.datastruct

/**
 * Created by Myron at 2020/11/29 19:35
 * @email yxmbest@163.com
 * @description 链表实现栈s
 */
class LinkStack<E> {

    private var head: Node<E>? = null
    private var size = 0

    /**
     * 弹栈
     * @return E
     */
    fun pop(): E? {
        val e = head?.value
        head = head?.next
        size--
        return e
    }

    /**
     * 是否为空
     * @return Boolean
     */
    fun isEmpty(): Boolean {
        return size == 0
    }

    /**
     * 去栈顶元素
     * @return E
     */
    fun peek(): E? {
        return head?.value
    }

    /**
     * 压栈
     * @param e E
     */
    fun push(e: E) {
        head = Node(head, e)
        size++
    }

    inner class Node<E>(var next: Node<E>?, var value: E)
}