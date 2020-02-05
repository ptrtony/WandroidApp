package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.WxArticleChaptersResp

interface WxArticleChaptersView : BaseView{
    fun onWxArticleChaptersResult(wxArticleChapers:List<WxArticleChaptersResp>)
}