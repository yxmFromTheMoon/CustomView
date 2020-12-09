package com.yxm.customview.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.yxm.customview.R
import com.yxm.customview.dp

/**
 * Created by Myron at 2020/12/9 19:51
 * @email yxmbest@163.com
 * @description
 */
class CameraView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : View(context, attributeSet) {
    private val width = 200.dp
    private val height = 200.dp
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPadding = 50.dp
    private val bitmap = getBitmap(width.toInt())

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val clipPath = Path().apply {
        addOval(10f, 10f, 50.dp, 50.dp, Path.Direction.CCW)
    }
    private val camera = Camera()

    init {
        camera.rotateX(45f)
        camera.setLocation(0f,0f,-6 * resources.displayMetrics.density)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.translate((mPadding + width / 2), (mPadding + height / 2))
        canvas.rotate(-60f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-width,-height,width,0f)
        canvas.rotate(60f)
        canvas.translate(-(mPadding + width / 2), -(mPadding + height / 2))
//        canvas.drawRect(mPadding, mPadding, width, height, mPaint)
        canvas.drawBitmap(bitmap,mPadding,mPadding,null)
        canvas.restore()

        canvas.save()
        canvas.translate((mPadding + width / 2), (mPadding + height / 2))
        canvas.rotate(-60f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-width,0f,width,height)
        canvas.rotate(60f)
        canvas.translate(-(mPadding + width / 2), -(mPadding + height / 2))
        //canvas.drawRect(mPadding, mPadding, width, height, mPaint)
        canvas.drawBitmap(bitmap,mPadding,mPadding,null)
        canvas.restore()
    }

    private fun getBitmap(width:Int):Bitmap{
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources,R.drawable.test,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources,R.drawable.test,options)
    }
}