package com.yxm.framelibrary.skin

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.yxm.framelibrary.skin.attr.SkinAttr
import com.yxm.framelibrary.skin.attr.SkinType

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/8 13:42
 * @description 皮肤属性解析support
 */
object SkinAttrSupport {

    /**
     * 获取skinAttr的属性
     * @param context Context
     * @param attrs AttributeSet
     * @return List<SkinAttr>
     */
    fun getSkinViewsAttr(context: Context, attrs: AttributeSet): List<SkinAttr> {
        val result = mutableListOf<SkinAttr>()
        //获取所有属性
        val arrLength = attrs.attributeCount
        for (i in 0 until arrLength) {
            val attrName = attrs.getAttributeName(i)
            val attrValue = attrs.getAttributeValue(i)
            Log.d("test_attr", "${attrName}->${attrValue}")
            //获取重要的属性
            val skinType = getSkinType(attrName)
            if (skinType != null) {
                val resName = getResName(context, attrValue)
                if (resName.isNullOrEmpty()) {
                    continue
                }
                Log.d("test_attr", "${resName}")

                val skinAttr = SkinAttr(resName, skinType)
                result.add(skinAttr)
            }
        }
        return result
    }

    /**
     * 获取资源的名称
     * @param context Context
     * @param attrValue String?
     * @return String
     */
    private fun getResName(context: Context, attrValue: String): String? {
        if (attrValue.startsWith("@")) {
            val resId = attrValue.substring(1).toInt()
            return context.resources.getResourceEntryName(resId)
        }
        return null
    }

    /**
     * 通过名称获取skinType
     * @param attrName String?
     * @return SkinType?
     */
    private fun getSkinType(attrName: String?): SkinType? {
        val skinTypes = SkinType.values()
        skinTypes.forEach {
            if (it.getResName() == attrName) {
                return it
            }
        }
        return null
    }

}