package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.HomeOfenNetResp

interface OftenNetView:BaseView {
    fun onOftenNetResult(oftenNetDatas:List<HomeOfenNetResp>)
}