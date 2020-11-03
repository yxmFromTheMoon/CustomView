package com.yxm.customview.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yxm.baselibrary.net.User
import com.yxm.customview.R
import com.yxm.customview.viewmodel.UserViewModel
import com.yxm.framelibrary.Person
import com.yxm.framelibrary.database.DaoSupportFactory

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/21 20:33
 * @description
 */
class TestActivity : AppCompatActivity() {

    private val mViewModel by viewModels<UserViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val person = Person("1", 18)
        val list = DaoSupportFactory.getDao(Person::class.java).querySupport()
                .selection("age=?").selectionArgs("18").query()

    }
}