package com.yxm.baselibrary

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.ActivityManager
import android.content.Context
import android.graphics.PointF
import android.os.Process
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import okio.buffer
import okio.sink
import okio.source
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader


/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/20 22:12
 * @Description
 */
object Utils {

    @JvmStatic
    fun sp2px(view: View, sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, view.resources.displayMetrics).toInt()
    }

    @JvmStatic
    fun dp2px(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

    @JvmStatic
    fun intValueAnimator(start: Int, end: Int, duration: Long, listener: ValueAnimator.AnimatorUpdateListener) {
        val animator = ObjectAnimator.ofInt(start, end)
        animator.duration = duration
        animator.addUpdateListener(listener)
        animator.start()
    }

    @JvmStatic
    fun floatValueAnimator(start: Float, end: Float, duration: Long, listener: ValueAnimator.AnimatorUpdateListener) {
        val animator = ObjectAnimator.ofFloat(start, end)
        animator.duration = duration
        animator.addUpdateListener(listener)
        animator.start()
    }

    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    fun getStatusBarHeight(context: Context): Int {
        var height = 0
        val resourceId: Int = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = context.resources.getDimensionPixelSize(resourceId)
        }
        return height
    }

    /**
     * 获取百分比对应的距离
     */
    fun evaluateValue(fraction: Float, start: Number, end: Number): Float {
        return start.toFloat() + (end.toFloat() - start.toFloat()) * fraction
    }

    /**
     * 根据百分比获取点
     */
    fun getPointByPercent(startPoint: PointF, endPointF: PointF, fraction: Float): PointF {
        return PointF(evaluateValue(fraction, startPoint.x, endPointF.x),
                evaluateValue(fraction, startPoint.y, endPointF.y))
    }

    /**
     * 使用okio拷贝资源文件夹下的文件
     * @param context Context
     * @param assetsFileName String
     * @param targetDir String
     * @return File
     */
    fun copyAssetsFile(context: Context, assetsFileName: String, targetFile: String): File {
        val file = File("${context.cacheDir}${File.separator}$targetFile")
        if (file.exists()) {
            file.delete()
        }
        val assets = context.assets.open(assetsFileName)
        val source = assets.source()
        val bufferSkin = file.sink().buffer()
        bufferSkin.writeAll(source)
        return file
    }

    fun isMainProcess(context: Context): Boolean {
        val processName: String? = getCurProcessName(context)
        return if (processName != null && processName.contains(":")) {
            false
        } else processName != null && processName == context.packageName
    }

    private fun getCurProcessName(context: Context): String? {
        var processName: String? = ""
        if (!TextUtils.isEmpty(processName)) {
            return processName
        }
        try {
            val pid = Process.myPid()
            val mActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (appProcess in mActivityManager.runningAppProcesses) {
                if (appProcess.pid == pid) {
                    processName = appProcess.processName
                    return processName
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        processName = getCurProcessNameFromProc()
        return processName
    }

    private fun getCurProcessNameFromProc(): String? {
        var cmdlineReader: BufferedReader? = null
        try {
            cmdlineReader = BufferedReader(InputStreamReader(
                    FileInputStream(
                            "/proc/" + Process.myPid() + "/cmdline"),
                    "iso-8859-1"))
            var c: Int
            val processName = StringBuilder()
            while (cmdlineReader.read().also { c = it } > 0) {
                processName.append(c.toChar())
            }
            return processName.toString()
        } catch (e: Throwable) {
            // ignore
        } finally {
            if (cmdlineReader != null) {
                try {
                    cmdlineReader.close()
                } catch (e: java.lang.Exception) {
                    // ignore
                }
            }
        }
        return null
    }
}