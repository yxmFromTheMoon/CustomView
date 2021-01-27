package com.yxm.customview.algorithm.algorithms.linkedlist

/**
 * Created by yxm at 2021/1/27 12:41
 * @email: yxmbest@163.com
 * @description:链表相关
 */

class ListNode @JvmOverloads constructor(var value: Int = 0, var next: ListNode? = null)


fun createLinkedList(data: IntArray): ListNode? {
    val nodeList = mutableListOf<ListNode>()
    data.forEachIndexed { index, i ->
        val newNode = ListNode(i)
        nodeList.add(newNode)
    }
    nodeList.forEachIndexed { index, listNode ->
        if (index < nodeList.size - 1)
            listNode.next = nodeList[index + 1]
    }
    return nodeList[0]
}

fun printLinkedList(head: ListNode?) {
    var cur = head
    while (cur != null) {
        println(cur.value)
        cur = cur.next
    }
}

/**
 * 反转链表
 */
fun reverseLinkedList(head: ListNode?): ListNode? {
    if (head == null) {
        return head
    }
    var pre: ListNode? = null
    var cur = head
    while (cur != null) {
        val next = cur.next
        cur.next = pre
        pre = cur
        cur = next
    }
    return pre
}

/**
 * 删除链表中值与给定的val相等的节点
 */
fun removeValNode(head: ListNode?, value: Int) {

}

/**
 * 删除链表中重复的节点，使得每个节点只出现一次
 */
fun removeDuplicateNode(head: ListNode?) {

}

/**
 * 删除链表中的某个节点
 * 思路：设置虚拟头节点
 */
fun removeNode(head: ListNode?) {

}

/**
 * 交换相邻的两个节点
 * 思路：设置虚拟头节点
 */
fun swapNodeInPairs(head: ListNode?) {

}