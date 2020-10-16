package com.yxm.baselibrary.imageloader

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.load
import coil.loadAny
import coil.request.ImageRequest

/**
 *@author: yxm
 *@time: 2020/10/16
 *@description:
 */
class CoilImageLoader(context: Context) : BaseImageLoader(context) {

    private val mContext = context

    override fun displayDefault(imageView: ImageView?, path: String?, loadingResId: Int, failResId: Int) {
        imageView!!.load(path) {
            placeholder(loadingResId)
            error(failResId)
        }
    }

    override fun displayWithWidthAndHeight(imageView: ImageView?, path: String?, width: Int, height: Int) {
        imageView!!.load(path) {
            size(width, height)
        }
    }

    override fun displayImage(imageView: ImageView, resId: Int) {
        imageView.loadAny(resId){

        }
    }

    override fun download(path: String?, delegate: DownloadCallback?) {
        val imageLoader = mContext.imageLoader
        val request = ImageRequest.Builder(mContext)
                .data(path)
                .target(onSuccess = { result ->
                    delegate?.onSuccess(path, result.toBitmap())
                }, onError = { error ->
                    delegate?.onFailed(path)
                })
                .build()
        imageLoader.enqueue(request)
    }

    override fun pause(activity: Activity?) {

    }

    override fun resume(activity: Activity?) {

    }
}