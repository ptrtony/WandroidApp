package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.HomeBannerResp

interface HomeView :BaseView{
    fun homeBanner(banners:List<HomeBannerResp>)

}