package com.yxm.customview.view

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.yxm.customview.dp

/**
 * Created by yxm at 2020/12/11
 * @time: 2020/12/11
 * @description:
 */
val provinces = listOf("北京市", "上海市", "天津市", "重庆市", "黑龙江省", "吉林省", "辽宁省", "河北省",
        "河南省", "陕西省", "山西省", "湖北省", "安徽省", "江苏省", "湖南省", "江西省", "广东省", "甘肃省", "浙江省", "福建省", "青海省",
        "台湾省", "新疆维吾尔族自治区", "宁夏回族自治区", "西藏自治区", "广西壮族自治区", "香港特别行政区", "澳门特别行政区")

class TypeEvaluatorView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : View(context, attributeSet) {
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mProvince = "北京市"
        set(value) {
            field = value
            invalidate()
        }

    init {
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 3.dp
        mPaint.textSize = 20.dp
    }

    override fun onDraw(canvas: Canvas) {
        val textLength = mPaint.measureText(mProvince)
        canvas.drawText(mProvince, (width / 2).toFloat() - textLength / 2, (height / 2).toFloat(), mPaint)
    }

    class StringEvaluator : TypeEvaluator<String> {
        override fun evaluate(fraction: Float, startValue: String?, endValue: String?): String {
            val startIndex = provinces.indexOf(startValue)
            val endIndex = provinces.indexOf(endValue)
            return provinces[((endIndex - startIndex) * fraction).toInt()]
        }

    }
}