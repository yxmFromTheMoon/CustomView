package com.yxm.framelibrary.imagepicker

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.yxm.baselibrary.imageloader.ImageLoaderUtils
import com.yxm.baselibrary.recyclerview.CommonAdapter
import com.yxm.baselibrary.recyclerview.ViewHolder
import com.yxm.framelibrary.R
import com.yxm.framelibrary.SquareImageView

/**
 * Created by Myron at 2020/11/15 17:41
 * @email yxmbest@163.com
 * @description
 */
class SelectPictureAdapter(context: Context, data: List<String>, private val mSelectImageList: ArrayList<String>)
    : CommonAdapter<String>(context, R.layout.item_select_picture, data) {

    override fun convert(holder: ViewHolder, item: String, position: Int) {
        val selectedIndicator = holder.getView<ImageView>(R.id.media_selected_indicator)
        val cameraLayout = holder.getView<LinearLayout>(R.id.camera_ll)
        val image = holder.getView<SquareImageView>(R.id.image)
        val mask = holder.getView<View>(R.id.mask)
        selectedIndicator.isSelected = mSelectImageList.contains(item)
        if (item == "") {
            //拍照
            mask.visibility = View.VISIBLE
            cameraLayout.visibility = View.VISIBLE
            selectedIndicator.visibility = View.GONE
            image.visibility = View.GONE
        } else {
            mask.visibility = if (mSelectImageList.contains(item)) View.VISIBLE else View.GONE
            image.visibility = View.VISIBLE
            cameraLayout.visibility = View.GONE
            selectedIndicator.visibility = View.VISIBLE
            ImageLoaderUtils.displayImage(image, item)
        }
    }
}