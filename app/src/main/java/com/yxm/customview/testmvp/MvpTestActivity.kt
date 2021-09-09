package com.yxm.customview.testmvp

import com.yxm.baselibrary.base.mvp.BaseMvpActivity
import com.yxm.baselibrary.base.mvp.BaseView
import com.yxm.baselibrary.net.RetrofitManager
import com.yxm.baselibrary.net.User
import com.yxm.customview.R
import com.yxm.customview.databinding.ActivityMvpTestBinding

/**
 * Created by Myron at 2021/8/22 15:21
 * @email yxmbest@163.com
 * @description
 */
class MvpTestActivity : BaseMvpActivity<UserInfoPresenter>(),UserInfoContract.UserInfoView {

    private lateinit var viewBinding: ActivityMvpTestBinding

    override fun getLayoutId(): Int {
        return R.layout.activity_mvp_test
    }

    override fun createPresenter(): UserInfoPresenter {
        return UserInfoPresenter()
    }

    override fun initView() {
        viewBinding = ActivityMvpTestBinding.inflate(layoutInflater)
    }

    override fun initData() {
        getPresenter()?.getUserInfo("yxmFromTheMoon")
    }

    override fun setUserInfo(user: User?) {
        viewBinding.mvpTestTv.text = user?.name
    }
}