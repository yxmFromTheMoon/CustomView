package com.yxm.baselibrary.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 *@author: yxm
 *@time: 2020/12/11
 *@description: BaseFragment based on Androidx
 */
abstract class BaseFragment : Fragment() {

    protected var mContext: BaseActivity? = null
    private var isFirstLoad = true
    private var mRootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null && bundle.size() > 0) {
            initArguments(bundle)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mContext = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        mRootView = view
        initView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * fragment可见时才会加载数据
     */
    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            initData()
            isFirstLoad = false
        }
    }

    abstract fun initView(view: View)

    abstract fun initData()

    abstract fun getLayoutId(): Int

    abstract fun initListener()

    override fun getContext(): BaseActivity? {
        return when {
            mContext == null -> {
                throw NullPointerException("HoldingActivity is null")
            }
            mContext != null -> {
                mContext
            }
            else -> {
                super.getContext() as BaseActivity
            }
        }
    }

    protected fun getRootView(): View? {
        mRootView?.let {
            return it
        }
        return null
    }

    /**
     * findViewById
     * @return View
     */
    protected open fun <T : View?> findViewById(viewId: Int): T {
        return mRootView?.findViewById<T>(viewId) as T
    }

    protected open fun showLongToast(str: String?) {
        Toast.makeText(mContext, str, Toast.LENGTH_LONG).show()
    }

    protected open fun showShortToast(str: String?) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show()
    }

    /**
     * 初始化argument，对于需要带参数的fragment可重写此方法获取参数
     */
    protected open fun initArguments(bundle: Bundle) {}

}