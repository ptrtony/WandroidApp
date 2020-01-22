package com.foxcr.user.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.user.data.protocal.RegisterResp

interface RegisterView :BaseView{
    fun onRegisterResult(registerResp:RegisterResp)
    fun onErrorMsg(errorMsg:String?)
}