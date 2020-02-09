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
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.CollectArticleListResp
import com.zhy.view.flowlayout.TagFlowLayout

class CollectAdapter constructor(data: List<CollectArticleListResp.DatasBean>) :
    BaseQuickAdapter<CollectArticleListResp.DatasBean, CollectAdapter.CollectArticleViewHolder>(
        R.layout.item_collect_article,
        data
    ) {
    override fun convert(helper: CollectArticleViewHolder, item: CollectArticleListResp.DatasBean) {
        helper.dataBinding(item)
        helper.setOnClickListener(R.id.mDelItemBtn) {
            onLikeClickListener?.cancelCollectClick(item.id, item.originId)
            onItemChildClick?.onChildClick(helper.layoutPosition)
        }
    }


    class CollectArticleViewHolder constructor(view: View) : BaseViewHolder(view) {
        val mTitleTv: TextView = view.findViewById(R.id.mTitleTv)
        val mAuthorTv: TextView = view.findViewById(R.id.mAuthorTv)
        val mCategoryTv: TextView = view.findViewById(R.id.mCategoryTv)
        val mTimeTv: TextView = view.findViewById(R.id.mTimeTv)
        val mLikeIv: ImageView = view.findViewById(R.id.mLikeIv)
        val mTagFlowL: TagFlowLayout = view.findViewById(R.id.mTagFlowL)
        @SuppressLint("SetTextI18n")
        fun dataBinding(item: CollectArticleListResp.DatasBean) {
            mTitleTv.text = item.title
            mTimeTv.text = "时间: ${item.niceDate}"
            mLikeIv.visibility = View.GONE
            if (item.chapterName.isNotEmpty()) {
                mCategoryTv.visibility = View.GONE
            } else {
                mCategoryTv.visibility = View.VISIBLE
                val categoryHtml = StringBuilder()
                    .append("<font color='#666666'>")
                    .append("分类: ")
                    .append("<font/>")
                    .append(item.chapterName)
                mCategoryTv.text = Html.fromHtml(categoryHtml.toString())
            }
        }

    }

    private var onLikeClickListener: OnLikeClickListener? = null

    fun setOnCollectClickListener(onLikeClickListener: OnLikeClickListener,onItemChildClick: OnItemChildClickListener) {
        this.onLikeClickListener = onLikeClickListener
        this.onItemChildClick = onItemChildClick
    }

    private var onItemChildClick: OnItemChildClickListener? = null

    interface OnItemChildClickListener {
        fun onChildClick(position: Int)
    }
}