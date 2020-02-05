package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.MainView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor() : BasePresenter<MainView>(){

    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getUserInfoCoinData(){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.getUserInfoCoinData()
            .delay(3,TimeUnit.SECONDS)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onUserInfoCoinResult(it)
            },{
                it.netException(mView)
            })
    }
}