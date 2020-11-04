package com.yxm.customview.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yxm.baselibrary.net.HttpUtils
import com.yxm.baselibrary.net.User
import com.yxm.customview.R
import com.yxm.customview.viewmodel.UserViewModel
import com.yxm.framelibrary.Person
import com.yxm.framelibrary.database.DaoSupport
import com.yxm.framelibrary.database.DaoSupportFactory
import com.yxm.framelibrary.database.IDaoSupport
import kotlinx.coroutines.launch

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:33
 * @description
 */
class TestActivity : AppCompatActivity() {

    private val mViewModel by viewModels<UserViewModel>()
    private lateinit var mDaoSupport: IDaoSupport<User>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        mDaoSupport = DaoSupportFactory.getDao(User::class.java)
        lifecycleScope.launch {
            val user = HttpUtils.getUser("yxmFromTheMoon")

        }
    }
}