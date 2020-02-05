package com.foxcr.kotlineasyshop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.base.common.BaseConstant
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.utils.GlideUtils
import com.foxcr.base.utils.SPUtil
import com.foxcr.base.utils.TimeUtils
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.ArticleEnum.ITEM_ARTICLE_TYPE
import com.foxcr.kotlineasyshop.adapter.ArticleEnum.ITEM_PROJECT_TYPE
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleResp
import com.zhy.view.flowlayout.TagFlowLayout

class HomeArticleAdapter constructor(data:List<HomeArticleResp.DatasBean>) : BaseQuickAdapter<HomeArticleResp.DatasBean, HomeArticleAdapter.HomeArticleViewHolder>(R.layout.item_home_article_or_project,data){


    class HomeArticleViewHolder constructor(view:View):BaseViewHolder(view){
        private lateinit var context:Context
        private var mTagData : MutableList<HomeArticleResp.DatasBean.TagsBean> = mutableListOf()
        private val mTagAdapter:HomeArticleTagAdapter by lazy {
            HomeArticleTagAdapter(context,mTagData)
        }
        private val mArticleContainerCl:ConstraintLayout = view.findViewById(R.id.mArticleContainerCl)
        private val mTitleArticleTv: TextView = view.findViewById(R.id.mTitleArticleTv)
        private val mArticleAuthorTv: TextView = view.findViewById(R.id.mArticleAuthorTv)
        private val mArticleCategoryTv: TextView = view.findViewById(R.id.mArticleCategoryTv)
        private val mArticleTimeTv: TextView = view.findViewById(R.id.mArticleTimeTv)
        private val mArticleLikeIv: ImageView = view.findViewById(R.id.mArticleLikeIv)
        private val mArticleTagFlowL: TagFlowLayout = view.findViewById(R.id.mArticleTagFlowL)

        private val mProjectContainerCl:ConstraintLayout = view.findViewById(R.id.mProjectContainerCl)
        private val mHomeArticleProjectIv: ImageView = view.findViewById(R.id.mHomeArticleProjectIv)
        val mTitleTv: TextView = view.findViewById(R.id.mTitleTv)
        private val mContentTv: TextView = view.findViewById(R.id.mContentTv)
        private val mArticlePublishTimeTv: TextView = view.findViewById(R.id.mArticlePublishTimeTv)
        val mAuthorTv: TextView = view.findViewById(R.id.mAuthorTv)
        val mLikeIv: ImageView = view.findViewById(R.id.mLikeIv)

        @SuppressLint("SetTextI18n")
        fun dataBinding(mContext: Context, item: HomeArticleResp.DatasBean){
            this.context = mContext
            if (item.itemType == ITEM_ARTICLE_TYPE){
                mArticleContainerCl.visibility = View.VISIBLE
                mProjectContainerCl.visibility = View.GONE
                mTitleArticleTv.text = item.title
                if (item.author.isNotEmpty()) {
                    val authorHtml = StringBuilder()
                        .append("<font color='#666666'>")
                        .append("作者: ")
                        .append("<font/>")
                        .append(item.author)
                    mArticleAuthorTv.text = Html.fromHtml(authorHtml.toString())
                    mArticleTimeTv.text = "时间: ${TimeUtils.QQFormatTime(mContext, item.publishTime)}"
                } else {
                    mArticleAuthorTv.text = "分享人 :${item.shareUser}"
                    mArticleTimeTv.text = "时间: ${TimeUtils.QQFormatTime(mContext, item.shareDate)}"
                }
                val categoryHtml = StringBuilder().append("<font color='#666666'>")
                    .append("分类: ")
                    .append("<font/>")
                    .append(item.superChapterName)
                    .append("/")
                    .append(item.chapterName)
                mArticleCategoryTv.text = Html.fromHtml(categoryHtml.toString())
                if (item.collect) {
                    mArticleLikeIv.setImageResource(R.mipmap.icon_like)
                } else {
                    mArticleLikeIv.setImageResource(R.mipmap.icon_no_like)
                }

                mArticleLikeIv.setOnClickListener {
                    if (SPUtil.getString(BaseConstant.LOGINUSERNAME,"").isNullOrEmpty()||SPUtil.getString(BaseConstant.LOGINUSERPASSWORD,"").isNullOrEmpty()){
                        ARouter.getInstance().build("/userCenter/login").greenChannel().navigation()
                        return@setOnClickListener
                    }
                    onLikeListener?.apply {
                        if (item.collect) {
                            mArticleLikeIv.setImageResource(R.mipmap.icon_no_like)
                            if (item.originId == 0 || item.originId == null) {
                                item.originId = -1
                            }
                            cancelCollectClick(item.id, item.originId)

                        } else {
                            mArticleLikeIv.setImageResource(R.mipmap.icon_like)
                            if (item.link.isNullOrEmpty()) {
                                onLikeInNetClick(mArticleLikeIv,item.id)
                            }else{
                                onLikeOutNetClick(mArticleLikeIv,item.title,item.author,item.link)
                            }
                        }
                        item.collect = !item.collect
                    }


                }
                mArticleTagFlowL.adapter = mTagAdapter
                mTagData.clear()
                mTagData.addAll(item.tags)
                mTagAdapter.setNewData(mTagData)
            }else{
                mArticleContainerCl.visibility = View.GONE
                mProjectContainerCl.visibility = View.VISIBLE
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
                mLikeIv.setOnClickListener {
                    if (SPUtil.getString(BaseConstant.LOGINUSERNAME,"").isNullOrEmpty()|| SPUtil.getString(
                            BaseConstant.LOGINUSERPASSWORD,"").isNullOrEmpty()){
                        ARouter.getInstance().build("/userCenter/login").greenChannel().navigation()
                        return@setOnClickListener
                    }
                    onLikeListener?.apply {
                        if (item.collect) {
                            mLikeIv.setImageResource(R.mipmap.icon_no_like)
                            if (item.originId == 0 || item.originId == null) {
                                item.originId = -1
                            }
                            cancelCollectClick(item.id, item.originId)

                        } else {
                            mLikeIv.setImageResource(R.mipmap.icon_like)
                            if (item.link.isNullOrEmpty()) {
                                onLikeInNetClick(mLikeIv,item.id)
                            }else{
                                onLikeOutNetClick(mLikeIv,item.title,item.author,item.link)
                            }
                        }
                        item.collect = !item.collect
                    }


                }


            }

            }

        private var onLikeListener: OnLikeClickListener? = null

        fun setOnLikeClickListener(onLikeClickListener: OnLikeClickListener?) {
            onLikeListener = onLikeClickListener
        }

    }

    private var onLikeListener: OnLikeClickListener? = null

    fun setOnLikeClickListener(onLikeClickListener: OnLikeClickListener) {
        onLikeListener = onLikeClickListener
    }

    override fun convert(helper: HomeArticleViewHolder, item: HomeArticleResp.DatasBean) {
        helper.setOnLikeClickListener(onLikeListener)
        helper.dataBinding(mContext,item)
    }


}