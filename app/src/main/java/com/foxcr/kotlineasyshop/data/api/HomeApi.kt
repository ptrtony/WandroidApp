package com.foxcr.kotlineasyshop.data.api

import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.data.protocal.BaseResp
import com.foxcr.kotlineasyshop.data.protocal.*
import io.reactivex.Observable
import retrofit2.http.*

interface HomeApi {


    /**
     * 首页banner
     */
    @GET("banner/json")
    fun homeBanner(): Observable<BaseResp<List<HomeBannerResp>>>

    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    fun homeArticleList(@Path("page") page: Int): Observable<BaseResp<HomeArticleResp>>

    /**
     * 置顶文章
     */
    @GET("article/{top}/json")
    fun homeArticleTop(@Path("top") top: String): Observable<BaseResp<HomeTopArticleResp>>

    /**
     * 搜索热词
     */
    @GET("{hotkey}/json")
    fun homeSearchHotkey(@Path("hotkey") hotkey: String): Observable<BaseResp<List<HomeSearchHotkeyResp>>>


    /**
     * 常用网站
     */
    @GET("friend/json")
    fun homeUserNetAddress(): Observable<BaseResp<List<HomeOfenNetResp>>>

    /**
     * 最新项目tab (首页的第二个tab)
     */
    @GET("article/listproject/{page}/json")
    fun homeArticleProjectList(@Path("page") page: Int): Observable<BaseResp<HomeArticleResp>>

    /**
     * 广场列表数据
     */
    @GET("user_article/list/{page}/json")
    fun homeSquareUserArticleList(@Path("page") page: Int): Observable<BaseResp<HomeSquareUserArticleListResp>>

    /**
     * 收藏文章列表
     */
    @GET("lg/collect/list/{page}/json")
    fun collectArticleList(@Path("page") page: Int): Observable<BaseResp<CollectArticleListResp>>

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{id}/json")
    fun collectInAddressArticle(@Path("id") id: Int): Observable<BaseResp<BaseNoneResponseResult>>

    /**
     * 收藏站外文章
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    fun collectOutAddressArticle(@Field("title")title:String
                                 ,@Field("author")author:String
                                 ,@Field("link")link:String):Observable<BaseResp<BaseNoneResponseResult>>

    /**
     * 取消收藏文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun uncollectArticleList(@Path("id")id:Int):Observable<BaseResp<BaseNoneResponseResult>>

    /**
     * 我的收藏页面（该页面包含自己录入的内容）
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun userUncollectArticle(@Path("id")id:Int,@Field("originId")originId:Int):Observable<BaseResp<BaseNoneResponseResult>>

    /**
     * 收藏网站列表
     */
    @GET("lg/collect/usertools/json")
    fun collectUserTools():Observable<BaseResp<BaseNoneResponseResult>>

    /**
     * 收藏网址
     */
    @POST("lg/collect/addtool/json")
    fun collectAddTools():Observable<BaseResp<BaseNoneResponseResult>>

    /**
     * 编辑收藏网站
     */
    @POST("lg/collect/updatetool/json")
    @FormUrlEncoded
    fun collectUpdateTool(@Field("id")id:Int,@Field("name")name:String,@Field("link")link:String):Observable<BaseResp<BaseNoneResponseResult>>

    /**
     * 删除收藏网站
     */
    @POST("lg/collect/deletetool/json")
    @FormUrlEncoded
    fun collectDeleteTool(@Field("id")id:Int):Observable<BaseResp<BaseNoneResponseResult>>

    /**
     * 导航数据
     */
    @GET("navi/json")
    fun getNavigationData():Observable<BaseResp<List<HomeNavigationResp>>>

    /**
     * 问答列表
     */
    @GET("wenda/list/{pageId}/json ")
    fun getRequestAnswerListData(@Path("pageId")page:Int):Observable<BaseResp<HomeRequestAnswerListResp>>

    /**
     * 体系数据
     */
    @GET("tree/json")
    fun getKnowledgeSystemData():Observable<BaseResp<List<HomeKnowledgeSystemResp>>>

    /**
     * 知识体系列表
     */
    @GET("article/list/{page}/json")
    fun getArticleKnowledgeSystemData(@Path("page")page:Int,@Query("cid")cid:Int):Observable<BaseResp<HomeKnowledgeSystemListResp>>

    /**
     * 公众号列表
     */
    @GET("wxarticle/chapters/json")
    fun getWxArticleChaptersData() : Observable<BaseResp<List<WxArticleChaptersResp>>>

    /**
     * 查看某个公众号历史数据
     */
    @GET("wxarticle/list/{ID}/{page}/json")
    fun getWxArticleListData(@Path("ID")ID:Int,@Path("page")page:Int):Observable<BaseResp<WxArticleListResp>>

    /**
     * 在某个公众号中搜索历史文章
     */
    @GET("wxarticle/list/{ID}/{page}/json")
    fun searchWxArticleListData(@Path("ID")ID:Int,@Path("page")page:Int,@Query("k")k:String):Observable<BaseResp<WxArticleListResp>>

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    fun getProjectCategoryTreeData():Observable<BaseResp<List<ProjectCategoryTreeResp>>>

    /**
     * 项目分类列表
     */
    @GET("project/list/{page}/json")
    fun getProjectCategoryListData(@Path("page")page:Int,@Query("cid")cid:Int):Observable<BaseResp<ProjectCategoryListResp>>

    /**
     * 搜索
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    fun getArticlQueryData(@Path("page")page:Int,@Field("k")k:String):Observable<BaseResp<SearchArticleResp>>

    /**
     * 个人积分
     */
    @GET("lg/coin/userinfo/json")
    fun getUserInfoCoin():Observable<BaseResp<UserInfoCoinResp>>

    /**
     * 积分排行榜接口
     */
    @GET("coin/rank/{page}/json")
    fun getCoinRankData(@Path("page")page:Int):Observable<BaseResp<CoinRankListResp>>

    /**
     * 获取个人积分获取列表，需要登录后访问
     */
    @GET("lg/coin/list/{page}/json")
    fun getLgCoinListData(@Path("page")page:Int):Observable<BaseResp<LgCoinListResp>>

    /**
     * 退出登录
     */
    @GET("user/logout/json")
    fun getUserLoginout():Observable<BaseResp<BaseNoneResponseResult>>
}