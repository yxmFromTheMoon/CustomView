package com.yxm.baselibrary.startup

/**
 * Created by yxm at 2021/2/4 16:41
 * @email: yxmbest@163.com
 * @description: 必须执行在主线程的Task
 */
abstract class MainTask : Task() {

    override fun runOnMainThread(): Boolean {
        return true
    }
}