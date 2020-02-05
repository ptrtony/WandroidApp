package com.foxcr.kotlineasyshop.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.CoinRankListResp

class CoinRankAdapter constructor(data:List<CoinRankListResp.DatasBean>) : BaseQuickAdapter<CoinRankListResp.DatasBean, CoinRankAdapter.CoinRankViewHolder>(
    R.layout.item_coin_rank,data) {
    class CoinRankViewHolder constructor(view:View): BaseViewHolder(view){
        private val mCoinTv = view.findViewById<TextView>(R.id.mCoinTv)
        private val mRankTv = view.findViewById<TextView>(R.id.mRankTv)

        @SuppressLint("SetTextI18n")
        fun dataBinding(item: CoinRankListResp.DatasBean){
            val coinSb = StringBuilder()
                .append(item.rank)
                .append(".")
                .append(item.username)
                .append(", 积分:")
                .append(item.coinCount)
            mCoinTv.text = coinSb.toString()
            mRankTv.text = "Lv${item.level}"

        }
    }

    override fun convert(helper: CoinRankViewHolder, item: CoinRankListResp.DatasBean) {
        helper.dataBinding(item)
    }
}