package com.yxm.framelibrary.database

import android.text.TextUtils
import java.util.*


/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/2 20:02
 * @description
 */
object DaoUtils {
    /**
     * 获取类名
     */
    fun getTableName(clazz: Class<*>): String {
        return clazz.simpleName
    }

    /**
     * 根据字段类型获取数据库字段类型
     */
    fun getColumnType(type: String): String {
        var value: String = ""
        if (type.contains("String")) {
            value = " text"
        } else if (type.contains("int")) {
            value = " integer"
        } else if (type.contains("boolean")) {
            value = " boolean"
        } else if (type.contains("float")) {
            value = " float"
        } else if (type.contains("double")) {
            value = " double"
        } else if (type.contains("char")) {
            value = " varchar"
        } else if (type.contains("long")) {
            value = " long"
        }
        return value
    }

    fun capitalize(string: String?): String? {
        if (!TextUtils.isEmpty(string)) {
            return string!!.substring(0, 1).toUpperCase(Locale.US) + string.substring(1)
        }
        return if (string == null) null else ""
    }
}