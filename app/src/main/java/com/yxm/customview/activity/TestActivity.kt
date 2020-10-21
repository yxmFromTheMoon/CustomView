package com.yxm.customview.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yxm.baselibrary.net.HttpUtils
import com.yxm.customview.R
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        test_tv.setOnClickListener {
            mCoroutineScope.launch(Dispatchers.Main + CoroutineExceptionHandler { coroutineContext, throwable ->
                throwable.printStackTrace()
            }) {
                val user = async {
                    HttpUtils.getUser("yxmFromTheMoon")
                }

                val user1 = async {
                    HttpUtils.getUser("bennyhuo")
                }
                test_tv.text = user.await().name + user1.await().name
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mCoroutineScope.isActive) {
            mCoroutineScope.cancel()
        }
    }
}