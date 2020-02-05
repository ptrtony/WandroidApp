package com.foxcr.kotlineasyshop.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.LgCoinListResp

class UserCoinAdapter constructor(data:List<LgCoinListResp.DatasBean>): BaseQuickAdapter<LgCoinListResp.DatasBean, UserCoinAdapter.UserCoinViewHolder>(R.layout.item_user_coin,data){
    class UserCoinViewHolder constructor(view:View): BaseViewHolder(view){
        private val mCoinTv = view.findViewById<TextView>(R.id.mCoinTv)
        fun dataBinding(item: LgCoinListResp.DatasBean){
            mCoinTv.text = item.desc
        }
    }

    override fun convert(helper: UserCoinViewHolder, item: LgCoinListResp.DatasBean) {
        helper.dataBinding(item)
    }


}