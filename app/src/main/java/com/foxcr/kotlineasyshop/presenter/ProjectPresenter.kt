package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.ProjectView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class ProjectPresenter @Inject constructor(): BasePresenter<ProjectView>(){
    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getNewProjectArticleList(page:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.homeArticleProjectList(page)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onNewProjectArticleResult(it)
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
        homeServiceImpl.uncollectArticle(id)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onUnCollectSuccessResult(it)
            },{
                it.netException(mView)
            })

    }
}