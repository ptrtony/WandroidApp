package com.foxcr.kotlineasyshop.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.ProjectCategoryTreeResp

class ProjectCategoryOneAdapter constructor(data: List<ProjectCategoryTreeResp>) :
    BaseQuickAdapter<ProjectCategoryTreeResp, ProjectCategoryOneAdapter.ProjectCategoryOneViewHolder>(
        R.layout.item_category_system, data
    ) {
    class ProjectCategoryOneViewHolder constructor(view: View) : BaseViewHolder(view) {
        private val mCategoryTv = view.findViewById<TextView>(R.id.mCategoryTv)

        fun dataBinding(item: ProjectCategoryTreeResp) {
            if (item.isSelect) {
                mCategoryTv.setTextColor(mCategoryTv.context.resources.getColor(R.color.common_white))
                mCategoryTv
            } else {
                mCategoryTv.setTextColor(mCategoryTv.context.resources.getColor(R.color.common_blue))
            }
            mCategoryTv.isSelected = item.isSelect
            mCategoryTv.text = item.name
        }
    }

    override fun convert(
        helper: ProjectCategoryOneViewHolder,
        item: ProjectCategoryTreeResp
    ) {
        helper.dataBinding(item)
        helper.itemView.setOnClickListener {
            onCategoryTwoClickListener?.onCategoryTwoClick(it,helper.layoutPosition,item)
        }
    }

    interface OnProjectCategoryOneClickListener {
        fun onCategoryTwoClick(
            view: View,
            position: Int,
            item: ProjectCategoryTreeResp
        )
    }

    private var onCategoryTwoClickListener: OnProjectCategoryOneClickListener? = null
    fun setProjectCategoryClickListener(onProjectCategoryOneClick: OnProjectCategoryOneClickListener) {
        this.onCategoryTwoClickListener = onProjectCategoryOneClick
    }
}