package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.CollectArticleListResp

interface CollectView : BaseView{
    fun onCollectListResult(collectArticleList: CollectArticleListResp)
    fun onUncollectResult(baseNoneResponseResult: BaseNoneResponseResult)
}