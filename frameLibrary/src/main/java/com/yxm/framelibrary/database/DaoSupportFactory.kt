package com.yxm.framelibrary.database

import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import com.yxm.baselibrary.net.User
import java.io.File

/**
 * @author yxm
 * @email yxmbest@163.com
 * 2020/10/31 14:29
 * @description
 */
object DaoSupportFactory {
    private var mSqlSQLiteDatabase: SQLiteDatabase

    //持有外部数据库的引用
    init {
        //别忘了动态申请读取权限
        val dbRoot = File(Environment.getExternalStorageDirectory()
                .absolutePath + File.separator + "test" + File.separator + "database")
        if (!dbRoot.exists()) {
            dbRoot.mkdirs()
        }
        val dbFile = File(dbRoot, "test.db")
        //打开数据库
        mSqlSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null)
    }

    fun <T> getDao(clazz: Class<T>): IDaoSupport<T> {
        val dao = DaoSupport<T>()
        dao.init(mSqlSQLiteDatabase, clazz)
        return DaoSupport()
    }
}