package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleListResp

interface NewBlogArticleView :BaseView{
    fun onNewBlogArticleListResult(homeArticleListResp: HomeArticleListResp)
}