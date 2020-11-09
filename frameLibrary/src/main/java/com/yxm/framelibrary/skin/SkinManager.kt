package com.yxm.framelibrary.skin

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.ArrayMap
import androidx.collection.arrayMapOf
import com.yxm.framelibrary.skin.attr.SkinView
import java.io.File

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/8 13:41
 * @description 皮肤资源管理
 */
object SkinManager {

    private lateinit var mContext: Context
    private var mSkinViews: ArrayMap<ISkinChangeCallback, MutableList<SkinView>> = ArrayMap()
    private var mSkinResource: SkinResource? = null

    /**
     * 防止内存泄漏
     * @param context Context
     */
    fun init(context: Context) {
        mContext = context.applicationContext
        //判断路径是否存在
        val skinPath = SkinPreUtils.getSkinPath()
        val file = File(skinPath!!)
        if (!file.exists()) {
            SkinPreUtils.clearSkinPath()
            return
        }
        val packageName = mContext.packageManager
                .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName as String
        if (packageName.isEmpty()) {
            SkinPreUtils.clearSkinPath()
            return
        }

        mSkinResource = SkinResource(mContext, skinPath)
    }

    /**
     * 加载皮肤资源
     * @param path String
     * @return Int 返回结果是否成功
     */
    fun loadSkin(path: String): Int {

        val skinPath = SkinPreUtils.getSkinPath()
        val file = File(skinPath!!)
        if (!file.exists()) {
            return SkinConfig.FILE_NOT_EXISTS
        }
        val packageName = mContext.packageManager
                .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName as String
        if (packageName.isEmpty()) {
            return SkinConfig.BAD_SKIN_RESOURCE
        }

        if (path == SkinPreUtils.getSkinPath()) {
            return SkinConfig.DEFAULT
        }

        //校验签名
        mSkinResource = SkinResource(mContext, path)
        //改变皮肤
        mSkinViews.keys.forEach {
            mSkinViews[it]?.forEach { skinView ->
                skinView.skin()
            }
        }
        saveSkinPath(path)
        return 0
    }

    /**
     * 保存皮肤资源路径
     * @param path String
     */
    private fun saveSkinPath(path: String) {
        SkinPreUtils.saveSkinPath(path)
    }

    /**
     * 恢复默认
     * @return Int
     */
    fun restoreDefault(): Int {
        //判断当前有没有切换皮肤
        if (SkinPreUtils.getSkinPath().isNullOrEmpty()) {
            return SkinConfig.DEFAULT
        }
        //当前运行的app的apk路径
        val defaultSkinPath = mContext.packageResourcePath

        mSkinResource = SkinResource(mContext, defaultSkinPath)
        mSkinViews.keys.forEach {
            mSkinViews[it]?.forEach { skinView ->
                skinView.skin()
            }
        }

        SkinPreUtils.clearSkinPath()
        return SkinConfig.SUCCESS
    }

    fun getSkinResource(): SkinResource? {
        return mSkinResource
    }

    /**
     * 获取skinViews
     * @param activity Activity
     * @return List<SkinView>?
     */
    fun getSkinViews(skinChangeCallback: ISkinChangeCallback): MutableList<SkinView>? {
        return mSkinViews[skinChangeCallback]
    }

    /**
     * 缓存SkinViews
     * @param activity Activity
     * @param skinViews MutableList<SkinView>
     */
    fun register(skinChangeCallback: ISkinChangeCallback, skinViews: MutableList<SkinView>) {
        mSkinViews[skinChangeCallback] = skinViews
    }

    /**
     * 防止类内泄漏
     * @param skinChangeCallback ISkinChangeCallback
     */
    fun unregister(skinChangeCallback: ISkinChangeCallback) {
        mSkinViews.remove(skinChangeCallback)
    }

    fun checkChangeSkin(skinView: SkinView) {
        if (!SkinPreUtils.getSkinPath().isNullOrEmpty()) {
            skinView.skin()
        }
    }
}