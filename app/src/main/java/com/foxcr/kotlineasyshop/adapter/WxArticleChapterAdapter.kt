package com.foxcr.kotlineasyshop.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.WxArticleChaptersResp

class WxArticleChapterAdapter constructor(datas:List<WxArticleChaptersResp>) : BaseQuickAdapter<WxArticleChaptersResp, WxArticleChapterAdapter.WxArticleChapterViewHolder>(
    R.layout.item_navigation_list_one,datas){

//    companion object{
//        const val UPDATE_STATE = 101
//        const val UPDATE_NAME = 102
//    }
    class WxArticleChapterViewHolder constructor(view:View):BaseViewHolder(view){
        val mTitleTv : TextView = view.findViewById(R.id.mNavigationListOneTv)
    }

    override fun convert(helper: WxArticleChapterViewHolder, item: WxArticleChaptersResp) {
        helper.mTitleTv.text = item.name
        if (item.isSuccess){
            helper.mTitleTv.setTextColor(Color.parseColor("#03a9f4"))
        }else{
            helper.mTitleTv.setTextColor(Color.parseColor("#333333"))
        }

        helper.itemView.setOnClickListener {
            onListOneClickListener?.onWxArticleChapterClick(it,helper.layoutPosition,item)
        }
    }

//    override fun onBindViewHolder(
//        holder: WxArticleChapterViewHolder,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//        if (payloads.isEmpty()){
//            onBindViewHolder(holder,position)
//        }else if (payloads[0] is Int){
//            when(payloads[0] as Int){
//                UPDATE_STATE ->{
//                    if (mData[position].isSuccess){
//                        holder.mTitleTv.setTextColor(Color.parseColor("#03a9f4"))
//                    }else{
//                        holder.mTitleTv.setTextColor(Color.parseColor("#333333"))
//                    }
//                }
//                UPDATE_NAME -> holder.mTitleTv.text = mData[position].name
//            }
//        }
//
//    }

    interface OnWxArticleChapterClickListener{
        fun onWxArticleChapterClick(view:View,position:Int, wxArticleChaptersResp: WxArticleChaptersResp)
    }

    private var onListOneClickListener : OnWxArticleChapterClickListener?=null

    fun setWxArticleClickListener(onListOneClickListener : OnWxArticleChapterClickListener){
        this.onListOneClickListener = onListOneClickListener
    }




}