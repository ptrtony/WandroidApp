package com.foxcr.kotlineasyshop.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.base.utils.GlideUtils
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleProjectListResp

class HomeArticleProjectListAdapter constructor(articleDatas: MutableList<HomeArticleProjectListResp.DatasBean>) :
    BaseQuickAdapter<HomeArticleProjectListResp.DatasBean,
            HomeArticleProjectListAdapter.ArticleListViewHolder>(
        R.layout.item_home_article_project,
        articleDatas
    ) {

    class ArticleListViewHolder constructor(view: View) : BaseViewHolder(view) {
        val mHomeArticleProjectIv: ImageView = view.findViewById(R.id.mHomeArticleProjectIv)
        val mTitleTv: TextView = view.findViewById(R.id.mTitleTv)
        val mContentTv: TextView = view.findViewById(R.id.mContentTv)
        val mArticlePublishTimeTv: TextView = view.findViewById(R.id.mArticlePublishTimeTv)
        val mAuthorTv: TextView = view.findViewById(R.id.mAuthorTv)
        val mLikeIv: ImageView = view.findViewById(R.id.mLikeIv)
    }

    override fun convert(
        helper: ArticleListViewHolder,
        item: HomeArticleProjectListResp.DatasBean
    ) {
        GlideUtils.loadImage(item.envelopePic, helper.mHomeArticleProjectIv)
        helper.mTitleTv.text = item.title
        helper.mContentTv.text = item.desc
        helper.mArticlePublishTimeTv.text = item.niceDate
        helper.mAuthorTv.text = item.author
        if (item.collect) {
            helper.mLikeIv.setImageResource(R.mipmap.icon_like)
        }else{
            helper.mLikeIv.setImageResource(R.mipmap.icon_no_like)
        }
        helper.mLikeIv.setOnClickListener {
            onLikeClickListener?.onProjectLikeClick(getParentPosition(item), !item.collect)
        }
    }

    private var onLikeClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onLikeClickListener = onClickListener
    }

    interface OnClickListener {
        fun onProjectLikeClick(position: Int, collect: Boolean)
    }
}