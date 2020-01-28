package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.HomeSquareUserArticleListResp

interface SquareView : BaseView{
    fun onHomeSquareUserArticleList(homeSquareUserArticleListResp: HomeSquareUserArticleListResp)
}