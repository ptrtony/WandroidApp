package com.foxcr.kotlineasyshop.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.base.common.BaseConstant
import com.foxcr.base.utils.GlideUtils
import com.foxcr.base.utils.SPUtil
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.ProjectCategoryListResp

class ProjectCagetoryListAdapter constructor(articleDatas: MutableList<ProjectCategoryListResp.DatasBean>) :
    BaseQuickAdapter<ProjectCategoryListResp.DatasBean,
            ProjectCagetoryListAdapter.ArticleListViewHolder>(
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

        fun dataBinding(item: ProjectCategoryListResp.DatasBean){
            GlideUtils.loadImage(item.envelopePic, mHomeArticleProjectIv)
            mTitleTv.text = item.title
            mContentTv.text = item.desc
            mArticlePublishTimeTv.text = item.niceDate
            mAuthorTv.text = item.author
            if (item.collect) {
                mLikeIv.setImageResource(R.mipmap.icon_like)
            }else{
                mLikeIv.setImageResource(R.mipmap.icon_no_like)
            }
        }
    }

    override fun convert(
        helper: ArticleListViewHolder,
        item: ProjectCategoryListResp.DatasBean
    ) {
        helper.dataBinding(item)
        helper.mLikeIv.setOnClickListener {
            if (SPUtil.getString(BaseConstant.LOGINUSERNAME,"").isNullOrEmpty()|| SPUtil.getString(
                    BaseConstant.LOGINUSERPASSWORD,"").isNullOrEmpty()){
                ARouter.getInstance().build("/userCenter/login").greenChannel().navigation()
                return@setOnClickListener
            }
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
            }


        }
    }

    private var onLikeListener: OnLikeClickListener? = null

    fun setOnLikeClickListener(onLikeClickListener: OnLikeClickListener) {
        onLikeListener = onLikeClickListener
    }

}