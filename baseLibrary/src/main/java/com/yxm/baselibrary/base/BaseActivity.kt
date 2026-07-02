package com.yxm.baselibrary.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yxm.baselibrary.imageloader.ImageLoaderUtils
import com.yxm.baselibrary.ioc.ViewUtils
import kotlinx.coroutines.launch

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/26 22:49
 * @Description
 */
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mContext :Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                
            }
        }
        ViewUtils.inject(this)
        initView()
        initData()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        ImageLoaderUtils.resume(this)
    }

    override fun onPause() {
        super.onPause()
        ImageLoaderUtils.pause(this)
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()

    /**
     * findViewById
     * @return View
     */
    protected open fun <T : View?> viewById(viewId: Int): T {
        return findViewById<View>(viewId) as T
    }
}