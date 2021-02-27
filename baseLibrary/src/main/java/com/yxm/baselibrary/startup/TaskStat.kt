package com.yxm.baselibrary.startup

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Myron at 2021/2/27 15:37
 * @email yxmbest@163.com
 * @description
 */
object TaskStat {
    @Volatile
    private var sCurrentSituation = ""
    private val sBeans: MutableList<TaskStatBean> = ArrayList()
    private var sTaskDoneCount: AtomicInteger = AtomicInteger()
    private const val sOpenLaunchStat = false // 是否开启统计


    fun getCurrentSituation(): String? {
        return sCurrentSituation
    }

    fun setCurrentSituation(currentSituation: String) {
        if (!sOpenLaunchStat) {
            return
        }
        sCurrentSituation = currentSituation
        setLaunchStat()
    }

    fun markTaskDone() {
        sTaskDoneCount.getAndIncrement()
    }

    fun setLaunchStat() {
        val bean = TaskStatBean()
        bean.setSituation(sCurrentSituation)
        bean.setCount(sTaskDoneCount.get())
        sBeans.add(bean)
        sTaskDoneCount = AtomicInteger(0)
    }
}