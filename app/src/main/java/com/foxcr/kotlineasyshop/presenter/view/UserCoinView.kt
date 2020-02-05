package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.LgCoinListResp

interface UserCoinView : BaseView {
    fun onUserCoinListResult(lgCoinListResp: LgCoinListResp)
}