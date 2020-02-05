package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.CoinRankListResp

interface CoinRankView : BaseView{
    fun onCoinBankResult(coinRankListResp: CoinRankListResp)
}