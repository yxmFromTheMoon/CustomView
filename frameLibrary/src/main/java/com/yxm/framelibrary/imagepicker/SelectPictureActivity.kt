package com.yxm.framelibrary.imagepicker

import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxm.baselibrary.recyclerview.ItemClickListener
import com.yxm.baselibrary.recyclerview.ViewHolder
import com.yxm.framelibrary.BaseSkinActivity
import com.yxm.framelibrary.R
import kotlinx.android.synthetic.main.activity_select_picture.*

/**
 * Created by Myron at 2020/11/15 11:02
 * @email yxmbest@163.com
 * @description
 */
class SelectPictureActivity : BaseSkinActivity() {

    companion object {
        const val SELECT_MODE_KEY = "select_mode"
        const val IS_SHOW_CAMERA_KEY = "is_show_camera"
        const val SELECTED_PICTURE_LIST_KEY = "selected_picture_list"
        const val MAX_SELECT_KEY = "max_select"
        const val RESULT_LIST_KEY = "result_list_key"

        //单选或者多选
        const val SINGLE_SELECT_MODE = 0
        const val MULTI_SELECT_MODE = 1
        const val LOADER_TYPE = 0x0021

    }

    //默认多选
    private var mSelectMode = MULTI_SELECT_MODE

    //是否显示拍照按钮
    private var mIsShowCamera = true

    //最大图片张数,默认是9
    private var mMaxPictureSize = 9

    //选择的图片集合
    private var mSelectedPictureList: ArrayList<String> = ArrayList()
    private var isFirstResume = true

    /**
     * UI
     */
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBackIv: ImageView
    private lateinit var mConfirmTv: TextView
    private lateinit var mAdapter: SelectPictureAdapter


    override fun getLayoutId(): Int {
        return R.layout.activity_select_picture
    }

    override fun initView() {
        mRecyclerView = picture_rv
        mBackIv = back_iv
        mConfirmTv = confirm_tv

        mSelectMode = intent.getIntExtra(SELECT_MODE_KEY, mSelectMode)
        mIsShowCamera = intent.getBooleanExtra(IS_SHOW_CAMERA_KEY, mIsShowCamera)
        mMaxPictureSize = if (mSelectMode == MULTI_SELECT_MODE) intent.getIntExtra(
            MAX_SELECT_KEY,
            mMaxPictureSize
        ) else 1
        mSelectedPictureList =
            intent?.getStringArrayListExtra(SELECTED_PICTURE_LIST_KEY) as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */
        if (mSelectedPictureList == null) {
            mSelectedPictureList = ArrayList()
        }
        updateSelectPictureCount()
    }

    override fun initListener() {
        mBackIv.setOnClickListener {
            finish()
        }
        mConfirmTv.setOnClickListener {
            Toast.makeText(
                this@SelectPictureActivity,
                "选了${mSelectedPictureList.size}张图片", Toast.LENGTH_SHORT
            ).show()
            setResult(RESULT_OK, Intent().apply {
                putStringArrayListExtra(RESULT_LIST_KEY, mSelectedPictureList)
            })
            finish()
        }
    }

    private val mLoaderCallback = object : LoaderManager.LoaderCallbacks<Cursor> {
        val IMAGE_PROJECTION = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media._ID
        )

        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            // 查询数据库一样 语句

            return CursorLoader(
                this@SelectPictureActivity,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                "${IMAGE_PROJECTION[4]} >0 AND ${IMAGE_PROJECTION[3]}=? OR ${IMAGE_PROJECTION[3]}=?",
                arrayOf("image/jpeg", "image/png"), "${IMAGE_PROJECTION[2]} DESC"
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            if (data != null && data.count > 0) {
                val images = mutableListOf<String>()
                //如果需要显示拍照
                if (mIsShowCamera) {
                    images.add("")
                }
                while (data.moveToNext()) {
                    val path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]))
                    images.add(path)
                }
                showImageList(images)
            }
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {

        }
    }

    override fun initData() {
        LoaderManager.getInstance(this).initLoader(
            LOADER_TYPE, null,
            mLoaderCallback
        )
    }

    private fun showImageList(images: MutableList<String>) {
        mAdapter = SelectPictureAdapter(this, images, mSelectedPictureList)

        mRecyclerView.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(this@SelectPictureActivity, 4)
        }

        mAdapter.setItemClickListener(object : ItemClickListener {
            override fun onItemClick(holder: ViewHolder, position: Int) {
                if (position == 0) {
                    //要申请拍照权限
                    Toast.makeText(
                        this@SelectPictureActivity,
                        "点击拍照", Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (mSelectedPictureList.size >= mMaxPictureSize) {
                    Toast.makeText(
                        this@SelectPictureActivity,
                        "最多只能选${mMaxPictureSize}张图片", Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (mSelectedPictureList.contains(images[position])) {
                    mSelectedPictureList.remove(images[position])
                } else {
                    mSelectedPictureList.add(images[position])
                }
                mAdapter.notifyDataSetChanged()
                updateSelectPictureCount()
            }
        })

    }

    private fun updateSelectPictureCount() {
        if (mSelectedPictureList.size > 0) {
            mConfirmTv.isEnabled = true
            mConfirmTv.setTextColor(Color.BLACK)
            mConfirmTv.text = "确定(${mSelectedPictureList.size}/${mMaxPictureSize})"
        } else {
            mConfirmTv.setTextColor(Color.GRAY)
            mConfirmTv.text = "确定"
            mConfirmTv.isEnabled = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
        } else {
            LoaderManager.getInstance(this).restartLoader(LOADER_TYPE, null, mLoaderCallback)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LoaderManager.getInstance(this).destroyLoader(LOADER_TYPE)
    }

}