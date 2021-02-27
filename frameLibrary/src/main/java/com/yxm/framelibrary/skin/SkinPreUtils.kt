package com.yxm.framelibrary.skin

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.edit

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/8 20:22
 * @description
 */
@SuppressLint("StaticFieldLeak")
object SkinPreUtils {
    private lateinit var mContext: Context

    fun init(context: Context) {
        mContext = context.applicationContext
    }

    fun saveSkinPath(path: String) {
        mContext.getSharedPreferences(SkinConfig.SKIN_INFO_NAME, Context.MODE_PRIVATE).edit {
            putString(SkinConfig.SKIN_PATH_NAME, path).apply()
        }
    }

    fun getSkinPath(): String? {
        return mContext.getSharedPreferences(SkinConfig.SKIN_INFO_NAME, Context.MODE_PRIVATE)
                .getString(SkinConfig.SKIN_PATH_NAME, "")
    }

    fun clearSkinPath() {
        mContext.getSharedPreferences(SkinConfig.SKIN_INFO_NAME, Context.MODE_PRIVATE).edit {
            clear()
        }
    }
}