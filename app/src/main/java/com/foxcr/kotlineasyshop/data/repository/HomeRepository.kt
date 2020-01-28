package com.foxcr.kotlineasyshop.data.repository

import com.foxcr.base.data.net.RetrofitFactory
import com.foxcr.base.ext.convert
import com.foxcr.kotlineasyshop.data.api.HomeApi
import com.foxcr.kotlineasyshop.data.protocal.*
import io.reactivex.Observable
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

}