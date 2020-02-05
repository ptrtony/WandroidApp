package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.*

interface HomeView :BaseView{

    /**
     * 首页banner
     */
    fun homeBanner(banners:List<HomeBannerResp>)
    /**
     * 首页文章列表
     */
    fun homeArticleList(homeArticleResp: HomeArticleResp)

    /**
     * 最新项目tab (首页的第二个tab)
     */
    fun homeArticleProjectList(homeArticleResp: HomeArticleResp)

    fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
    fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
//    /**
//     * 置顶文章
//     */
//    fun homeTopArticle(homeTopArticleResp: HomeTopArticleResp)
//    /**
//     * 搜索热词
//     */
//    fun homeSearchHotkey(homeSearchHotKeyRespLists:List<HomeSearchHotkeyResp>)
//    /**
//     * 常用网站
//     */
//    fun homeOftenNetAddress(homeOftenNetResp:List<HomeOfenNetResp>)
}