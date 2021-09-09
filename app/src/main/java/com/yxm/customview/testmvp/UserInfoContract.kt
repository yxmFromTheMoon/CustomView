package com.yxm.customview.testmvp

import com.yxm.baselibrary.base.mvp.BasePresenter
import com.yxm.baselibrary.base.mvp.BaseView
import com.yxm.baselibrary.net.User

/**
 * Created by Myron at 2021/8/22 15:33
 * @email yxmbest@163.com
 * @description
 */
interface UserInfoContract {
    interface UserInfoView : BaseView {
        fun setUserInfo(user: User?)
    }

    interface UserInfoPresenter {
        fun getUserInfo(userName: String)
    }
}