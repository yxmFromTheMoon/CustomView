package com.yxm.baselibrary.startup

import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by yxm at 2021/2/4 17:56
 * @email: yxmbest@163.com
 * @description:
 */
object DispatcherExecutor {

    private var cpuThreadPoolExecutor: ThreadPoolExecutor? = null
    private var ioThreadPoolExecutor: ExecutorService? = null

    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work

    private val CORE_POOL_SIZE = 2.coerceAtLeast((CPU_COUNT - 1).coerceAtMost(5))
    private val MAXIMUM_POOL_SIZE = CORE_POOL_SIZE
    private const val KEEP_ALIVE_SECONDS = 5
    private val sPoolWorkQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()
    private val sThreadFactory: DefaultThreadFactory = DefaultThreadFactory()
    private val sHandler: RejectedExecutionHandler = RejectedExecutionHandler { r, executor ->
        // 一般不会到这里
        Executors.newCachedThreadPool().execute(r)
    }

    /**
     * 获取CPU线程池
     * @return
     */
    fun getCPUExecutor(): ThreadPoolExecutor? {
        return cpuThreadPoolExecutor
    }

    /**
     * 获取IO线程池
     * @return
     */
    fun getIOExecutor(): ExecutorService? {
        return ioThreadPoolExecutor
    }

    init {
        cpuThreadPoolExecutor = ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS.toLong(), TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory, sHandler)
        cpuThreadPoolExecutor!!.allowCoreThreadTimeOut(true)
        ioThreadPoolExecutor = Executors.newCachedThreadPool(sThreadFactory)
    }

    class DefaultThreadFactory : ThreadFactory {
        private val poolNumber: AtomicInteger = AtomicInteger(1)
        private var group: ThreadGroup? = null
        private val threadNumber: AtomicInteger = AtomicInteger(1)
        private var namePrefix: String? = null

        init {
            val s = System.getSecurityManager()
            group = if (s != null) s.threadGroup else Thread.currentThread().threadGroup
            namePrefix = "TaskDispatcherPool-" +
                    poolNumber.getAndIncrement() +
                    "-Thread-"
        }

        override fun newThread(r: Runnable?): Thread {
            val t = Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(), 0)
            if (t.isDaemon) t.isDaemon = false
            if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
            return t
        }

    }
}


