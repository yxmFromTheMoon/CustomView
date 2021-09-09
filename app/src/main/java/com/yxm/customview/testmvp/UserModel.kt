package com.yxm.customview.testmvp

import com.yxm.baselibrary.base.mvp.BaseModel
import com.yxm.baselibrary.net.HttpUtils
import com.yxm.baselibrary.net.RetrofitCallback
import com.yxm.baselibrary.net.User

/**
 * Created by Myron at 2021/8/22 15:26
 * @email yxmbest@163.com
 * @description
 */
class UserModel : BaseModel {

    fun getUserInfo(userName: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        HttpUtils.getUser1(userName).enqueue(object : RetrofitCallback<User>() {

            override fun onSuccess(result: User) {
                onSuccess.invoke(result)
            }

            override fun onFailure(msg: String, code: Int) {
                onFailure.invoke(msg)
            }
        })
    }
}