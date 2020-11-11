package com.yxm.baselibrary.ui.banner

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View


/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/11 20:18
 * @description
 */
class DotIndicatorView @JvmOverloads
constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : View(context, attributeSet, defStyle) {

    private var drawable: Drawable? = null

    override fun onDraw(canvas: Canvas?) {
        if (drawable != null) {
            // 画圆
            val bitmap: Bitmap = drawableToBitmap(drawable!!)

            // 把Bitmap变为圆的
            val circleBitmap: Bitmap = getCircleBitmap(bitmap)

            // 把圆形的Bitmap绘制到画布上
            canvas?.drawBitmap(circleBitmap, 0f, 0f, null)
        }
    }

    /**
     * 获取圆形bitmap
     */
    private fun getCircleBitmap(bitmap: Bitmap): Bitmap {
        // 创建一个Bitmap
        val circleBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(circleBitmap)
        val paint = Paint()
        // 设置抗锯齿
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        // 设置仿抖动
        paint.isDither = true

        // 在画布上面画个圆
        canvas.drawCircle(measuredWidth / 2.toFloat(), measuredHeight / 2.toFloat(), measuredWidth / 2.toFloat(), paint)

        // 取圆和Bitmap矩形的交集
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        // 再把原来的Bitmap绘制到新的圆上面
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return circleBitmap
    }

    /**
     * 从drawable中得到Bitmap
     * @param drawable
     * @return
     */
    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        // 如果是BitmapDrawable类型
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        // 其他类型 ColorDrawable
        // 创建一个什么也没有的bitmap
        val outBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        // 创建一个画布
        val canvas = Canvas(outBitmap)

        // 把drawable化到Bitmap上
        drawable.setBounds(0, 0, measuredWidth, measuredHeight)
        drawable.draw(canvas)
        return outBitmap
    }

    /**
     * 设置Drawable
     */
    fun setDrawable(drawable: Drawable?) {
        this.drawable = drawable
        // 重新绘制View
        invalidate()
    }
}