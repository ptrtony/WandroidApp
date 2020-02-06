package com.foxcr.kotlineasyshop.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeOfenNetResp

class OftenNetTagAdapter constructor(var context: Context,var datas:MutableList<HomeOfenNetResp>): BaseQuickAdapter<HomeOfenNetResp, OftenNetTagAdapter.OftenNetViewHolder>(R.layout.item_often_net_title,datas) {
    class OftenNetViewHolder constructor(view:View):BaseViewHolder(view){
        private var mTitleTv = view.findViewById<TextView>(R.id.mColorTextView)
        fun dataBinding(item: HomeOfenNetResp){
            mTitleTv.text = item.name
        }
    }

    override fun convert(helper: OftenNetViewHolder, item: HomeOfenNetResp) {
        helper.dataBinding(item)
    }
}