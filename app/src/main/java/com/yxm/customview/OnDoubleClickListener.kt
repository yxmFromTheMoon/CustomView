package com.yxm.customview

import android.os.Handler
import android.view.MotionEvent
import android.view.View

/**
 * Created by Myron at 2021/3/7 18:28
 * @email yxmbest@163.com
 * @description
 */
class OnDoubleClickListener : View.OnTouchListener {

    private var countDownTimes = 0
    private var firstDownTime = 0L
    private var firstUpTime = 0L
    private var secondDownTime = 0L
    private val DURATION = 300
    private val handler = Handler()

    var listener: OnDoubleClickListener? = null

    interface OnDoubleClickListener {
        fun onDoubleClick()

        fun onClick()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                countDownTimes++
                //使用handler实现
                handler.postDelayed({
                    if (countDownTimes == 1) {
                        listener?.onClick()
                    } else if (countDownTimes == 2) {
                        listener?.onDoubleClick()
                    }
                    handler.removeCallbacksAndMessages(null)
                    countDownTimes = 0
                }, DURATION.toLong())

                //自己监听实现
//                if (countDownTimes == 1) {
//                    firstDownTime = System.currentTimeMillis()
//                } else if (countDownTimes == 2) {
//                    secondDownTime = System.currentTimeMillis()
//                    if (secondDownTime - firstDownTime < DURATION) {
//                        listener?.onDoubleClick()
//                        countDownTimes = 0
//                        firstDownTime = 0
//                        return true
//                    } else {
//                        firstDownTime = secondDownTime
//                        countDownTimes = 1
//                    }
//                    secondDownTime = 0
//                }
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return false
    }
}