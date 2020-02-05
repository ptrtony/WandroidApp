package com.foxcr.kotlineasyshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleListResp
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleResp
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

class HomeArticleTagAdapter constructor(context: Context,var datas:MutableList<HomeArticleResp.DatasBean.TagsBean>): TagAdapter<HomeArticleResp.DatasBean.TagsBean>(datas){
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    fun setNewData(data: MutableList<HomeArticleResp.DatasBean.TagsBean>?) {
        this.datas = data ?: mutableListOf()
        notifyDataChanged()
    }

    fun addData(data: MutableList<HomeArticleResp.DatasBean.TagsBean>) {
        datas.addAll(data)
        notifyDataChanged()
    }

    override fun getView(
        parent: FlowLayout,
        position: Int,
        t: HomeArticleResp.DatasBean.TagsBean
    ): View {
        val view = mInflater.inflate(R.layout.item_text,parent,false)
        val text: TextView = view.findViewById(R.id.mTagTv)
        text.text = t.name
        return view
    }

}