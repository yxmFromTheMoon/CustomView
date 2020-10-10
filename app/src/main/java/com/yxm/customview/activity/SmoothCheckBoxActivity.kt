package com.yxm.customview.activity

import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.showToast
import kotlinx.android.synthetic.main.activity_check_box.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/7/3 16:53
 * @Description
 */
class SmoothCheckBoxActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_check_box
    }

    override fun initView() {

    }

    override fun initListener() {
        checkbox.setOnCheckedChangeListener { checkBox, isChecked ->
            if(isChecked){
                "Checked".showToast()
            }else{
                "Unchecked".showToast()
            }
        }
    }

    override fun initData() {
    }
}