package com.yxm.baselibrary.imageloader

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/**
 *@author: yxm
 *@time: 2020/10/16
 *@description:
 */
class GlideImageLoader(context: Context) : BaseImageLoader() {

    private val mContext = context

    /**
     * 加载图片默认方法
     * @param imageView
     * @param path
     * @param loadingResId
     * @param failResId
     */
    override fun displayDefault(imageView: ImageView?, path: String?, loadingResId: Int, failResId: Int) {
        imageView?.let {
            Glide.with(mContext)
                    .load(path)
                    .placeholder(loadingResId)
                    .error(failResId)
                    .into(imageView)
        }
    }

    /**
     * 加载指定宽高
     * @param imageView
     * @param path
     * @param width
     * @param height
     */
    override fun displayWithWidthAndHeight(imageView: ImageView?, path: String?, width: Int, height: Int) {
        imageView?.let {
            Glide.with(mContext)
                    .load(path)
                    .override(width, height)
                    .into(imageView)
        }
    }

    /**
     * 加载本地资源图片
     * @param imageView
     * @param resId
     */
    override fun displayImage(imageView: ImageView, resId: Int) {
        Glide.with(mContext)
                .load(resId)
                .into(imageView)
    }

    /**
     * 下载图片
     * @param path
     * @param delegate 回调
     */
    override fun download(path: String?, delegate: DownloadCallback?) {
        Glide.with(mContext)
                .asBitmap()
                .load(path)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        delegate?.onSuccess(path, resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        delegate?.onFailed(path)
                    }
                })
    }

    /**
     * 暂停加载
     * @param activity
     */
    override fun pause(activity: Activity?) {
        Glide.with(activity!!).pauseAllRequests()
    }

    /**
     * 恢复加载
     * @param activity
     */
    override fun resume(activity: Activity?) {
        Glide.with(activity!!).resumeRequests()
    }
}