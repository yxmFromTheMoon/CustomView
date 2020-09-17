package com.yxm.customview

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.yxm.customview.basic.MyApplication

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/26 22:54
 * @Description 扩展工具类
 */

fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MyApplication.INSTANCE, this, duration).show()
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

inline fun <reified T> Context.startActivity(block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.block()
    this.startActivity(intent)
}