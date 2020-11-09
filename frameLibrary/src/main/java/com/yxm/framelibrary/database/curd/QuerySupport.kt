package com.yxm.framelibrary.database.curd

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.yxm.framelibrary.database.DaoUtils
import java.lang.reflect.Method
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/11/3 21:32
 * @description
 */
class QuerySupport<T>(private val sqlSQLiteDatabase: SQLiteDatabase, private val mClazz: Class<T>) {
    private var mColumns: Array<out String>? = null
    private var mSelection: String? = null
    private var mSelectionArgs: Array<out String>? = null
    private var mGroupBy: String? = null
    private var mHaving: String? = null
    private var mOrderBy: String? = null
    private var mLimit: String? = null

    fun columns(vararg columns: String): QuerySupport<T> {
        mColumns = columns
        return this
    }

    fun limit(limit: String?): QuerySupport<T> {
        mLimit = limit
        return this
    }

    fun selection(selection: String?): QuerySupport<T> {
        mSelection = selection
        return this
    }

    fun selectionArgs(vararg selectionArgs: String): QuerySupport<T> {
        mSelectionArgs = selectionArgs
        return this
    }

    fun groupBy(groupBy: String?): QuerySupport<T> {
        mGroupBy = groupBy
        return this
    }

    fun having(having: String?): QuerySupport<T> {
        mHaving = having
        return this
    }

    fun orderBy(orderBy: String?): QuerySupport<T> {
        mOrderBy = orderBy
        return this
    }

    fun query(): List<T> {
        val cursor = sqlSQLiteDatabase.query(mClazz.simpleName, mColumns, mSelection,
                mSelectionArgs, mGroupBy, mHaving, mOrderBy, mLimit)
        clearQueryParams()
        return cursorToList(cursor)
    }

    private fun clearQueryParams() {
        mColumns = null
        mSelection = null
        mSelectionArgs = null
        mGroupBy = null
        mHaving = null
        mOrderBy = null
        mLimit = null
    }

    private fun cursorToList(cursor: Cursor?): List<T> {
        val list = ArrayList<T>()
        if (cursor != null && cursor.moveToFirst()) {
            // 不断的从游标里面获取数据
            do {
                try {
                    val instance = mClazz.newInstance()
                    val fields = mClazz.declaredFields
                    for (field in fields) {
                        // 遍历属性
                        field.isAccessible = true
                        val name = field.name
                        // 获取角标  获取在第几列
                        val index = cursor.getColumnIndex(name)
                        if (index == -1) {
                            continue
                        }
                        // 通过反射获取 游标的方法  field.getType() -> 获取的类型
                        val cursorMethod = cursorMethod(field.type)
                        if (cursorMethod != null) {
                            // 通过反射获取了 value
                            var value: Any? = cursorMethod.invoke(cursor, index) ?: continue

                            // 处理一些特殊的部分
                            if (field.type === Boolean::class.javaPrimitiveType || field.type === Boolean::class.java) {
                                if ("0" == value.toString()) {
                                    value = false
                                } else if ("1" == value.toString()) {
                                    value = true
                                }
                            } else if (field.type === Char::class.javaPrimitiveType || field.type === Char::class.java) {
                                value = (value as String)[0]
                            } else if (field.type === Date::class.java) {
                                val date = value as Long
                                if (date <= 0) {
                                    value = null
                                } else {
                                    value = Date(date)
                                }
                            }
                            // 通过反射注入
                            field.set(instance, value)
                        }
                    }
                    // 加入集合
                    list.add(instance)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } while (cursor.moveToNext())
        }
        return list
    }

    // 获取游标的方法
    @Throws(java.lang.Exception::class)
    private fun cursorMethod(type: Class<*>): Method? {
        val methodName = getColumnMethodName(type)
        // type String getString(index); int getInt; boolean getBoolean
        return Cursor::class.java.getMethod(methodName!!, Int::class.javaPrimitiveType)
    }

    /**
     * 获取游标方法名
     */
    private fun getColumnMethodName(fieldType: Class<*>): String? {
        val typeName = if (fieldType.isPrimitive) {
            DaoUtils.capitalize(fieldType.name)
        } else {
            fieldType.simpleName
        }
        var methodName = "get$typeName"
        if ("getBoolean" == methodName) {
            methodName = "getInt"
        } else if ("getChar" == methodName || "getCharacter" == methodName) {
            methodName = "getString"
        } else if ("getDate" == methodName) {
            methodName = "getLong"
        } else if ("getInteger" == methodName) {
            methodName = "getInt"
        }
        return methodName
    }
}