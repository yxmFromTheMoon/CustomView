package com.yxm.framelibrary.skin.attr

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.yxm.framelibrary.skin.SkinManager

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/8 13:42
 * @description 要解析的view的属性
 */
enum class SkinType(private val mResName: String) {

    TEXT_COLOR("textColor") {
        override fun skin(view: View, resName: String) {
            val skinResource = SkinManager.getSkinResource()
            val color = skinResource?.getColorByName(resName)
            color?.let {
                view as TextView
                view.setTextColor(color)
            }
        }
    },
    BACKGROUND("background") {
        override fun skin(view: View, resName: String) {
            val skinResource = SkinManager.getSkinResource()
            val drawable = skinResource?.getDrawableByName(resName)
            drawable?.let {
                view as ImageView
                view.setBackground(it)
            }
            val color = skinResource?.getColorByName(resName)
            color?.let {
                view.setBackgroundColor(color.defaultColor)
            }
        }
    },
    SRC("src") {
        override fun skin(view: View, resName: String) {
            val skinResource = SkinManager.getSkinResource()
            val drawable = skinResource?.getDrawableByName(resName)
            drawable?.let {
                view as ImageView
                view.setImageDrawable(it)
            }
        }
    };

    fun getResName(): String {
        return mResName
    }

    abstract fun skin(view: View, resName: String)
}