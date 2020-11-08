package com.yxm.framelibrary.skin

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Environment
import java.io.File
import java.lang.Exception

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/8 13:46
 * @description 皮肤资源
 */
class SkinResource(context: Context, skinPath: String) {
    private var mResource: Resources? = null
    private var mPackageName: String? = null

    init {
        try {
            val superRes = context.resources
            val assetManager = AssetManager::class.java.newInstance()
            val method = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            method.invoke(assetManager, Environment.getExternalStorageDirectory().absolutePath
                    + File.separator + "test.skin")
            mResource = Resources(assetManager, superRes.displayMetrics, superRes.configuration)

            mPackageName = context.packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawableByName(name: String): Drawable? {
        try {
            val resId = mResource?.getIdentifier(name, "drawable", mPackageName)
            return mResource?.getDrawable(resId!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 通过名字获取color
     * @param name String
     * @return ColorStateList?
     */
    fun getColorByName(name: String): ColorStateList? {
        try {
            val resId = mResource?.getIdentifier(name, "color", mPackageName)
            return mResource?.getColorStateList(resId!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}