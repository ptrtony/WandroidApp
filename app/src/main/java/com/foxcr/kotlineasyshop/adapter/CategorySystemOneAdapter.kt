package com.foxcr.kotlineasyshop.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeKnowledgeSystemResp

class CategorySystemOneAdapter constructor(data:List<HomeKnowledgeSystemResp>) : BaseQuickAdapter<HomeKnowledgeSystemResp,CategorySystemOneAdapter.CategorySystemOneViewHolder>(
    R.layout.item_category_system,data){
    class CategorySystemOneViewHolder constructor(view:View): BaseViewHolder(view){
        private val mCategoryTv = view.findViewById<TextView>(R.id.mCategoryTv)

        fun dataBinding(item: HomeKnowledgeSystemResp){
            if (item.userControlSetTop){
                mCategoryTv.setTextColor(itemView.context.resources.getColor(R.color.common_white))
            }else{
                mCategoryTv.setTextColor(itemView.context.resources.getColor(R.color.common_blue))
            }
            mCategoryTv.isSelected = item.userControlSetTop
            mCategoryTv.text = item.name
        }
    }

    override fun convert(helper: CategorySystemOneViewHolder, item: HomeKnowledgeSystemResp) {
        helper.dataBinding(item)
        helper.itemView.setOnClickListener {
            onOneClickListener?.onCategorySystemOneClick(it,helper.layoutPosition,item)
        }

    }

    interface OnCategorySystemOneClickListener{
        fun onCategorySystemOneClick(view:View,position:Int,item: HomeKnowledgeSystemResp)
    }

    private var onOneClickListener:OnCategorySystemOneClickListener?=null

    fun setOnCategorySystemOneClickListener(onOneClickListener:OnCategorySystemOneClickListener){
        this.onOneClickListener = onOneClickListener
    }
}