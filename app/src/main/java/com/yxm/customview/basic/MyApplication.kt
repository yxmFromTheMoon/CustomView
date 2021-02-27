package com.yxm.customview.basic

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.yxm.baselibrary.imageloader.GlideImageLoader
import com.yxm.baselibrary.imageloader.ImageLoaderUtils
import com.yxm.baselibrary.startup.TaskDispatcher
import com.yxm.customview.LauncherTimer
import com.yxm.customview.task.CrashExceptionHandlerTask
import com.yxm.customview.task.ImageLoaderTask
import com.yxm.customview.task.SkinManagerTask
import com.yxm.customview.task.SkinPreUtilsTask
import com.yxm.framelibrary.CrashExceptionHandler
import com.yxm.framelibrary.skin.SkinManager
import com.yxm.framelibrary.skin.SkinPreUtils

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/27 13:17
 * @Description
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        TaskDispatcher.init(this)

        val taskDispatcher = TaskDispatcher.createInstance()
        taskDispatcher.addTask(CrashExceptionHandlerTask())
                .addTask(ImageLoaderTask())
                .addTask(SkinManagerTask())
                .addTask(SkinPreUtilsTask())
                .start()
        taskDispatcher.await()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        LauncherTimer.startRecord()
    }

    companion object {
        lateinit var INSTANCE: MyApplication
    }
}