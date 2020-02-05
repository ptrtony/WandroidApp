package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.CoinRankView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class CoinRankPresenter @Inject constructor(): BasePresenter<CoinRankView>(){

    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getCoinRankListData(page:Int){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.getCoinRankData(page)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onCoinBankResult(it)
            },{
                it.netException(mView)
            })
    }
}