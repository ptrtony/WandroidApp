package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.SquareView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class SquarePresenter @Inject constructor():BasePresenter<SquareView>(){
    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getSquareArticleUserList(page:Int){
        homeServiceImpl.homeSquareUserArticleList(page)
            .ioToUI()
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribe {
                mView.onHomeSquareUserArticleList(it)
            }
    }
}