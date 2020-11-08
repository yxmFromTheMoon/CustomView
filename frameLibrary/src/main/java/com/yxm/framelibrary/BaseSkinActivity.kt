package com.yxm.framelibrary

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewParent
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.ViewCompat
import com.yxm.baselibrary.base.BaseActivity
import com.yxm.framelibrary.skin.SkinAttrSupport
import com.yxm.framelibrary.skin.SkinManager
import com.yxm.framelibrary.skin.attr.SkinAttr
import com.yxm.framelibrary.skin.attr.SkinView
import com.yxm.framelibrary.skin.support.SkinAppCompatViewInflater

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/4 21:35
 * @description
 */
abstract class BaseSkinActivity : BaseActivity(), LayoutInflater.Factory2 {

    private var mSkinAppCompatViewInflater: SkinAppCompatViewInflater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutInflater = LayoutInflater.from(this)

        LayoutInflaterCompat.setFactory2(layoutInflater, this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        //拦截view的创建，解析属性
        //1.创建view
        val view = createView(parent, name, context, attrs)
        //2.解析属性 src ,textColor,background, 自定义view的属性？
        view?.let {
            val skinAttrs: List<SkinAttr> = SkinAttrSupport.getSkinViewsAttr(context, attrs)
            val skinView = SkinView(view, skinAttrs)
            //3.统一交给SkinManager处理
            managerSkinView(skinView)
            //4.要不要换肤
            SkinManager.checkChangeSkin(skinView)
        }

        Log.d("SkinView", "${view}")

        return view
    }

    /**
     * 统一管理skinView
     * @param skinView SkinView
     */
    private fun managerSkinView(skinView: SkinView) {
        var list = SkinManager.getSkinViews(this)
        if (list == null) {
            list = ArrayList()
            SkinManager.register(this, list)
        }
        list.add(skinView)
    }


    @SuppressLint("Recycle")
    private fun createView(parent: View?, name: String?, context: Context,
                           attrs: AttributeSet): View? {
        if (mSkinAppCompatViewInflater == null) {
            val a = mContext.obtainStyledAttributes(R.styleable.AppCompatTheme)
            val viewInflaterClassName = a.getString(R.styleable.AppCompatTheme_viewInflaterClass)
            mSkinAppCompatViewInflater = if (viewInflaterClassName == null) {
                // Set to null (the default in all AppCompat themes). Create the base inflater
                // (no reflection)
                SkinAppCompatViewInflater()
            } else {
                try {
                    val viewInflaterClass = Class.forName(viewInflaterClassName)
                    viewInflaterClass.getDeclaredConstructor()
                            .newInstance() as SkinAppCompatViewInflater
                } catch (t: Throwable) {
                    SkinAppCompatViewInflater()
                }
            }
        }
        val inheritContext = Build.VERSION.SDK_INT < 21 && shouldInheritContext(parent as ViewParent)

        return mSkinAppCompatViewInflater?.createView(parent, name, context, attrs, inheritContext,
                Build.VERSION.SDK_INT < 21,  /* Only read android:theme pre-L (L+ handles this anyway) */
                true,  /* Read read app:theme as a fallback at all times for legacy reasons */
                Build.VERSION.SDK_INT < 21 /* Only tint wrap the context if enabled */
        )
    }

    private fun shouldInheritContext(parent: ViewParent): Boolean {
        var parent: ViewParent? = parent
                ?: // The initial parent is null so just return false
                return false
        val windowDecor: View = window.decorView
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true
            } else if (parent === windowDecor || parent !is View
                    || ViewCompat.isAttachedToWindow((parent as View?)!!)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false
            }
            parent = parent.getParent()
        }
    }

}