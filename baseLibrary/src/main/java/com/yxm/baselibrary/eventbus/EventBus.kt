package com.yxm.baselibrary.eventbus

import java.util.concurrent.CopyOnWriteArrayList


/**
 * Created by Myron at 2020/11/22 9:56
 * @email yxmbest@163.com
 * @description
 */
class EventBus @JvmOverloads constructor(builder: EventBusBuilder? = DEFAULT_BUILDER) {

    companion object {
        var defaultInstance: EventBus? = null
        private val DEFAULT_BUILDER = EventBusBuilder()

        /** Convenience singleton for apps using a process-wide EventBus instance.  */
        fun getDefault(): EventBus? {
            var instance = defaultInstance
            if (instance == null) {
                synchronized(EventBus::class.java) {
                    instance = defaultInstance
                    if (instance == null) {
                        defaultInstance = EventBus()
                        instance = defaultInstance
                    }
                }
            }
            return instance
        }
    }
    private val subscriptionsByEventType: Map<Class<*>, CopyOnWriteArrayList<Subscription>>? = null
    private val typesBySubscriber: Map<Any, List<Class<*>>>? = null
    private val subscriberMethodFinder: SubscriberMethodFinder? = null

    fun register(subscriber: Any) {
        val subscriberClass: Class<*> = subscriber.javaClass
        val subscriberMethods: List<SubscriberMethod> = subscriberMethodFinder!!.findSubscriberMethods(subscriberClass)
        synchronized(this) {
            for (subscriberMethod in subscriberMethods) {
                subscribe(subscriber, subscriberMethod)
            }
        }
    }

    private fun subscribe(subscriber: Any, subscriberMethod: SubscriberMethod) {

    }
}