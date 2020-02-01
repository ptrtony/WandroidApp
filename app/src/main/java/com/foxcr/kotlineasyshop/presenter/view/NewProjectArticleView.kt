package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleProjectListResp

interface NewProjectArticleView : BaseView{
    fun onNewProjectArticleResult(homeArticleProjectListResp: HomeArticleProjectListResp)
}