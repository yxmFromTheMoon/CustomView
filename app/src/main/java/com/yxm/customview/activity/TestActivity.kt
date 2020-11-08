package com.yxm.customview.activity

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yxm.baselibrary.net.HttpUtils
import com.yxm.baselibrary.net.User
import com.yxm.customview.R
import com.yxm.customview.viewmodel.UserViewModel
import com.yxm.framelibrary.BaseSkinActivity
import com.yxm.framelibrary.Person
import com.yxm.framelibrary.database.DaoSupport
import com.yxm.framelibrary.database.DaoSupportFactory
import com.yxm.framelibrary.database.IDaoSupport
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

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

    }

    override fun initListener() {

    }

    override fun initData() {

    }

    @SuppressLint("UseCompatLoadingForDrawables", "DiscouragedPrivateApi")
    private fun changeResource() {
        try {
            val superRes = resources
            val assetManager = AssetManager::class.java.newInstance()
            val method = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            method.invoke(assetManager, Environment.getExternalStorageDirectory().absolutePath
                    + File.separator + "test.skin")
            val mRes = Resources(assetManager, superRes.displayMetrics, superRes.configuration)
            val drawableId = mRes.getIdentifier("ic_back_pressed", "drawable", "com.example.connotation")
            val drawable = mRes.getDrawable(drawableId)
            test_iv.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}