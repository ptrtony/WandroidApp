package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.HomeKnowledgeSystemListResp
import com.foxcr.kotlineasyshop.data.protocal.HomeKnowledgeSystemResp

interface KnowledgeSystemView:BaseView {
    fun onKnowledgeSystemResult(knowledgeSystemData: List<HomeKnowledgeSystemResp>)
    fun onKnowledgeSystemListResult(knowledgeSystemListResp: HomeKnowledgeSystemListResp)
    fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
    fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
}