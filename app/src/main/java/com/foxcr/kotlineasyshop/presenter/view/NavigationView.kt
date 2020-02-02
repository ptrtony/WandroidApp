package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.HomeNavigationResp

interface NavigationView : BaseView {
    fun onNavigationResult(navigationDatas: List<HomeNavigationResp>)
}