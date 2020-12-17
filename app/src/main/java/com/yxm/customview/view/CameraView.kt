package com.yxm.customview.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.graphics.withSave
import com.yxm.customview.dp
import com.yxm.customview.utils.Utils

/**
 * Created by Myron at 2020/12/9 19:51
 * @email yxmbest@163.com
 * @description
 */
class CameraView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : View(context, attributeSet) {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPadding = 50.dp
    private val bitmap = Utils.getBitmap(context,SIZE.toInt())
    var flipRotation = 0f
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
            rotate(-flipRotation)
            camera.save()
            camera.rotateX(flipTop)
            camera.applyToCanvas(canvas)
            camera.restore()
            clipRect(-SIZE, -SIZE, SIZE, 0f)
            rotate(flipRotation)
            translate(-(mPadding + SIZE / 2), -(mPadding + SIZE / 2))
            drawBitmap(bitmap, mPadding, mPadding, null)
        }

        canvas.withSave {
            translate((mPadding + SIZE / 2), (mPadding + SIZE / 2))
            rotate(-flipRotation)
            camera.save()
            camera.rotateX(flipBottom)
            camera.applyToCanvas(canvas)
            camera.restore()
            clipRect(-SIZE, 0f, SIZE, SIZE)
            rotate(flipRotation)
            translate(-(mPadding + SIZE / 2), -(mPadding + SIZE / 2))
            drawBitmap(bitmap, mPadding, mPadding, null)
        }

    }

    companion object {
        private val SIZE = 200.dp
    }
}