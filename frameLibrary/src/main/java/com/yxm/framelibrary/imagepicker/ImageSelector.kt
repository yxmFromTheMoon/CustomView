package com.yxm.framelibrary.imagepicker

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity

/**
 * Created by Myron at 2020/11/15 19:01
 * @email yxmbest@163.com
 * @description
 */
class ImageSelector {
    private var mSelectMode = SelectPictureActivity.MULTI_SELECT_MODE
    private var mOriginData = ArrayList<String>()
    private var mIsShowCamera = false
    private var mMaxSelectSize = 9

    companion object {
        fun create(): ImageSelector {
            return ImageSelector()
        }
    }

    fun max(max: Int): ImageSelector {
        mMaxSelectSize = max
        return this
    }

    fun showCamera(): ImageSelector {
        mIsShowCamera = true
        return this
    }

    fun multi(): ImageSelector {
        mSelectMode = SelectPictureActivity.MULTI_SELECT_MODE
        return this
    }

    fun single(): ImageSelector {
        mSelectMode = SelectPictureActivity.SINGLE_SELECT_MODE
        return this
    }

    fun originData(data: List<String>): ImageSelector {
        mOriginData = data as ArrayList<String>
        return this
    }

    fun start(context: FragmentActivity, requestCode: Int) {
        context.startActivityForResult(addParamsIntent(context), requestCode)
    }

    private fun addParamsIntent(context: FragmentActivity): Intent {
        return Intent(context, SelectPictureActivity::class.java).apply {
            putExtra(SelectPictureActivity.IS_SHOW_CAMERA_KEY, mIsShowCamera)
            putExtra(SelectPictureActivity.SELECT_MODE_KEY, mSelectMode)
            putStringArrayListExtra(SelectPictureActivity.SELECTED_PICTURE_LIST_KEY, mOriginData)
            putExtra(SelectPictureActivity.MAX_SELECT_KEY, mMaxSelectSize)
        }
    }
}