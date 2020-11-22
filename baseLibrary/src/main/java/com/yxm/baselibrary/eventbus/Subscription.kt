package com.yxm.baselibrary.eventbus

/**
 * Created by Myron at 2020/11/22 9:58
 * @email yxmbest@163.com
 * @description
 */
class Subscription {

    var subscriber: Any? = null
    var subscriberMethod: SubscriberMethod? = null

    /**
     * Becomes false as soon as [EventBus.unregister] is called, which is checked by queued event delivery
     * [EventBus.invokeSubscriber] to prevent race conditions.
     */
    @Volatile
    var active = false

    fun Subscription(subscriber: Any?, subscriberMethod: SubscriberMethod?) {
        this.subscriber = subscriber
        this.subscriberMethod = subscriberMethod
        active = true
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Subscription) {
            val otherSubscription = other
            (subscriber === otherSubscription.subscriber
                    && subscriberMethod == otherSubscription.subscriberMethod)
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return subscriber.hashCode() + subscriberMethod!!.methodString.hashCode()
    }
}