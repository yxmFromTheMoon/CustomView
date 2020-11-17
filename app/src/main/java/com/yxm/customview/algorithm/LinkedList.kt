package com.yxm.customview.algorithm

/**
 * Created by Myron at 2020/11/16 20:27
 * @email yxmbest@163.com
 * @description 手写LinkedList
 */
class LinkedList<E> {

    private var len: Int = 0

    //头节点
    private var head: Node<E>? = null

    //尾节点
    private var tail: Node<E>? = null

    /**
     * 添加一个元素
     * @param value E
     */
    fun push(value: E) {
        linkTail(value)
        //单向链表的操作
//        val newNode = Node(value, null,null)
//        if (head == null) {
//            head = newNode
//        } else {
//            //插入到最后一个的后面
//            //记录last节点,时间复杂度变为0(1),这也是系统采用双向链表的一个原因
//            //经测试，插入100000条数据由8秒变为5毫秒
//            tail?.next = newNode
//        }
//       tail = newNode
        len++
    }

    /**
     * 和尾节点相连
     * @param value E
     */
    private fun linkTail(value: E) {
        val t = tail
        val newNode = Node(value, tail, null)
        tail = newNode
        if (head != null) {
            t?.next = newNode
        } else {
            head = newNode
        }
    }

    /**
     * 和前一个节点相连
     * @param value E
     */
    private fun linkBefore(currentNode: Node<E>?, value: E) {
        val preNode = currentNode?.pre
        val newNode = Node(value, preNode, currentNode)

        //插入的是头节点的位置
        if (preNode == null) {
            head = newNode
        } else {
            preNode.next = newNode
        }
        currentNode?.pre = newNode
    }

    private fun unlink(currentNode: Node<E>?): E? {
        //左右两个节点
        val preNode = currentNode?.pre
        val nextNode = currentNode?.next
        val value = currentNode?.value

        /**
         * 当前节点不是头节点
         * 移除当前节点时，要把当前节点的前一个节点的next指向当前节点的next，
         * 把当前节点的下一个节点的pre指向当前节点的pre
         *
         * 当移除的节点是头节点时，直接把当前节点的next赋值给头节点，
         *
         * 如果当前节点的next为空，
         * 有两种情况 1.只有一个头节点 2.当前节点是最后一个节点
         * 对于第一种情况，直接将head和tail置为空就行了，
         * 对于第二种情况，要把当前节点的pre赋值给tail
         */
        if (preNode != null) {
            preNode.next = nextNode
        } else {
            head = nextNode
        }
        if (nextNode != null) {
            nextNode.pre = preNode
        } else {
            tail = preNode
        }
        len--
        return value
    }

    fun size(): Int {
        return len
    }


    /**
     * 获取指定索引的节点值
     * @param index Int
     * @return E
     */
    fun get(index: Int): E? {
        findNode(index)?.let {
            return it.value
        }
        return null
    }

    /**
     * 移除指定索引的节点
     * 本质就是将待移除节点的左右节点断开连接
     * @param index Int
     */
    fun remove(index: Int): E? {
        if (index < 0 || index >= len) {
            throw IndexOutOfBoundsException("数组越界")
        }
        //移除头节点，单向链表的操作
//        if (index == 0) {
//            val h = head
//            head = h?.next
//        } else {
//            //删除其他节点
//            val preNode = findNode(index - 1)
//            val currentNode = preNode?.next
//            preNode?.next = currentNode?.next
//            currentNode?.next = null
//        }
        return unlink(findNode(index))
    }

    /**
     * 在指定位置插入e
     * @param index Int
     * @param e E
     */
    fun insert(index: Int, value: E) {
        if (index < 0 || index > len) {
            throw IndexOutOfBoundsException("数组越界")
        }
        if (index == len - 1) {
            linkTail(value)
        } else {
            linkBefore(findNode(index), value)
        }
        //单向链表的操作
//        val newNode = Node(value, null)
//        if (index == 0) {
//            val h = head
//            newNode.next = h
//            head = newNode
//        } else {
//            val preNode = findNode(index - 1)
//            val currentNode = preNode?.next
//            preNode?.next = newNode
//            newNode.next = currentNode
//        }
        len++
    }

    /**
     * 找到指定位置的node节点
     * 可以用二分查找优化
     * @param index Int
     */
    private fun findNode(index: Int): Node<E>? {
        //从头节点开始找
        var headNode = head
        for (i in 0 until index) {
            headNode = headNode?.next
        }
        return headNode
    }

    inner class Node<E>(var value: E?, var pre: Node<E>?, var next: Node<E>?)
}