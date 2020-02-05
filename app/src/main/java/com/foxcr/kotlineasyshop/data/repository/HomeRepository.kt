package com.foxcr.kotlineasyshop.data.repository

import com.foxcr.base.data.net.RetrofitFactory
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.ext.convert
import com.foxcr.kotlineasyshop.data.api.HomeApi
import com.foxcr.kotlineasyshop.data.protocal.*
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class HomeRepository @Inject constructor(){
    /**
     * 首页banner
     */
    fun homeBanner():Observable<List<HomeBannerResp>>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .homeBanner()
            .convert()
    }

    /**
     * 首页文章列表
     */
    fun homeArticleList(page:Int):Observable<HomeArticleListResp>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .homeArticleList(page)
            .convert()
    }

    /**
     * 置顶文章
     */
    fun homeTopArticle(top:String):Observable<HomeTopArticleResp>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .homeArticleTop(top)
            .convert()
    }

    /**
     * 搜索热词
     */
    fun searchHotkey(hotkey:String):Observable<List<HomeSearchHotkeyResp>>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .homeSearchHotkey(hotkey)
            .convert()
    }

    /**
     * 常用网站
     */
    fun ofenNetAddress():Observable<List<HomeOfenNetResp>>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .homeUserNetAddress()
            .convert()
    }

    /**
     * 最新项目tab (首页的第二个tab)
     */
    fun homeArticleProjectList(page:Int):Observable<HomeArticleProjectListResp>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .homeArticleProjectList(page)
            .convert()
    }

    /**
     * 广场列表数据
     */
    fun homeSquareUserArticleProjectList(page: Int):Observable<HomeSquareUserArticleListResp>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .homeSquareUserArticleList(page)
            .convert()
    }

    /**
     * 收藏站内文章
     */
    fun collectInStandArticle(id:Int):Observable<BaseNoneResponseResult>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .collectInAddressArticle(id)
            .convert()
    }

    /**
     * 收藏站外文章
     */
    fun collectOutStandArticle(title:String,author:String,link:String):Observable<BaseNoneResponseResult>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .collectOutAddressArticle(title, author, link)
            .convert()
    }

    /**
     * 取消收藏的文章
     */
    fun uncollectArticle(id:Int,originId:Int):Observable<BaseNoneResponseResult>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .userUncollectArticle(id,originId)
            .convert()
    }

    /**
     * 导航数据
     */
    fun getNavigationData():Observable<List<HomeNavigationResp>>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .getNavigationData()
            .convert()
    }

    /**
     * 问答列表
     */
    fun getQuestAnswerListData(page:Int):Observable<HomeRequestAnswerListResp>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .getRequestAnswerListData(page)
            .convert()
    }

    /**
     * 体系
     */
    fun getKnowledgeSystemData():Observable<List<HomeKnowledgeSystemResp>>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .getKnowledgeSystemData()
            .convert()
    }

    /**
     * 知识体系列表
     */
    fun getKnowledgeSystemListData(page:Int,cid:Int):Observable<HomeKnowledgeSystemListResp>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .getArticleKnowledgeSystemData(page, cid)
            .convert()
    }

    /**
     * 获取公众号列表
     */
    fun getWxArticleChaptersData():Observable<List<WxArticleChaptersResp>>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .getWxArticleChaptersData()
            .convert()
    }

    /**
     * 查看某个公众号历史数据
     */

    fun getWxArticleList(id:Int,page:Int):Observable<WxArticleListResp>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .getWxArticleListData(id,page)
            .convert()
    }

    /**
     * 在某个公众号中搜索历史文章
     */
    fun searchWxArticleListData(id:Int,page:Int,key:String):Observable<WxArticleListResp>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .searchWxArticleListData(id,page,key)
            .convert()
    }

    /**
     * 项目分类
     */

    fun getProjectCategoryTreeData():Observable<List<ProjectCategoryTreeResp>>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .getProjectCategoryTreeData()
            .convert()
    }

    /**
     *  项目列表数据
     */
    fun getProjectCategoryListData(id:Int,page:Int):Observable<ProjectCategoryListResp>{
        return RetrofitFactory.instance.create(HomeApi::class.java)
            .getProjectCategoryListData(page,id)
            .convert()
    }


}