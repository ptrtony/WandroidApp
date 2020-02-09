package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.KnowledgeSystemView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class KnowledgeSystemPresenter @Inject constructor(): BasePresenter<KnowledgeSystemView>() {

    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl
    @SuppressLint("CheckResult")
    fun getKnowledgeSystemData(){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.getKnowledgeSystemData()
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onKnowledgeSystemResult(it)
                getKnowledgeSystemListData(0,it[0].children[0].id)
            },{
                it.netException(mView)
            })
    }

    @SuppressLint("CheckResult")
    fun getKnowledgeSystemListData(page:Int, cid:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.getKnowledgeSystemListData(page, cid)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onKnowledgeSystemListResult(it)
            },{
                it.netException(mView)
            })
    }

    @SuppressLint("CheckResult")
    fun collectInStandArticle(id:Int){

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