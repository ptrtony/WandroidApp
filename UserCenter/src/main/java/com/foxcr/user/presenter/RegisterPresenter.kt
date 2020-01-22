package com.foxcr.user.presenter

import android.annotation.SuppressLint
import com.foxcr.base.ext.ioToUI
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.user.presenter.view.RegisterView
import com.foxcr.user.service.impl.UserServiceImpl
import javax.inject.Inject

class RegisterPresenter @Inject constructor(): BasePresenter<RegisterView>() {
    @Inject
    lateinit var userRegister:UserServiceImpl
    @SuppressLint("CheckResult")
    fun register(username:String, password:String, repassword:String){
        /*
            业务逻辑
         */
        mView.showLoading()
        userRegister.register(username,password,repassword)
            .ioToUI()
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribe({
                mView.hideLoading()
                if (it.errorCode == 0){
                    mView.onRegisterResult(it.data)
                }else{
                    mView.onErrorMsg(it.errorMsg)
                }
            },{
                mView.hideLoading()
                mView.onErrorMsg(it.message.toString())
            })
    }
}