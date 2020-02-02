package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.NavigationView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class NavigationPresenter @Inject constructor():BasePresenter<NavigationView>(){
    @Inject
    lateinit var homeServiceImpl:HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getNavigationData(){
        mView.showLoading()
        homeServiceImpl.getNavigationData()
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onNavigationResult(it)
                mView.hideLoading()
            },{
                it.netException(mView)
                mView.hideLoading()
            })
    }
}