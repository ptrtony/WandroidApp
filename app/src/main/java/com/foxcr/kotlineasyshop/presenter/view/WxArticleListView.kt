package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.WxArticleListResp

interface WxArticleListView :BaseView{
    fun onWxArticleListResult(wxArticleListResp: WxArticleListResp)
    fun onSearchWxArticleListResult(wxArticleListResp: WxArticleListResp)

    fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
    fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
}