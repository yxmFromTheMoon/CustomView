package com.yxm.baselibrary.handler

/**
 * Created by Myron at 2020/11/21 14:49
 * @email yxmbest@163.com
 * @description
 */
class Message {
    var next: Message? = null
    var `when`: Long = 0
    var target: Handler? = null
    var obj: Any? = null

}