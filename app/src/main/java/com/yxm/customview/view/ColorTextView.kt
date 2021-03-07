package com.yxm.customview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import com.yxm.customview.dp
import kotlin.random.Random

/**
 * Created by Myron at 2021/3/7 14:57
 * @email yxmbest@163.com
 * @description
 */
class ColorTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : AppCompatTextView(context, attributeSet) {

    private val COLORS = intArrayOf(Color.parseColor("#373AB7"),
            Color.parseColor("#ffff819f"),
            Color.parseColor("#ff71414f"),
            Color.parseColor("#ff00a0e9"),
            Color.parseColor("#ffff9f37"),
            Color.parseColor("#ffaa3333"),
            Color.parseColor("#ff4f88e7"))

    private val TEXT_SIZES = intArrayOf(14, 16, 18, 20)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val random = java.util.Random()

    init {
        setTextColor(Color.WHITE)
        textSize = TEXT_SIZES[random.nextInt(4)].toFloat()
        paint.color = COLORS[random.nextInt(COLORS.size)]
        setPadding(16, 8, 16, 8)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), 4.dp, 4.dp, paint)
        super.onDraw(canvas)
    }
}