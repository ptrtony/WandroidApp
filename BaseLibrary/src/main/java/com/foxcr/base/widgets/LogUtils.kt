package com.foxcr.base.widgets

import android.util.Log

object LogUtils {
    private val isDebug: Boolean = true
    private val TAG: String = "日常打log"

    /**
     *包装log.d日志
     */
    fun d(msg: String) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }

    /**
     *包装log.e日志
     */
    fun e(msg: String) {
        if (isDebug) {
            Log.e(TAG, msg)
        }
    }

    /**
     * v类型的log.v日志
     */
    fun v(msg: String) {
        if (isDebug) {
            Log.v(TAG, msg)
        }
    }

}