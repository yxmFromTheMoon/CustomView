package com.yxm.framelibrary.skin

import android.app.Activity
import android.content.Context
import android.util.ArrayMap
import androidx.collection.arrayMapOf
import com.yxm.framelibrary.skin.attr.SkinView

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/8 13:41
 * @description 皮肤资源管理
 */
object SkinManager {

    private lateinit var mContext: Context
    private var mSkinViews: ArrayMap<Activity, MutableList<SkinView>> = ArrayMap()
    private var mSkinResource: SkinResource? = null

    /**
     * 防止内存泄漏
     * @param context Context
     */
    fun init(context: Context) {
        mContext = context.applicationContext
    }

    /**
     * 加载皮肤资源
     * @param path String
     * @return Int 返回结果是否成功
     */
    fun loadSkin(path: String): Int {
        //校验签名
        mSkinResource = SkinResource(mContext, path)
        //改变皮肤
        mSkinViews.keys.forEach {
            mSkinViews[it]?.forEach { skinView ->
                skinView.skin()
            }
        }
        return 0
    }

    /**
     * 恢复默认
     * @return Int
     */
    fun restoreDefault(): Int {
        return 0
    }

    fun getSkinResource(): SkinResource? {
        return mSkinResource
    }

    /**
     * 获取skinViews
     * @param activity Activity
     * @return List<SkinView>?
     */
    fun getSkinViews(activity: Activity): MutableList<SkinView>? {
        return mSkinViews[activity]
    }

    /**
     * 缓存SkinViews
     * @param activity Activity
     * @param skinViews MutableList<SkinView>
     */
    fun register(activity: Activity, skinViews: MutableList<SkinView>) {
        mSkinViews[activity] = skinViews
    }
}