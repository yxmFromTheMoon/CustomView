package com.yxm.baselibrary.eventbus

import java.lang.reflect.Method

/**
 * Created by Myron at 2020/11/22 9:57
 * @email yxmbest@163.com
 * @description
 */
class SubscriberMethod {
    var method: Method? = null
    var threadMode: ThreadMode? = null
    var eventType: Class<*>? = null
    var priority = 0
    var sticky = false

    /** Used for efficient comparison  */
    var methodString: String? = null

    fun SubscriberMethod(method: Method?, eventType: Class<*>?, threadMode: ThreadMode?, priority: Int, sticky: Boolean) {
        this.method = method
        this.threadMode = threadMode
        this.eventType = eventType
        this.priority = priority
        this.sticky = sticky
    }

    override fun equals(other: Any?): Boolean {
        return when {
            other === this -> {
                true
            }
            other is SubscriberMethod -> {
                checkMethodString()
                other.checkMethodString()
                // Don't use method.equals because of http://code.google.com/p/android/issues/detail?id=7811#c6
                methodString == other.methodString
            }
            else -> {
                false
            }
        }
    }

    @Synchronized
    private fun checkMethodString() {
        if (methodString == null) {
            // Method.toString has more overhead, just take relevant parts of the method
            val builder = StringBuilder(64)
            builder.append(method!!.declaringClass.name)
            builder.append('#').append(method!!.name)
            builder.append('(').append(eventType!!.name)
            methodString = builder.toString()
        }
    }

    override fun hashCode(): Int {
        return method.hashCode()
    }
}