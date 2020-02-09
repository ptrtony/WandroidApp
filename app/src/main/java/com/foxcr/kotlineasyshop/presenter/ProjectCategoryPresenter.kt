package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.ProjectCategoryView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class ProjectCategoryPresenter @Inject constructor(): BasePresenter<ProjectCategoryView>(){

    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getProjectCategoryTreeData(){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.getProjectCategoryTreeData()
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onProjectCategoryTreeResult(it)
            },{
                it.netException(mView)
            })
    }

    @SuppressLint("CheckResult")
    fun getProjectCategoryListData(page:Int, cid:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.getProjectCategoryListData(page, cid)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onProjectCategoryListResult(it)
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