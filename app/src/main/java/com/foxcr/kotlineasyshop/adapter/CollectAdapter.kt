package com.foxcr.kotlineasyshop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.base.utils.GlideUtils
import com.foxcr.base.utils.TimeUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.CollectArticleListResp
import com.zhy.view.flowlayout.TagFlowLayout

class CollectAdapter constructor(data:List<CollectArticleListResp.DatasBean>): BaseQuickAdapter<CollectArticleListResp.DatasBean, CollectAdapter.CollectArticleViewHolder>(R.layout.item_collect_article,data){
    override fun convert(helper: CollectArticleViewHolder, item: CollectArticleListResp.DatasBean) {
        helper.dataBinding(mContext,item)
        helper.setOnClickListener(R.id.mDelItemBtn
        ) { ToastUtils.showToast("删除成功") }
    }


    class CollectArticleViewHolder constructor(view:View): BaseViewHolder(view){
        val mTitleTv: TextView = view.findViewById(R.id.mTitleTv)
        val mAuthorTv: TextView = view.findViewById(R.id.mAuthorTv)
        val mCategoryTv: TextView = view.findViewById(R.id.mCategoryTv)
        val mTimeTv: TextView = view.findViewById(R.id.mTimeTv)
        val mLikeIv: ImageView = view.findViewById(R.id.mLikeIv)
        val mTagFlowL: TagFlowLayout = view.findViewById(R.id.mTagFlowL)
        val mDelItemBtn:Button = view.findViewById(R.id.mDelItemBtn)
        @SuppressLint("SetTextI18n")
        fun dataBinding(mContext: Context, item: CollectArticleListResp.DatasBean){
            mTitleTv.text = item.title
            if (item.author.isNotEmpty()) {
                val authorHtml = StringBuilder()
                    .append("<font color='#666666'>")
                    .append("作者: ")
                    .append("<font/>")
                    .append(item.author)
                mAuthorTv.text = Html.fromHtml(authorHtml.toString())
                mTimeTv.text = "时间: ${TimeUtils.QQFormatTime(mContext, item.publishTime)}"
            } else {
                mAuthorTv.text = "分享人 :${item.shareUser}"
                mTimeTv.text = "时间: ${TimeUtils.QQFormatTime(mContext, item.shareDate)}"
            }
            val categoryHtml = StringBuilder().append("<font color='#666666'>")
                .append("分类: ")
                .append("<font/>")
                .append(item.superChapterName)
                .append("/")
                .append(item.chapterName)
            mCategoryTv.text = Html.fromHtml(categoryHtml.toString())
            mLikeIv.visibility = View.GONE
        }
    }


}