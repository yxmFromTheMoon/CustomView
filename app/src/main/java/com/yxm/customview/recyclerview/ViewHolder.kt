package com.yxm.customview.recyclerview

import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.yxm.customview.view.TextView

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/9/12 13:03
 * @description
 */
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mViews = SparseArray<View>()
    private val mItemView = itemView

    fun <T : View> getView(viewId: Int): T {
        var view = mViews.get(viewId)

        if (view == null) {
            view = mItemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    fun setText(viewId: Int, text: String): ViewHolder {
        val textView = getView<TextView>(viewId)
        textView.setText(text)
        return this
    }

    fun setTextColor(viewId: Int, colorId: Int): ViewHolder {
        val textView = getView<TextView>(viewId)
        textView.setTextColor(colorId)
        return this
    }

    fun setImageResource(viewId: Int, resourceId: Int): ViewHolder {
        val imageView: ImageView = getView(viewId)
        imageView.setImageResource(resourceId)
        return this
    }

    fun setImagePath(viewId: Int, imageLoader: ImageLoader): ViewHolder {
        val imageView = getView<ImageView>(viewId)
        imageLoader.loadImage(imageView, imageLoader.mPath)
        return this
    }

    abstract class ImageLoader(path: String) {

        val mPath = path

        abstract fun loadImage(imageView: ImageView, path: String)

    }

}