package com.yxm.customview.activity

import android.annotation.SuppressLint
import android.content.Context
import com.yxm.customview.OnDoubleClickListener
import com.yxm.customview.R
import com.yxm.customview.showToast
import com.yxm.customview.viewgruop.TagLayout
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

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {

        val rootView = findViewById<TagLayout>(R.id.root)
//        rootView.setOnClickListener {
//            "onClickSystem".showToast()
//        }
        val onDoubleClickListener = OnDoubleClickListener()
        onDoubleClickListener.listener = object : OnDoubleClickListener.OnDoubleClickListener {
            override fun onDoubleClick() {
                "onDoubleClick".showToast()
            }

            override fun onClick() {
                "onClick".showToast()
            }
        }
        rootView.setOnTouchListener(onDoubleClickListener)


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