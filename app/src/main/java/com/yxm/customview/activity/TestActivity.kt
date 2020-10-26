package com.yxm.customview.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.yxm.baselibrary.net.HttpUtils
import com.yxm.customview.R
import com.yxm.customview.showToast
import com.yxm.customview.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:33
 * @description
 */
class TestActivity : AppCompatActivity() {

    private val mJob = Job()
    private val mCoroutineScope = CoroutineScope(mJob)

    private val mViewModel by viewModels<UserViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        mViewModel.liveData.observe(this, { result ->
            val user = result.getOrNull()
            if(user != null){
                mViewModel.user = user
                test_tv.text = user.name
            }else{
                "数据为空".showToast()
            }

        })

        test_tv.setOnClickListener {
            mViewModel.getUser("yxmFromTheMoon")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mCoroutineScope.isActive) {
            mCoroutineScope.cancel()
        }
    }
}