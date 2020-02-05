package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.UserCoinView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class UserCoinPresenter @Inject constructor() : BasePresenter<UserCoinView>(){
    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getUserCoinListData(page:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.getUserInfoCoinListData(page)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onUserCoinListResult(it)
            },{
                it.netException(mView)
            })
    }
}