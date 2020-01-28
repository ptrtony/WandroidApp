package com.foxcr.kotlineasyshop.data.api

import com.foxcr.base.data.protocal.BaseResp
import com.foxcr.kotlineasyshop.data.protocal.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {


    /**
     * 首页banner
     */
    @GET("banner/json")
    fun homeBanner():Observable<BaseResp<List<HomeBannerResp>>>

    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    fun homeArticleList(@Path("page")page:Int):Observable<BaseResp<HomeArticleListResp>>

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

    /**
     * 最新项目tab (首页的第二个tab)
     */
    @GET("article/listproject/{page}/json")
    fun homeArticleProjectList(@Path("page")page:Int):Observable<BaseResp<HomeArticleProjectListResp>>

    /**
     * 广场列表数据
     */
    @GET("user_article/list/{page}/json")
    fun homeSquareUserArticleList(@Path("page")page:Int):Observable<BaseResp<HomeSquareUserArticleListResp>>

}