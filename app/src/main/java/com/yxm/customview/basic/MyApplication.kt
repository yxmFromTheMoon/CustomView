package com.yxm.customview.basic

import android.app.Application
import androidx.multidex.MultiDex
import com.yxm.baselibrary.imageloader.CoilImageLoader
import com.yxm.baselibrary.imageloader.ImageLoaderUtils
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
        CrashExceptionHandler.init(this)
        SkinPreUtils.init(this)
        SkinManager.init(this)
        ImageLoaderUtils.init(this)
        ImageLoaderUtils.setImageLoader(CoilImageLoader(this))
        MultiDex.install(this)
    }

    companion object {
        lateinit var INSTANCE: MyApplication
    }
}