package com.foxcr.kotlineasyshop.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.WxArticleChaptersView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class WxArticleChapterPresenter @Inject constructor(): BasePresenter<WxArticleChaptersView>(){
    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl

    @SuppressLint("CheckResult")
    fun getWxArticleChaptersData(){
        if (!checkNetWork()){
            return
        }
        homeServiceImpl.getWxArticleChaptersData()
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                mView.onWxArticleChaptersResult(it)
            },{
                it.netException(mView)
            })
    }

}