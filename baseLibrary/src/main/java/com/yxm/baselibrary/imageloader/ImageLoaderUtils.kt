package com.yxm.baselibrary.imageloader

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.yxm.baselibrary.R
import com.yxm.baselibrary.base.BaseActivity
import java.lang.Exception
import java.lang.RuntimeException

/**
 *@author: yxm
 *@time: 2020/10/16
 *@description:
 */
object ImageLoaderUtils {
    private var mImageLoader: BaseImageLoader? = null
    private lateinit var mContext: Context

    fun init(context: Context) {
        mContext = context
        //默认使用Glide加载
        mImageLoader = GlideImageLoader(context)
    }

    private fun getImageLoader(): BaseImageLoader {
        if (mImageLoader != null) {
            return mImageLoader as BaseImageLoader
        }
        mImageLoader = when {
            isClazzExits("com.bumptech.glide.Glide") -> {
                GlideImageLoader(mContext)
            }
            isClazzExits("coil.Coil") -> {
                CoilImageLoader(mContext)
            }
            else -> {
                throw RuntimeException("找不到图片加载库")
            }
        }
        return mImageLoader as BaseImageLoader
    }

    private fun isClazzExits(clazzName: String): Boolean {
        return try {
            Class.forName(clazzName)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    fun displayImage(imageView: ImageView, path: String,
                     @DrawableRes loadingResId: Int = R.drawable.bga_pp_ic_holder_light,
                     @DrawableRes failResId: Int = R.drawable.bga_pp_ic_holder_light) {
        try {
            getImageLoader().displayDefault(imageView, path, loadingResId, failResId)
        } catch (e: Exception) {
            Log.d("load_picture", "加载图片失败")
        }
    }

    fun displayWithWidthAndHeight(imageView: ImageView, path: String, width: Int, height: Int) {
        try {
            getImageLoader().displayWithWidthAndHeight(imageView, path, width, height)
        } catch (e: Exception) {
            Log.d("load_picture", "加载图片失败")
        }
    }

    fun displayImage(imageView: ImageView, resId: Int) {
        try {
            getImageLoader().displayImage(imageView, resId)
        } catch (e: Exception) {
            Log.d("load_picture", "加载图片失败")
        }
    }

    /**
     * 设置图片加载器
     */
    fun setImageLoader(imageLoader: BaseImageLoader) {
        mImageLoader = imageLoader
    }

    fun downLoad(path: String, callback: BaseImageLoader.DownloadCallback) {
        getImageLoader().download(path, callback)
    }

    fun pause(activity: BaseActivity) {
        getImageLoader().pause(activity)
    }

    fun resume(activity: BaseActivity) {
        getImageLoader().resume(activity)
    }
}