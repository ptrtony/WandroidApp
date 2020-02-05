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
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.base.utils.GlideUtils
import com.foxcr.base.utils.TimeUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.CollectArticleListResp
import com.zhy.view.flowlayout.TagFlowLayout

class CollectAdapter constructor(data:List<CollectArticleListResp.DatasBean>): BaseMultiItemQuickAdapter<CollectArticleListResp.DatasBean,BaseViewHolder>(data){
    override fun convert(helper: BaseViewHolder?, item: CollectArticleListResp.DatasBean) {
        if (helper is CollectArticleViewHolder){
            helper.dataBinding(mContext,item)
            helper.setOnClickListener(R.id.mDelItemBtn,
                View.OnClickListener { ToastUtils.showToast("删除成功") })
        }else if (helper is CollectProjectViewHolder){
            helper.setOnClickListener(R.id.mDelItemBtn,
                View.OnClickListener { ToastUtils.showToast("删除成功") })
            helper.dataBinding(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == 0){
            return CollectArticleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_collect_article,parent,false))
        }
        return CollectProjectViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_collect_article_project,parent,false))
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].itemType
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

    class CollectProjectViewHolder constructor(view:View):BaseViewHolder(view){
        val mHomeArticleProjectIv: ImageView = view.findViewById(R.id.mHomeArticleProjectIv)
        val mTitleTv: TextView = view.findViewById(R.id.mTitleTv)
        val mContentTv: TextView = view.findViewById(R.id.mContentTv)
        val mArticlePublishTimeTv: TextView = view.findViewById(R.id.mArticlePublishTimeTv)
        val mAuthorTv: TextView = view.findViewById(R.id.mAuthorTv)
        val mLikeIv: ImageView = view.findViewById(R.id.mLikeIv)

        fun dataBinding(item: CollectArticleListResp.DatasBean){
            GlideUtils.loadImage(item.envelopePic, mHomeArticleProjectIv)
            mTitleTv.text = item.title
            mContentTv.text = item.desc
            mArticlePublishTimeTv.text = item.niceDate
            mAuthorTv.text = item.author
            mLikeIv.visibility = View.GONE
        }
    }


}