package com.foxcr.user.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.ext.netException
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.user.presenter.view.LoginView
import com.foxcr.user.service.impl.UserServiceImpl
import javax.inject.Inject

class LoginPresenter @Inject constructor():BasePresenter<LoginView>(){

    @Inject
    lateinit var userService: UserServiceImpl

    @SuppressLint("CheckResult")
    fun login(username:String, password:String){
        if (!checkNetWork()){
            return
        }

        mView.showLoading()
        /*
            登录
         */
        userService.login(username, password)
            .compose(lifecycleProvider.bindToLifecycle())
            .ioToUI()
            .subscribe({
                if (null != it){
                    mView.hideLoading()
                    mView.onLoginResult(it)
                }
            },{
                mView.hideLoading()
                it.netException(mView)
            })


    }
}