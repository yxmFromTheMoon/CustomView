package com.yxm.baselibrary.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.yxm.baselibrary.R

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/13 20:43
 * @description
 */
class AlertDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    private var mAlert: AlertController? = null

    init {
        mAlert = AlertController(this, window)
    }

    fun setOnClickListener(viewId: Int, listener: View.OnClickListener) {
        mAlert!!.setOnClickListener(viewId, listener)
    }

    fun setText(viewId: Int, text: CharSequence) {
        mAlert!!.setText(viewId, text)
    }

    fun <T : View> getView(viewId: Int): T {
        return mAlert!!.getView(viewId) as T
    }

    class Builder(context: Context, themeResId: Int = R.style.dialog) {
        private var P: AlertController.AlertParams? = null

        init {
            P = AlertController.AlertParams(context, themeResId)
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setOnKeyListener(onKeyListener: DialogInterface.OnKeyListener?): Builder {
            P!!.mOnKeyListener = onKeyListener
            return this
        }

        fun setText(viewId: Int, text: CharSequence): Builder {
            P!!.mTextArray.put(viewId, text)
            return this
        }

        /**
         * Set the title using the given resource id.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        fun setTitle(title: String): Builder {
            P!!.mTitle = title
            return this
        }

        /**
         * 设置点击事件
         * @param viewId
         * @param listener
         */
        fun setOnClickListener(viewId: Int, listener: View.OnClickListener): Builder {
            P!!.mClickArray.put(viewId, listener)
            return this
        }

        /**
         * 设置contentView
         * @param layoutResId layoutId
         */
        fun setContentView(layoutResId: Int): Builder {
            P!!.mView = null
            P!!.mViewLayoutResId = layoutResId
            return this
        }

        /**
         * 设置contentView
         * @param view
         */
        fun setContentView(view: View): Builder {
            P!!.mView = view
            P!!.mViewLayoutResId = 0
            return this
        }

        /**
         * 设置dismiss监听
         * @param onDismissListener
         */
        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener?): Builder {
            P!!.mOnDismissListener = onDismissListener
            return this
        }

        /**
         * 设置点击外部是否取消dialog
         * @param cancelable
         */
        fun setCancelable(cancelable: Boolean): Builder {
            P!!.mCancelable = cancelable
            return this
        }

        /**
         * 宽度占满屏幕
         */
        fun fullWidth(): Builder {
            P!!.mWidth = WindowManager.LayoutParams.MATCH_PARENT
            return this
        }

        fun fullHeight(): Builder {
            P!!.mHeight = WindowManager.LayoutParams.MATCH_PARENT
            return this
        }

        /**
         * 设置宽高
         * @param width
         * @param height
         */
        fun setWidthAndHeight(width: Int, height: Int): Builder {
            P!!.mWidth = width
            P!!.mHeight = height
            return this
        }

        /**
         * 从底部弹出
         * @param isAnimation 是否带动画
         */
        fun fromBottom(isAnimation: Boolean): Builder {
            if (isAnimation) {
                P!!.mDefaultAnimationStyleId = R.style.fromBottomAnimation
            }
            P!!.mGravity = Gravity.BOTTOM
            return this
        }

        /**
         * 设置弹出的位置
         */
        fun setGravity(gravity: Int): Builder {
            P!!.mGravity = gravity
            return this
        }

        /**
         * 添加默认动画
         */
        fun addDefaultAnimation(): Builder {
            P!!.mDefaultAnimationStyleId = R.style.dialog_default_anim
            return this
        }

        /**
         * 添加自定义动画
         * @param styleAnimation
         */
        fun addAnimation(styleAnimation: Int): Builder {
            P!!.mDefaultAnimationStyleId = styleAnimation
            return this
        }

        fun create(): AlertDialog {
            // Context has already been wrapped with the appropriate theme.
            val dialog = AlertDialog(P!!.mContext, P!!.mThemeResId)
            dialog.mAlert?.let { P!!.apply(it) }
            dialog.setCancelable(P!!.mCancelable)
            if (P!!.mCancelable) {
                dialog.setCanceledOnTouchOutside(true)
            }
            dialog.setOnDismissListener(P!!.mOnDismissListener)
            if (P!!.mOnKeyListener != null) {
                dialog.setOnKeyListener(P!!.mOnKeyListener)
            }
            return dialog
        }

        fun show(): AlertDialog? {
            val dialog: AlertDialog = create()
            dialog.show()
            return dialog
        }
    }
}