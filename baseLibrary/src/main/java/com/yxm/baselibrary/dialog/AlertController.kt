package com.yxm.baselibrary.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.View
import android.view.Window

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/13 20:43
 * @description
 */
class AlertController(dialog: AlertDialog, window: Window?) {
    private var mDialog = dialog
    private var mWindow = window


    fun getDialog(): AlertDialog {
        return mDialog
    }

    fun getWindow(): Window? {
        return mWindow
    }

    class AlertParams(context: Context, themesResId: Int) {
        var mContext: Context = context
        var mThemeResId: Int = themesResId
        var mCancelable = true
        var mOnCancelListener: DialogInterface.OnCancelListener? = null
        var mOnDismissListener: DialogInterface.OnDismissListener? = null
        var mOnKeyListener: DialogInterface.OnKeyListener? = null

        //布局view
        var mView: View? = null

        //布局Id
        var mViewLayoutResId: Int = 0

        //标题
        var mTitle: String? = null

        //存放字符串
        var mTextArray = SparseArray<CharSequence>()

        //存放点击事件
        var mClickArray = SparseArray<View.OnClickListener>()

        fun apply(alert: AlertController) {
            //配置各种参数
            var viewHelper: DialogViewHelper? = null
            if (mViewLayoutResId != 0) {
                viewHelper = DialogViewHelper(mContext, mViewLayoutResId)
            }
            if (mView != null) {
                viewHelper = DialogViewHelper()
                viewHelper.setContentView(mView)
            }

            //给dialog设置布局
            alert.getDialog().setContentView(viewHelper!!.getContentView())

            //设置文本
            val textArraySize = mTextArray.size()
            for (i in 0 until textArraySize) {
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i))
            }
            for (i in 0 until mClickArray.size()) {
                viewHelper.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i))
            }
        }
    }
}