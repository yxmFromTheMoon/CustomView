package com.yxm.customview.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.yxm.customview.utils.Utils

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/1 20:28
 * @description 仿携程loading view
 */
class CTripLoadingView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(context, attr, defStyle) {

    private var left: CircleView
    private var middle: CircleView
    private var right: CircleView

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        left = getCircleView()
        middle = getCircleView()
        right = getCircleView()
        left.exchangeColor(Color.RED)
        middle.exchangeColor(Color.GREEN)
        right.exchangeColor(Color.BLUE)
        addView(left)
        addView(middle)
        addView(right)
    }

    //1.添加透明度动画
    //2.添加缩放动画


    private fun getCircleView(): CircleView {
        val circleView = CircleView(context)
        val params = RelativeLayout.LayoutParams(Utils.dp2px(context, 8f),
                Utils.dp2px(context, 8f))
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        params.leftMargin = Utils.dp2px(context,10f)
        circleView.layoutParams = params
        return circleView
    }

}