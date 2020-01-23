package com.foxcr.user.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.user.data.protocal.LoginResp
import com.foxcr.user.data.protocal.RegisterResp

interface LoginView :BaseView{
    fun onLoginResult(loginResp: LoginResp)
    fun onErrorMsg(errorMsg:String?)
}