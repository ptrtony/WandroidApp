package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.HomeView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import kotlinx.coroutines.*
import javax.inject.Inject


@SuppressLint("CheckResult")
class HomePresenter @Inject constructor(): BasePresenter<HomeView>() {

    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    /**
     * 首页banner
     */
    fun homeBanner(){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.homeBanner()
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.homeBanner(it)
            },{
                mView.onError(it.message.toString())
            })
    }
//    /**
//     * 首页文章列表
//     */
//    fun homeArticleList(page:Int){
//        homeServiceImpl.homeArticleList(page)
//            .ioToUI()
//            .subscribe {
//                mView.homeArticleList(it)
//            }
//    }
//
//    fun homeArticleProjectList(page:Int){
//        homeServiceImpl.homeArticleProjectList(page)
//            .ioToUI()
//            .subscribe {
//                mView.homeArticleProjectList(it)
//            }
//    }
//    /**
//     * 置顶文章
//     */
//    fun homeTopArticle(top:String){
//        homeServiceImpl.homeTopArticle(top)
//            .ioToUI()
//            .subscribe {
//                mView.homeTopArticle(it)
//            }
//    }
//    /**
//     * 搜索热词
//     */
//    fun homeSearchHotKey(hotkey:String){
//        homeServiceImpl.homeSearchHotkey(hotkey)
//            .ioToUI()
//            .subscribe {
//                mView.homeSearchHotkey(it)
//            }
//    }
//
//    /**
//     * 常用网站
//     */
//    fun homeOftenNetAddress(){
//        homeServiceImpl.homeUserNetAddress()
//            .ioToUI()
//            .subscribe {
//                mView.homeOftenNetAddress(it)
//            }
//    }
}