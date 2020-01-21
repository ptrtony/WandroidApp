package com.foxcr.user.presenter

import android.annotation.SuppressLint
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.rx.AndroidSchedulers
import com.foxcr.base.rx.BaseException
import com.foxcr.user.presenter.view.RegisterView
import com.foxcr.user.service.impl.UserServiceImpl
import io.reactivex.schedulers.Schedulers

class RegisterPresenter : BasePresenter<RegisterView>() {
    @SuppressLint("CheckResult")
    fun register(username:String, password:String, repassword:String){
        /*
            业务逻辑
         */
        mView.showLoading()
        val userRegister = UserServiceImpl()
        userRegister.register(username,password,repassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mView.hideLoading()
                if (null!=it){
                    mView.onRegisterResult(it)
                }
            },{
                mView.hideLoading()
                if (it is BaseException){
                    mView.onErrorMsg(it.errorMsg)
                }else {
                    mView.onErrorMsg(it.message.toString())
                }
            })

    }
}