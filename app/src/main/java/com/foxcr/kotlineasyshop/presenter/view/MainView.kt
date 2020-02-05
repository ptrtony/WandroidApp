package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.UserInfoCoinResp

interface MainView : BaseView{
    fun onUserInfoCoinResult(userInfoCoin: UserInfoCoinResp)
}