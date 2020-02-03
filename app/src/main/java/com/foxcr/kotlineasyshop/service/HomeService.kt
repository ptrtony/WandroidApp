package com.foxcr.kotlineasyshop.service

import com.foxcr.base.data.protocal.BaseNoneResponseResult
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

    /**
     * 最新项目tab (首页的第二个tab)
     */
    fun homeArticleProjectList(page:Int):Observable<HomeArticleProjectListResp>

    /**
     * 广场列表数据
     */
    fun homeSquareUserArticleList(page: Int):Observable<HomeSquareUserArticleListResp>

    /**
     * 收藏站内文章
     */
    fun collectInStackArticle(id:Int):Observable<BaseNoneResponseResult>

    /**
     * 收藏站外文章
     */
    fun collectOutStackArticle(title:String,author:String,link:String):Observable<BaseNoneResponseResult>

    /**
     * 我的收藏页面（该页面包含自己录入的内容）
     */
    fun uncollectArticle(id:Int,originId:Int):Observable<BaseNoneResponseResult>

    /**
     * 导航数据
     */
    fun getNavigationData():Observable<List<HomeNavigationResp>>

    /**
     * 问答数据
     */
    fun getQuestAnswerData(page:Int):Observable<HomeRequestAnswerListResp>

    /**
     * 知识体系 二级
     */
    fun getKnowledgeSystemData():Observable<List<HomeKnowledgeSystemResp>>

    /**
     * 知识体系
     */
    fun getKnowledgeSystemListData(page:Int,cid:Int):Observable<HomeKnowledgeSystemListResp>
}