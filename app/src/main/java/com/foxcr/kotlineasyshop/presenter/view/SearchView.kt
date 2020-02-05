package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.SearchArticleResp

interface  SearchView : BaseView {
    fun onSearchResult(searchArticleResp: SearchArticleResp)
    fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
    fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
}