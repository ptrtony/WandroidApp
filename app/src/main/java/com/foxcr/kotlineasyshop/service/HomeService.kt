package com.foxcr.kotlineasyshop.service

import com.foxcr.kotlineasyshop.data.protocal.*
import io.reactivex.Observable

interface HomeService {
    /**
     * 首页banner
     */
    fun homeBanner():Observable<List<HomeBannerResp>>

    /**
     * 首页文章列表
     */
    fun homeArticleList(curPage:Int):Observable<HomeArticleListResp>

    /**
     * 置顶文章
     */
    fun homeTopArticle(top:String):Observable<HomeTopArticleResp>

    /**
     * 搜索热词
     */
    fun homeSearchHotkey(hotkey:String):Observable<List<HomeSearchHotkeyResp>>

    /**
     * 常用网站
     */
    fun homeUserNetAddress():Observable<List<HomeOfenNetResp>>

}