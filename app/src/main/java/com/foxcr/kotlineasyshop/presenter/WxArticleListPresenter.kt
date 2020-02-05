package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.WxArticleListView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class WxArticleListPresenter @Inject constructor(): BasePresenter<WxArticleListView>(){

    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getWxArticleListData(id:Int, page:Int,isShowLoading:Boolean){
        if (!checkNetWork()){
            return
        }
        if (isShowLoading){
            mView.showLoading()
        }
        homeServiceImpl.getWxArticleListData(id, page)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.hideLoading()
                mView.onWxArticleListResult(it)
            },{
                mView.hideLoading()
                it.netException(mView)
            })
    }

    @SuppressLint("CheckResult")
    fun searchWxArticleListData(id:Int, page:Int, key:String){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.searchWxArticleListData(id, page, key)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onSearchWxArticleListResult(it)
            },{
                it.netException(mView)
            })

    }


    @SuppressLint("CheckResult")
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

    @SuppressLint("CheckResult")
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

    @SuppressLint("CheckResult")
    fun uncollectArticle(id:Int, originId:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.uncollectArticle(id,originId)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onUnCollectSuccessResult(it)
            },{
                it.netException(mView)
            })

    }

}