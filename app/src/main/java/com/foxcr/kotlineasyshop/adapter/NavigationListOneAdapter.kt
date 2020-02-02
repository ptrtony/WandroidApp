package com.foxcr.kotlineasyshop.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.NavigationListOneBean

class NavigationListOneAdapter constructor(datas:List<NavigationListOneBean>) : BaseQuickAdapter<NavigationListOneBean, NavigationListOneAdapter.NavigationListOneViewHolder>(
    R.layout.item_navigation_list_one,datas){

    companion object{
        const val UPDATE_STATE = 101
        const val UPDATE_NAME = 102
    }
    class NavigationListOneViewHolder constructor(view:View):BaseViewHolder(view){
        val mTitleTv : TextView = view.findViewById(R.id.mNavigationListOneTv)
    }

    override fun convert(helper: NavigationListOneViewHolder, item: NavigationListOneBean) {
        helper.mTitleTv.text = item.title
        if (item.isSuccess){
            helper.mTitleTv.setTextColor(Color.parseColor("#03a9f4"))
        }else{
            helper.mTitleTv.setTextColor(Color.parseColor("#333333"))
        }

        helper.itemView.setOnClickListener {
            onListOneClickListener?.onNavigationListOneClick(it,helper.layoutPosition,item)
        }
    }

    override fun onBindViewHolder(
        holder: NavigationListOneViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()){
            onBindViewHolder(holder,position)
        }else if (payloads[0] is Int){
            when(payloads[0] as Int){
                UPDATE_STATE ->{
                    if (mData[position].isSuccess){
                        holder.mTitleTv.setTextColor(Color.parseColor("#03a9f4"))
                    }else{
                        holder.mTitleTv.setTextColor(Color.parseColor("#333333"))
                    }
                }
                UPDATE_NAME -> holder.mTitleTv.text = mData[position].title
            }
        }

    }

    interface OnNavigationListOneClickListener{
        fun onNavigationListOneClick(view:View,position:Int, navigationListOneBean: NavigationListOneBean)
    }

    private var onListOneClickListener : OnNavigationListOneClickListener?=null

    fun setOnNavigationListOneClickListener(onListOneClickListener : OnNavigationListOneClickListener){
        this.onListOneClickListener = onListOneClickListener
    }




}