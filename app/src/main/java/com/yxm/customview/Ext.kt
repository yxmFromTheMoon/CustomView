package com.yxm.customview

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.Resources.getSystem
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

inline fun <reified T> Context.startActivity(block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.block()
    this.startActivity(intent)
}

inline fun <reified T> Fragment.startActivity(block: Intent.() -> Unit){
    val intent = Intent(activity,T::class.java)
    intent.block()
    activity?.startActivity(intent)
}

inline fun <reified T> FragmentActivity.startActivityForResult(requestCode: Int, block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.block()
    this.startActivityForResult(intent, requestCode)
}

val Float.dp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            this, getSystem().displayMetrics)

val Int.dp: Float
    get() = this.toFloat().dp