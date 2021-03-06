package com.foxcr.user.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.user.presenter.view.RegisterView
import com.foxcr.user.service.impl.UserServiceImpl
import javax.inject.Inject

class RegisterPresenter @Inject constructor() : BasePresenter<RegisterView>() {

    @Inject
    lateinit var userRegister: UserServiceImpl

    @SuppressLint("CheckResult")
    fun register(username: String, password: String, repassword: String) {

        if (!checkNetWork()) {
            return
        }

        /*
            业务逻辑
         */
        mView.showLoading()
        userRegister.register(username, password, repassword)
            .ioToUI()
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribe({
                mView.hideLoading()
                mView.onRegisterResult(it)
            }, {
                mView.hideLoading()
                it.netException(mView)
            })
    }
}