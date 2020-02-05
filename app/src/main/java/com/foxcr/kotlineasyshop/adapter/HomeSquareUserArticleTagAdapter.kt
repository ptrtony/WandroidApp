package com.foxcr.kotlineasyshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeSquareUserArticleListResp
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

class HomeSquareUserArticleTagAdapter constructor(context: Context, var datas:MutableList<HomeSquareUserArticleListResp.DatasBean.TagsBean>): TagAdapter<HomeSquareUserArticleListResp.DatasBean.TagsBean>(datas){
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    fun setNewData(data: MutableList<HomeSquareUserArticleListResp.DatasBean.TagsBean>?) {
        this.datas = data ?: mutableListOf()
        notifyDataChanged()
    }

    fun addData(data: MutableList<HomeSquareUserArticleListResp.DatasBean.TagsBean>) {
        datas.addAll(data)
        notifyDataChanged()
    }

    override fun getView(
        parent: FlowLayout,
        position: Int,
        t: HomeSquareUserArticleListResp.DatasBean.TagsBean
    ): View {
        val view = mInflater.inflate(R.layout.item_text,parent,false)
        val text: TextView = view.findViewById(R.id.mTagTv)
        text.text = t.name
        return view
    }

}