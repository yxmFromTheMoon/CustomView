package com.yxm.customview.testmvp

import com.yxm.baselibrary.base.mvp.BasePresenter

/**
 * Created by Myron at 2021/8/22 15:32
 * @email yxmbest@163.com
 * @description
 */
class UserInfoPresenter: BasePresenter<UserInfoContract.UserInfoView>(),
        UserInfoContract.UserInfoPresenter {

    private var mModel: UserModel? = null

    init {
        mModel = UserModel()
    }

    override fun getUserInfo(userName: String) {
        mModel?.getUserInfo(userName, {
            getView()?.setUserInfo(it)
        }, {
            getView()?.setUserInfo(null)
        })
    }
}