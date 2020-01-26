package com.foxcr.kotlineasyshop.service.impl

import com.foxcr.kotlineasyshop.data.protocal.*
import com.foxcr.kotlineasyshop.data.repository.HomeRepository
import com.foxcr.kotlineasyshop.service.HomeService
import io.reactivex.Observable
import javax.inject.Inject

class HomeServiceImpl @Inject constructor(): HomeService {

    @Inject
    lateinit var homeRepository:HomeRepository


    override fun homeBanner(): Observable<List<HomeBannerResp>> {
        return homeRepository.homeBanner()
    }

    override fun homeArticleList(curPage: Int): Observable<HomeArticleListResp> {
        return homeRepository.homeArticleList(curPage)
    }

    override fun homeTopArticle(top: String): Observable<HomeTopArticleResp> {
        return homeRepository.homeTopArticle(top)
    }

    override fun homeSearchHotkey(hotkey:String): Observable<List<HomeSearchHotkeyResp>> {
        return homeRepository.searchHotkey(hotkey)
    }

    override fun homeUserNetAddress(): Observable<List<HomeOfenNetResp>> {
        return homeRepository.ofenNetAddress()
    }
}