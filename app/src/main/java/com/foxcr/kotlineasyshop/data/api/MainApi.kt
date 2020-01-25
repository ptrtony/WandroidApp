package com.foxcr.kotlineasyshop.data.api

import com.foxcr.base.data.protocal.BaseResp
import com.foxcr.kotlineasyshop.data.protocal.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApi {


    /**
     * 首页banner
     */
    @GET("banner/json")
    fun homeBanner():Observable<BaseResp<List<HomeBannerResp>>>

    /**
     * 首页文章列表
     */
    @GET("article/list/{curPage}/json")
    fun homeArticleList(@Path("curPage")page:Int):Observable<BaseResp<HomeArticleListResp>>

    /**
     * 置顶文章
     */
    @GET("article/{top}/json")
    fun homeArticleTop(@Path("top")top:String):Observable<BaseResp<HomeTopArticleResp>>

    /**
     * 搜索热词
     */
    @GET("{hotkey}/json")
    fun homeSearchHotkey(@Path("hotkey")hotkey:String):Observable<BaseResp<List<HomeSearchHotkeyResp>>>


    /**
     * 常用网站
     */
    @GET("friend/json")
    fun homeUserNetAddress():Observable<BaseResp<List<HomeOfenNetResp>>>


}