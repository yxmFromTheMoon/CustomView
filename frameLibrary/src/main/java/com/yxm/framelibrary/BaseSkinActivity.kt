package com.yxm.framelibrary

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.LayoutInflaterCompat
import com.yxm.baselibrary.base.BaseActivity

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/4 21:35
 * @description
 */
abstract class BaseSkinActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val layoutInflater = LayoutInflater.from(this)
        //拦截view的创建
        LayoutInflaterCompat.setFactory2(layoutInflater, object : LayoutInflater.Factory2 {
            override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
                if (name == "Button") {
                    val tv = TextView(this@BaseSkinActivity)
                    tv.text = "拦截View"
                    return tv
                }
                return null
            }

            override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
                return null
            }
        })
        super.onCreate(savedInstanceState)
    }
}