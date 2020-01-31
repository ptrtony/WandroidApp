package com.foxcr.kotlineasyshop.adapter

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.base.utils.TimeUtils
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeSquareUserArticleListResp

class HomeSquareUserArticleAdapter constructor(articleDatas: MutableList<HomeSquareUserArticleListResp.DatasBean>) :
    BaseQuickAdapter<HomeSquareUserArticleListResp.DatasBean,
            HomeSquareUserArticleAdapter.ArticleListViewHolder>(
        R.layout.item_home_square_article,
        articleDatas
    ) {

    class ArticleListViewHolder constructor(view: View) : BaseViewHolder(view) {
        val mTitleTv: TextView = view.findViewById(R.id.mTitleTv)
        val mAuthorTv: TextView = view.findViewById(R.id.mAuthorTv)
//        val mCategoryTv: TextView = view.findViewById(R.id.mCategoryTv)
        val mTimeTv: TextView = view.findViewById(R.id.mTimeTv)
        val mLikeIv: ImageView = view.findViewById(R.id.mLikeIv)
    }

    override fun convert(helper: ArticleListViewHolder, item: HomeSquareUserArticleListResp.DatasBean) {
        helper.mTitleTv.text = item.title
        if (item.author.isNotEmpty()){
            val authorHtml = StringBuilder()
                .append("<font color='#666666'>")
                .append("作者: ")
                .append("<font/>")
                .append(item.author)
            helper.mAuthorTv.text = Html.fromHtml(authorHtml.toString())
            helper.mTimeTv.text = "时间: ${TimeUtils.QQFormatTime(mContext,item.publishTime)}"
        }else{
            helper.mAuthorTv.text = "分享人 :${item.shareUser}"
            helper.mTimeTv.text = "时间: ${TimeUtils.QQFormatTime(mContext,item.shareDate)}"
        }
//        val categoryHtml = StringBuilder().append("<font color='#666666'>")
//            .append("分类: ")
//            .append("<font/>")
//            .append(item.superChapterName)
//            .append("/")
//            .append(item.chapterName)
//        helper.mCategoryTv.text = Html.fromHtml(categoryHtml.toString())
        if (item.collect){
            helper.mLikeIv.setImageResource(R.mipmap.icon_like)
        }else{
            helper.mLikeIv.setImageResource(R.mipmap.icon_no_like)
        }

        helper.mLikeIv.setOnClickListener {
            onLikeClickListener?.onLikeClick(getParentPosition(item),!item.collect)
        }
    }

    private var onLikeClickListener:OnLikeClickListener?=null

    fun setOnLikeClickListener(onLikeClickListener:OnLikeClickListener){
        this.onLikeClickListener = onLikeClickListener
    }

    interface OnLikeClickListener{
        fun onLikeClick(position:Int,collect:Boolean)
    }
}