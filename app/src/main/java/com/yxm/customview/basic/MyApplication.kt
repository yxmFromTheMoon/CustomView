package com.yxm.customview.basic

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.yxm.baselibrary.imageloader.GlideImageLoader
import com.yxm.baselibrary.imageloader.ImageLoaderUtils
import com.yxm.baselibrary.startup.TaskDispatcher
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

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        TaskDispatcher.init(this)
        CrashExceptionHandler.init(this)
        SkinPreUtils.init(this)
        SkinManager.init(this)
        ImageLoaderUtils.init(this)
    }

    companion object {
        lateinit var INSTANCE: MyApplication
    }
}