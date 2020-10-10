package com.yxm.customview.activity

import android.widget.TextView
import com.yxm.customview.R
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.customview.gone
import com.yxm.customview.view.LetterSideBar
import com.yxm.customview.visible
import kotlinx.android.synthetic.main.activity_letter_side_bar.*

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/7/4 16:13
 * @description
 */
class LetterSideBarActivity : BaseActivity() {

    private lateinit var mLetterTv:TextView
    private lateinit var mLetterSideBar: LetterSideBar

    override fun getLayoutId(): Int {
        return R.layout.activity_letter_side_bar
    }

    override fun initView() {
        mLetterTv = letter_tv
        mLetterSideBar = letter_side_bar
    }

    override fun initListener() {
        mLetterSideBar.setOnLettersSelectedListener(object : LetterSideBar.OnLettersSelected {

            override fun selected(letter: String?) {
                if(letter.isNullOrEmpty()){
                    mLetterTv.gone()
                }else{
                    mLetterTv.text = letter
                    mLetterTv.visible()
                }
            }
        })

    }

    override fun initData() {
    }

}