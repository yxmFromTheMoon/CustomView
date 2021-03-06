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
fun removeValNode(head: ListNode?, value: Int): ListNode? {
    var tempHead = head
    while (tempHead != null && tempHead.value == value) {
        tempHead = tempHead.next
    }
    var cur = tempHead
    while (cur?.next != null) {
        val next = cur.next
        if (cur.next?.value == value) {
            cur.next = next?.next
        } else {
            cur = cur.next
        }
    }
    return tempHead
}

/**
 * 删除链表中相邻重复的节点，使得每个节点只出现一次
 */
fun removeDuplicateNode(head: ListNode?): ListNode? {
    if (head?.next == null) {
        return head
    }
    var cur = head
    while (cur?.next != null) {
        val next = cur.next
        if (cur.value == next?.value) {
            cur.next = next.next
        } else {
            cur = next
        }
    }
    return head
}

/**
 * 删除链表中的某个节点
 */
fun removeNode(node: ListNode?) {
    if (node == null) {
        return
    }
    val delNode = node.next
    delNode?.let {
        node.value = delNode.value
        node.next = delNode.next
    }
}

/**
 * 交换相邻的两个节点
 * 思路：设置虚拟头节点
 */
fun swapNodeInPairs(head: ListNode?) {

}

/**
 * 判断链表是否有环,快慢指针 O(n)
 * 也可用集合记录每个节点，遍历链表,如果集合中存在某个元素，
 * 则证明有环，性能较差
 * @param head ListNode?
 * @return Boolean
 */
fun hasCycle(head: ListNode?): Boolean {
    if (head?.next == null) {
        return false
    }
    var slow = head
    var fast = head
    while (fast?.next != null) {
        slow = slow?.next
        fast = fast.next?.next
        if (slow == fast) {
            return true
        }
    }
    return false
}

/**
输入两个链表，找出它们的第一个公共结点。
思路：由于两个链表长度不等，所以无法通过双指针直接遍历，设a链表的长度为A,b链表的长度为B,
则A+B = B+A，遍历完a链表后，将当前链表指向B的头指针，遍历完b链表后，将当前节点指向A的头指针
 **/
fun FindFirstCommonNode(pHead1: ListNode?, pHead2: ListNode?): ListNode? {
    if (pHead1 == null || pHead2 == null) {
        return null
    }
    var node1 = pHead1
    var node2 = pHead2
    while (node1 != node2) {
        node1 = node1?.next
        node2 = node2?.next
        if (node1 != node2) {
            if (node1 == null) {
                node1 = pHead2
            }
            if (node2 == null) {
                node2 = pHead1
            }
        }
    }
    return node1
}
