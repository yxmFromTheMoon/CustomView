package com.yxm.customview.activity

import android.content.Context
import com.yxm.customview.R
import com.yxm.framelibrary.BaseSkinActivity
import dalvik.system.DexClassLoader
import java.io.File

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:33
 * @description
 */
class TestActivity : BaseSkinActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initView() {
        val file = File("$cacheDir", "/demo.apk")
        val assets = assets.open("plugin/plugindemo.apk")
        assets.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output, 8 * 1024)
                input.close()
                output.close()
            }
        }

        if (file.exists()) {
            val classLoader = DexClassLoader(file.path, cacheDir.path, null, null)
            val clazz = classLoader.loadClass("com.yxm.plugindemo.PluginUtils")
            val constructor = clazz.declaredConstructors[0]
            val instance = constructor.newInstance()
            val method = clazz.getDeclaredMethod("toast", Context::class.java)
            method.invoke(instance, this)
        }
    }

    override fun initListener() {

    }

    override fun initData() {

    }

}