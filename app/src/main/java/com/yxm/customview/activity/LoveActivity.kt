package com.yxm.customview.activity

import android.widget.RelativeLayout
import com.yxm.customview.R
import com.yxm.customview.basic.BaseActivity
import com.yxm.customview.view.LoveLayout
import com.yxm.customview.view.LoveLayoutV2
import kotlinx.android.synthetic.main.activity_love.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/19 14:53
 * @description
 */
class LoveActivity:BaseActivity() {

    private lateinit var mLoveLayout: LoveLayout
    override fun getLayoutId(): Int {
        return R.layout.activity_love
    }

    override fun initView() {
        mLoveLayout = love_layout
    }

    override fun initListener() {
        add_love.setOnClickListener {
            mLoveLayout.addLove()
        }
    }

    override fun initData() {

    }
}