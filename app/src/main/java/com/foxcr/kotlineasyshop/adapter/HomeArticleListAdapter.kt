package com.foxcr.kotlineasyshop.adapter

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.base.utils.TimeUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.likebutton.LikeButton
import com.foxcr.base.widgets.likebutton.OnLikeListener
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleListResp


/**
 * HomeArticleListResp.DatasBean
 */
class HomeArticleListAdapter constructor(articleDatas: MutableList<HomeArticleListResp.DatasBean>) :
    BaseQuickAdapter<HomeArticleListResp.DatasBean, HomeArticleListAdapter.ArticleListViewHolder>(
        R.layout.item_home_article,
        articleDatas
    ) {

    class ArticleListViewHolder constructor(view: View) : BaseViewHolder(view) {
        val mTitleTv: TextView = view.findViewById(R.id.mTitleTv)
        val mAuthorTv: TextView = view.findViewById(R.id.mAuthorTv)
        val mCategoryTv: TextView = view.findViewById(R.id.mCategoryTv)
        val mTimeTv: TextView = view.findViewById(R.id.mTimeTv)
        val mLikeIv: ImageView = view.findViewById(R.id.mLikeIv)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun convert(helper: ArticleListViewHolder, item: HomeArticleListResp.DatasBean) {

        helper.mTitleTv.text = item.title
        if (item.author.isNotEmpty()) {
            val authorHtml = StringBuilder()
                .append("<font color='#666666'>")
                .append("作者: ")
                .append("<font/>")
                .append(item.author)
            helper.mAuthorTv.text = Html.fromHtml(authorHtml.toString())
            helper.mTimeTv.text = "时间: ${TimeUtils.QQFormatTime(mContext, item.publishTime)}"
        } else {
            helper.mAuthorTv.text = "分享人 :${item.shareUser}"
            helper.mTimeTv.text = "时间: ${TimeUtils.QQFormatTime(mContext, item.shareDate)}"
        }
        val categoryHtml = StringBuilder().append("<font color='#666666'>")
            .append("分类: ")
            .append("<font/>")
            .append(item.superChapterName)
            .append("/")
            .append(item.chapterName)
        helper.mCategoryTv.text = Html.fromHtml(categoryHtml.toString())
        if (item.collect) {
            helper.mLikeIv.setImageResource(R.mipmap.icon_like)
        } else {
            helper.mLikeIv.setImageResource(R.mipmap.icon_no_like)
        }

        helper.mLikeIv.setOnClickListener {
            onLikeListener?.apply {
                if (item.collect) {
                    helper.mLikeIv.setImageResource(R.mipmap.icon_no_like)
                    if (item.originId == 0 || item.originId == null) {
                        item.originId = -1
                    }
                    cancelCollectClick(item.id, item.originId)

                } else {
                    helper.mLikeIv.setImageResource(R.mipmap.icon_like)
                    if (item.link.isNullOrEmpty()) {
                        onLikeInNetClick(helper.mLikeIv,item.id)
                    }else{
                        onLikeOutNetClick(helper.mLikeIv,item.title,item.author,item.link)
                    }
                }
                item.collect = !item.collect
                notifyItemInserted(getParentPosition(item))
            }


        }

    }


    private var onLikeListener: OnLikeClickListener? = null

    fun setOnLikeClickListener(onLikeClickListener: OnLikeClickListener) {
        onLikeListener = onLikeClickListener
    }

    interface OnLikeClickListener {
        fun onLikeInNetClick(view:View,id: Int)
        fun onLikeOutNetClick(view:View,title: String, author: String, link: String)
        fun cancelCollectClick(id: Int, originId: Int)
    }

}
