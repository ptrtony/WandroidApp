package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.CollectView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class CollectPresenter @Inject constructor(): BasePresenter<CollectView>(){

    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getCollectListData(page:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.collectListArticle(page)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onCollectListResult(it)
            },{
                it.netException(mView)
            })
    }

    @SuppressLint("CheckResult")
    fun getUncollectArticle(id:Int, originId:Int){
        if (!checkNetWork()){
            return
        }

        homeServiceImpl.uncollectArticle(id, originId)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onUncollectResult(it)
            },{
                it.netException(mView)
            })
    }
}