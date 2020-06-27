package com.yxm.customview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.yxm.customview.R
import com.yxm.customview.activity.ColorTrackTextViewActivity
import com.yxm.customview.startActivity
import kotlinx.android.synthetic.main.fragment_item.*

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/20 14:11
 * @Description
 */
class ItemFragment : Fragment() {

    private lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item, null)
                ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = item as TextView
        textView.text = arguments?.getString(TEXT)

        textView.setOnClickListener {
            context?.startActivity<ColorTrackTextViewActivity> {
                putExtra(TEXT, textView.text.toString())
            }
        }
    }

    companion object {
        const val TEXT = "text"

        fun newInstance(text: String): ItemFragment = ItemFragment().apply {
            val bundle = Bundle()
            bundle.putString(TEXT, text)
            arguments = bundle
        }
    }
}