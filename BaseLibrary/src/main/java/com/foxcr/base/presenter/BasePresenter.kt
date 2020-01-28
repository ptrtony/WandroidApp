package com.foxcr.base.presenter

import android.content.Context
import com.cxz.wanandroid.utils.NetWorkUtils
import com.foxcr.base.presenter.view.BaseView
import com.trello.rxlifecycle3.LifecycleProvider
import javax.inject.Inject

open class BasePresenter<T : BaseView> {
    lateinit var mView: T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>

    @Inject
    lateinit var context: Context

    fun checkNetWork(): Boolean {
        if (NetWorkUtils.isNetworkAvailable(context)) {
            return true
        }
        mView.onError("网络不可用")
        return false
    }
}