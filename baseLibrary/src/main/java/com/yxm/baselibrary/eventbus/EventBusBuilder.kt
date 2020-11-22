package com.yxm.baselibrary.eventbus

import android.os.Looper
import java.lang.RuntimeException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by Myron at 2020/11/22 10:08
 * @email yxmbest@163.com
 * @description
 */
class EventBusBuilder {
    private val DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool()


    var executorService = DEFAULT_EXECUTOR_SERVICE
    var skipMethodVerificationForClasses: MutableList<Class<*>>? = null

    fun EventBusBuilder() {}


    /**
     * Provide a custom thread pool to EventBus used for async and background event delivery. This is an advanced
     * setting to that can break things: ensure the given ExecutorService won't get stuck to avoid undefined behavior.
     */
    fun executorService(executorService: ExecutorService): EventBusBuilder? {
        this.executorService = executorService
        return this
    }

    /**
     * Method name verification is done for methods starting with onEvent to avoid typos; using this method you can
     * exclude subscriber classes from this check. Also disables checks for method modifiers (public, not static nor
     * abstract).
     */
    fun skipMethodVerificationFor(clazz: Class<*>): EventBusBuilder? {
        if (skipMethodVerificationForClasses == null) {
            skipMethodVerificationForClasses = ArrayList()
        }
        skipMethodVerificationForClasses!!.add(clazz)
        return this
    }


    /**
     * Installs the default EventBus returned by [EventBus.getDefault] using this builders' values. Must be
     * done only once before the first usage of the default EventBus.
     *
     * @throws EventBusException if there's already a default EventBus instance in place
     */
    fun installDefaultEventBus(): EventBus? {
        synchronized(EventBus::class.java) {
            if (EventBus.defaultInstance != null) {
                throw RuntimeException("Default instance already exists." +
                        " It may be only set once before it's used the first time to ensure consistent behavior.")
            }
            EventBus.defaultInstance = build()
            return EventBus.defaultInstance
        }
    }

    /** Builds an EventBus based on the current configuration.  */
    fun build(): EventBus? {
        return EventBus(this)
    }
}