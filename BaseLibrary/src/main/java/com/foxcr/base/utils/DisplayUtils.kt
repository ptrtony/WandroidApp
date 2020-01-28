package com.foxcr.base.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

object DisplayUtils {
    private fun getDisplayMetrics(): DisplayMetrics{
        return Resources.getSystem().displayMetrics
    }

    fun sp2Px(spValue:Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue.toFloat(),getDisplayMetrics()).toInt()
    }

    fun dp2px(dp:Float):Int{
        return (getDisplayMetrics().density*0.5 + dp).toInt()
    }

    fun px2Dp(px:Int):Int{
        return (px/ getDisplayMetrics().density + 0.5).toInt()
    }

}