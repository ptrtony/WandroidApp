package com.foxcr.base.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.foxcr.base.R

class BottomNavigationBar @JvmOverloads constructor(context: Context,attributeSet: AttributeSet?=null,defStyleAttr:Int = 0):BottomNavigationBar(
    context,attributeSet,defStyleAttr) {

    init {
        //首页
        val homeItem = BottomNavigationItem(R.mipmap.iv_main_bottom_navigation_home,resources.getString(R.string.nav_bar_home))
            .setInactiveIconResource(R.mipmap.iv_main_bottom_navigation_home_normal)
            .setActiveColor(R.color.text_light_dark)
            .setInActiveColor(R.color.text_normal)


        //我的
        val mineItem = BottomNavigationItem(R.mipmap.iv_main_bottom_navigation_home_normal,resources.getString(R.string.nav_bar_mine))
            .setInactiveIconResource(R.mipmap.iv_main_bottom_navigation_mine)
            .setActiveColor(R.color.text_light_dark)
            .setInActiveColor(R.color.text_normal)
        setMode(MODE_FIXED)
        setBackgroundStyle(BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.common_white)

        addItem(homeItem)
            .addItem(mineItem)
            .setFirstSelectedPosition(0)
            .initialise()
    }
}