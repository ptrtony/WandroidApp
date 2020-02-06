package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.OftenNetView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class OftenNetPresenter @Inject constructor(): BasePresenter<OftenNetView>(){

    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getOftenNetData(){
        if (!checkNetWork()){
            return
        }
        mView.showLoading()
        homeServiceImpl.homeUserNetAddress()
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.hideLoading()
               mView.onOftenNetResult(it)
            },{
                mView.hideLoading()
                it.netException(mView)
            })
    }
}