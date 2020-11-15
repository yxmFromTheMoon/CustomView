package com.yxm.framelibrary.imagepicker

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxm.baselibrary.imageloader.ImageLoaderUtils
import com.yxm.baselibrary.recyclerview.CommonAdapter
import com.yxm.baselibrary.recyclerview.ItemClickListener
import com.yxm.baselibrary.recyclerview.ViewHolder
import com.yxm.framelibrary.BaseSkinActivity
import com.yxm.framelibrary.R
import com.yxm.framelibrary.SquareImageView
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
    private var mSelectedPictureList: ArrayList<String>? = ArrayList()
    private var isFirstResume = true

    /**
     * UI
     */
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBackIv: ImageView
    private lateinit var mConfirmTv: TextView
    private lateinit var mAdapter: CommonAdapter<String>


    override fun getLayoutId(): Int {
        return R.layout.activity_select_picture
    }

    override fun initView() {
        mRecyclerView = picture_rv
        mBackIv = back_iv
        mConfirmTv = confirm_tv

        mSelectMode = intent.getIntExtra(SELECT_MODE_KEY, mSelectMode)
        mIsShowCamera = intent.getBooleanExtra(IS_SHOW_CAMERA_KEY, mIsShowCamera)
        mMaxPictureSize = intent.getIntExtra(MAX_SELECT_KEY, mMaxPictureSize)
        mSelectedPictureList = intent.getStringArrayListExtra(SELECTED_PICTURE_LIST_KEY)
        if (mSelectedPictureList == null) {
            mSelectedPictureList = ArrayList()
        }
    }

    override fun initListener() {
        mBackIv.setOnClickListener {
            finish()
        }
        mConfirmTv.setOnClickListener {

        }
    }

    private val mLoaderCallback = object : LoaderManager.LoaderCallbacks<Cursor> {
        val IMAGE_PROJECTION = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID)

        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            // 查询数据库一样 语句

            return CursorLoader(this@SelectPictureActivity,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    "${IMAGE_PROJECTION[4]} >0 AND ${IMAGE_PROJECTION[3]}=? OR ${IMAGE_PROJECTION[3]}=?",
                    arrayOf("image/jpeg", "image/png"), "${IMAGE_PROJECTION[2]} DESC")
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
        LoaderManager.getInstance(this).initLoader(LOADER_TYPE, null,
                mLoaderCallback)
    }

    private fun showImageList(images: MutableList<String>) {
        mAdapter = object : CommonAdapter<String>(this, R.layout.item_select_picture, images) {
            override fun convert(holder: ViewHolder, item: String, position: Int) {
                val selectedIndicator = holder.getView<ImageView>(R.id.media_selected_indicator)
                val cameraLayout = holder.getView<LinearLayout>(R.id.camera_ll)
                val image = holder.getView<SquareImageView>(R.id.image)
                val mask = holder.getView<View>(R.id.mask)
                if (item == "") {
                    //拍照
                    mask.visibility = View.VISIBLE
                    cameraLayout.visibility = View.VISIBLE
                    selectedIndicator.visibility = View.GONE
                    image.visibility = View.GONE
                } else {
                    mask.visibility = View.GONE
                    image.visibility = View.VISIBLE
                    cameraLayout.visibility = View.GONE
                    selectedIndicator.visibility = View.VISIBLE
                    ImageLoaderUtils.displayImage(image, item)
                }
            }
        }
        mRecyclerView.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(this@SelectPictureActivity, 4)
        }
        mAdapter.setItemClickListener(object : ItemClickListener {
            override fun onItemClick(holder: ViewHolder, position: Int) {
                val selectedIndicator = holder.getView<ImageView>(R.id.media_selected_indicator)
                val mask = holder.getView<View>(R.id.mask)
                selectedIndicator.isSelected = true
                mask.visibility = View.VISIBLE
            }
        })
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