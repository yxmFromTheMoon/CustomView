package com.yxm.baselibrary.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
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

        fun setOnClickListener(viewId: Int, listener: View.OnClickListener): Builder {
            P!!.mClickArray.put(viewId, listener)
            return this
        }

        fun setContentView(layoutResId: Int): Builder {
            P!!.mView = null
            P!!.mViewLayoutResId = layoutResId
            return this
        }

        fun setContentView(view: View): Builder {
            P!!.mView = view
            P!!.mViewLayoutResId = 0
            return this
        }

        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?): Builder {
            P!!.mOnCancelListener = onCancelListener
            return this
        }

        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener?): Builder {
            P!!.mOnDismissListener = onDismissListener
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            P!!.mCancelable = cancelable
            return this
        }

        fun fullWidth(): Builder {
            P!!.mWidth = WindowManager.LayoutParams.MATCH_PARENT
            return this
        }

        fun fromBottom(isFromBottom: Boolean): Builder {
            if (isFromBottom) {
                addDefaultAnimation()
            }
            return this
        }

        private fun addDefaultAnimation() {

        }

        fun create(): AlertDialog {
            // Context has already been wrapped with the appropriate theme.
            val dialog = AlertDialog(P!!.mContext, P!!.mThemeResId)
            dialog.mAlert?.let { P!!.apply(it) }
            dialog.setCancelable(P!!.mCancelable)
            if (P!!.mCancelable) {
                dialog.setCanceledOnTouchOutside(true)
            }
            dialog.setOnCancelListener(P!!.mOnCancelListener)
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