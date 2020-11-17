package com.yxm.baselibrary.imageloader

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import coil.intercept.Interceptor
import coil.load
import coil.loadAny
import coil.request.ImageRequest
import coil.request.ImageResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

/**
 *@author: yxm
 *@time: 2020/10/16
 *@description:
 */
class CoilImageLoader(context: Context) : BaseImageLoader(context) {

    private val mContext = context
    @ExperimentalCoilApi
    private val mImageLoader = ImageLoader.Builder(mContext).componentRegistry {
        add(PauseInterceptor())
    }.build()

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
        imageView.loadAny(resId) {

        }
    }

    @ExperimentalCoilApi
    override fun download(path: String?, delegate: DownloadCallback?) {

        val request = ImageRequest.Builder(mContext)
                .data(path)
                .target(onSuccess = { result ->
                    delegate?.onSuccess(path, result.toBitmap())
                }, onError = { error ->
                    delegate?.onFailed(path)
                })
                .build()
        mImageLoader.enqueue(request)
    }

    override fun pause(activity: Activity?) {

    }

    override fun resume(activity: Activity?) {

    }

    @ExperimentalCoilApi
    class PauseInterceptor : Interceptor {

        @ExperimentalCoroutinesApi
        val isPaused = MutableStateFlow(false)

        @ExperimentalCoroutinesApi
        override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
            isPaused.first {
                !it
            }
            return chain.proceed(chain.request)
        }
    }
}