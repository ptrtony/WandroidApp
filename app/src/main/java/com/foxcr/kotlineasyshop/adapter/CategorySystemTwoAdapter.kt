package com.foxcr.kotlineasyshop.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeKnowledgeSystemResp

class CategorySystemTwoAdapter constructor(data: List<HomeKnowledgeSystemResp.ChildrenBean>) :
    BaseQuickAdapter<HomeKnowledgeSystemResp.ChildrenBean, CategorySystemTwoAdapter.CategorySystemOneViewHolder>(
        R.layout.item_category_system, data
    ) {
    class CategorySystemOneViewHolder constructor(view: View) : BaseViewHolder(view) {
        private val mCategoryTv = view.findViewById<TextView>(R.id.mCategoryTv)

        fun dataBinding(item: HomeKnowledgeSystemResp.ChildrenBean) {
            if (item.userControlSetTop) {
                mCategoryTv.setTextColor(mCategoryTv.context.resources.getColor(R.color.common_white))
            } else {
                mCategoryTv.setTextColor(mCategoryTv.context.resources.getColor(R.color.common_blue))
            }
            mCategoryTv.isSelected = item.userControlSetTop
            mCategoryTv.text = item.name
        }
    }

    override fun convert(
        helper: CategorySystemOneViewHolder,
        item: HomeKnowledgeSystemResp.ChildrenBean
    ) {
        helper.dataBinding(item)
        helper.itemView.setOnClickListener {
            onCategoryTwoClickListener?.onCategoryTwoClick(it,helper.layoutPosition,item)
        }
    }

    interface OnCategoryTwoSystemClickListener {
        fun onCategoryTwoClick(
            view: View,
            position: Int,
            item: HomeKnowledgeSystemResp.ChildrenBean
        )
    }

    private var onCategoryTwoClickListener: OnCategoryTwoSystemClickListener? = null
    fun setOnCategoryTwoSystemClickListener(onCategoryTwoClickListener: OnCategoryTwoSystemClickListener) {
        this.onCategoryTwoClickListener = onCategoryTwoClickListener
    }
}