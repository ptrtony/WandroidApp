package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.HomeView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
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
                it.netException(mView)
            })
    }
    /**
     * 首页文章列表
     */
    fun homeArticleList(page:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.homeArticleList(page)
            .ioToUI()
            .subscribe ({
                mView.homeArticleList(it)
            },{
                it.netException(mView)
            })
    }

    fun homeArticleProjectList(page:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.homeArticleProjectList(page)
            .ioToUI()
            .subscribe ({
                mView.homeArticleProjectList(it)
            },{
                it.netException(mView)
            })
    }


    fun collectInStandArticle(id:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.collectInStackArticle(id)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onCollectSuccessResult(it)
            },{
                it.netException(mView)
            })
    }


    fun collectOutStandArticle(title:String, author:String, link:String){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.collectOutStackArticle(title, author, link)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onCollectSuccessResult(it)
            },{
                it.netException(mView)
            })

    }

    fun uncollectArticle(id:Int, originId:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.uncollectArticle(id)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onUnCollectSuccessResult(it)
            },{
                it.netException(mView)
            })

    }


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