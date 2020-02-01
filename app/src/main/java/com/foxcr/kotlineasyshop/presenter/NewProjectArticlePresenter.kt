package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.NewProjectArticleView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class NewProjectArticlePresenter @Inject constructor(): BasePresenter<NewProjectArticleView>(){
    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl
    @SuppressLint("CheckResult")
    fun getNewProjectArticleList(page:Int){
        homeServiceImpl.homeArticleProjectList(page)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onNewProjectArticleResult(it)
            },{
                mView.onError(it.message.toString())
            })
    }
}