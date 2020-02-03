package com.foxcr.kotlineasyshop.service.impl

import com.foxcr.base.data.protocal.BaseNoneResponseResult
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

    override fun homeArticleProjectList(page: Int): Observable<HomeArticleProjectListResp> {
        return homeRepository.homeArticleProjectList(page)
    }

    override fun homeSquareUserArticleList(page: Int): Observable<HomeSquareUserArticleListResp> {
        return homeRepository.homeSquareUserArticleProjectList(page)
    }

    override fun collectInStackArticle(id: Int): Observable<BaseNoneResponseResult> {
        return homeRepository.collectInStandArticle(id)
    }

    override fun collectOutStackArticle(
        title: String,
        author: String,
        link: String
    ): Observable<BaseNoneResponseResult> {
        return homeRepository.collectOutStandArticle(title, author, link)
    }

    override fun uncollectArticle(id: Int, originId: Int): Observable<BaseNoneResponseResult> {
        return homeRepository.uncollectArticle(id, originId)
    }

    override fun getNavigationData(): Observable<List<HomeNavigationResp>> {
        return homeRepository.getNavigationData()
    }

    override fun getQuestAnswerData(page: Int): Observable<HomeRequestAnswerListResp> {
        return homeRepository.getQuestAnswerListData(page)
    }

    override fun getKnowledgeSystemData(): Observable<List<HomeKnowledgeSystemResp>> {
        return homeRepository.getKnowledgeSystemData()
    }

    override fun getKnowledgeSystemListData(
        page: Int,
        cid: Int
    ): Observable<HomeKnowledgeSystemListResp> {
        return homeRepository.getKnowledgeSystemListData(page, cid)
    }


}