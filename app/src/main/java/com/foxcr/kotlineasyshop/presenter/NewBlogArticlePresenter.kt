package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.NewBlogArticleView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class NewBlogArticlePresenter @Inject constructor(): BasePresenter<NewBlogArticleView>() {
    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getNewBlogArticleList(page:Int){
        homeServiceImpl.homeArticleList(page)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onNewBlogArticleListResult(it)
            },{
                mView.onError(it.message.toString())
            })
    }
}