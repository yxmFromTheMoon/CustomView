package com.yxm.customview.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import java.lang.ref.SoftReference

/**
 * Created by yxm on 2022/11/29 1:57 下午
 * @email: yinxiangming@lightinthebox.com
 * @description:
 */
class FramesSequenceAnimation(imageView: ImageView, frames: IntArray, fps: Int) {

    private val mFrames: IntArray // animation frames
    private var mIndex: Int // current frame

    private var mShouldRun: Boolean
    // true if the animation should continue running. Used to stop the animation

    private var mIsRunning: Boolean// true if the animation currently running. prevents starting the animation twice

    private val mSoftReferenceImageView: SoftReference<ImageView> // Used to prevent holding ImageView when it should be dead.

    private val mHandler: Handler = Handler(Looper.getMainLooper())

    private val mDelayMillis: Int

    var onAnimationStoppedListener: (() -> Unit?)? = null

    private var mBitmap: Bitmap? = null

    private val mBitmapOptions: BitmapFactory.Options

    private val next: Int
        get() {
            mIndex++
            if (mIndex >= mFrames.size) mIndex = 0
            return mFrames[mIndex]
        }

    init {
        mFrames = frames
        mIndex = -1
        mSoftReferenceImageView = SoftReference(imageView)
        mShouldRun = false
        mIsRunning = false
        mDelayMillis = 1000 / fps
        imageView.setImageResource(mFrames[0])

        // use in place bitmap to save GC work (when animation images are the same size & type)
        val bmp = (imageView.drawable as BitmapDrawable).bitmap
        val width = bmp.width
        val height = bmp.height
        val config = bmp.config
        mBitmap = Bitmap.createBitmap(width, height, config)
        mBitmapOptions = BitmapFactory.Options()
        // setup bitmap reuse options.
        mBitmapOptions.inBitmap = mBitmap
        mBitmapOptions.inMutable = true
        mBitmapOptions.inSampleSize = 1
    }

    /**
     * Starts the animation
     */
    @Synchronized
    fun start() {
        mShouldRun = true
        if (mIsRunning) return
        val runnable: Runnable = object : Runnable {
            override fun run() {
                val imageView = mSoftReferenceImageView.get()
                if (!mShouldRun || imageView == null) {
                    mIsRunning = false
                    onAnimationStoppedListener?.invoke()
                    return
                }
                mIsRunning = true
                mHandler.postDelayed(this, mDelayMillis.toLong())
                if (imageView.isShown) {
                    val imageRes = next
                    if (mBitmap != null) { // so Build.VERSION.SDK_INT >= 11
                        var bitmap: Bitmap? = null
                        try {
                            bitmap = BitmapFactory.decodeResource(
                                imageView.resources,
                                imageRes,
                                mBitmapOptions
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap)
                        } else {
                            imageView.setImageResource(imageRes)
                            mBitmap?.recycle()
                            mBitmap = null
                        }
                    } else {
                        imageView.setImageResource(imageRes)
                    }
                }
            }
        }
        mHandler.post(runnable)
    }

    /**
     * Stops the animation
     */
    @Synchronized
    fun stop() {
        mHandler.removeCallbacksAndMessages(null)
        mShouldRun = false
    }
}