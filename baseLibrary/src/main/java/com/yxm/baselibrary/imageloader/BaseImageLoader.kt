package com.yxm.baselibrary.imageloader

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes

/**
 *@author: yxm
 *@time: 2020/10/16
 *@description: 图片加载引擎基类
 */
abstract class BaseImageLoader {

    protected open fun getPath(path: String?): String? {
        var result = path
        if (result == null) {
            result = ""
        }
        if (!result.startsWith("http") && !result.startsWith("file")) {
            result = "file://$result"
        }
        return result
    }

    abstract fun displayDefault(imageView: ImageView?, path: String?,
                                @DrawableRes loadingResId: Int, @DrawableRes failResId: Int)

    abstract fun displayWithWidthAndHeight(imageView: ImageView?, path: String?,
                                           width: Int, height: Int)

    abstract fun displayImage(imageView: ImageView, resId: Int)

    abstract fun download(path: String?, delegate: DownloadCallback?)

    abstract fun pause(activity: Activity?)

    abstract fun resume(activity: Activity?)


    interface DisplayCallback {
        fun onSuccess(view: View?, path: String?)
    }

    interface DownloadCallback {
        fun onSuccess(path: String?, bitmap: Bitmap?)
        fun onFailed(path: String?)
    }
}