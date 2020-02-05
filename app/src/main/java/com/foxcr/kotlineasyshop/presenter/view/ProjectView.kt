package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleProjectListResp
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleResp

interface ProjectView : BaseView{
    fun onNewProjectArticleResult(homeArticleProjectListResp: HomeArticleResp)
    fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
    fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
}