package com.yxm.baselibrary.dialog

import android.content.Context
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.util.SparseArray
import android.view.*
import com.yxm.baselibrary.R

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/13 20:43
 * @description
 */
class AlertController(dialog: AlertDialog, window: Window?) {
    private var mDialogHelper: DialogViewHelper? = null
    private var mDialog = dialog
    private var mWindow = window


    fun getDialog(): AlertDialog {
        return mDialog
    }

    fun getWindow(): Window? {
        return mWindow
    }

    fun setViewHelper(viewHelper: DialogViewHelper) {
        mDialogHelper = viewHelper
    }

    fun setOnClickListener(viewId: Int, listener: View.OnClickListener) {
        mDialogHelper?.setOnClickListener(viewId, listener)
    }

    fun setText(viewId: Int, text: CharSequence) {
        mDialogHelper?.setText(viewId, text)
    }

    fun <T : View> getView(viewId: Int): T {
        return mDialogHelper?.getView<T>(viewId) as T
    }


    class AlertParams(context: Context, themesResId: Int) {
        var mGravity: Int = Gravity.CENTER
        var mContext: Context = context
        var mThemeResId: Int = themesResId
        var mCancelable = true

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

        //设置宽高
        var mWidth = WindowManager.LayoutParams.WRAP_CONTENT
        var mHeight = WindowManager.LayoutParams.WRAP_CONTENT

        var mDefaultAnimationStyleId: Int = 0

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
            alert.setViewHelper(viewHelper!!)

            //给dialog设置布局
            alert.getDialog().setContentView(viewHelper.getContentView())

            val window = alert.getWindow()
            window?.setGravity(mGravity)
            val windowParams = window?.attributes
            windowParams?.width = mWidth
            windowParams?.height = mHeight

            alert.getWindow()?.attributes = windowParams
            if (mDefaultAnimationStyleId != 0) {
                alert.getWindow()?.setWindowAnimations(mDefaultAnimationStyleId)
            }

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