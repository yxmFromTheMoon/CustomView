package com.yxm.customview.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.graphics.withSave
import com.yxm.customview.R
import com.yxm.customview.dp

/**
 * Created by Myron at 2020/12/9 19:51
 * @email yxmbest@163.com
 * @description
 */
class CameraView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : View(context, attributeSet) {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPadding = 50.dp
    private val bitmap = getBitmap(SIZE.toInt())
    var cameraRotation = 0f
        set(value) {
            field = value
            invalidate()
        }

    var flipBottom = 0f
        set(value) {
            field = value
            invalidate()
        }

    var flipTop = 0f
        set(value) {
            field = value
            invalidate()
        }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val clipPath = Path().apply {
        addOval(10f, 10f, 50.dp, 50.dp, Path.Direction.CCW)
    }
    private val camera = Camera()

    init {
        camera.setLocation(0f, 0f, -6 * resources.displayMetrics.density)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        canvas.withSave {
            translate((mPadding + SIZE / 2), (mPadding + SIZE / 2))
            rotate(-flipTop)
            camera.rotateX(cameraRotation)
            camera.applyToCanvas(canvas)
            clipRect(-SIZE, -SIZE, SIZE, 0f)
            rotate(flipTop)
            translate(-(mPadding + SIZE / 2), -(mPadding + SIZE / 2))
            drawBitmap(bitmap, mPadding, mPadding, null)
        }

        canvas.withSave {
            translate((mPadding + SIZE / 2), (mPadding + SIZE / 2))
            rotate(-flipBottom)
            camera.rotateX(cameraRotation)
            camera.applyToCanvas(canvas)
            clipRect(-SIZE, 0f, SIZE, SIZE)
            rotate(flipBottom)
            translate(-(mPadding + SIZE / 2), -(mPadding + SIZE / 2))
            drawBitmap(bitmap, mPadding, mPadding, null)
        }

    }

    private fun getBitmap(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.test, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.test, options)
    }

    companion object {
        private val SIZE = 200.dp
    }
}