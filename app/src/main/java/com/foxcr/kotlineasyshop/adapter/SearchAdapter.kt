package com.foxcr.kotlineasyshop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.base.common.BaseConstant
import com.foxcr.base.utils.SPUtil
import com.foxcr.base.utils.TimeUtils
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.SearchArticleResp
import com.zhy.view.flowlayout.TagFlowLayout


/**
 * HomeArticleListResp.DatasBean
 */
class SearchAdapter constructor(articleDatas: MutableList<SearchArticleResp.DatasBean>) :
    BaseQuickAdapter<SearchArticleResp.DatasBean, SearchAdapter.ArticleListViewHolder>(
        R.layout.item_home_article,
        articleDatas
    ) {
    private var mTagData : MutableList<SearchArticleResp.DatasBean.TagsBean> = mutableListOf()
    private val mTagAdapter:SearchTagAdapter by lazy {
        SearchTagAdapter(mContext,mTagData)
    }

    class ArticleListViewHolder constructor(view: View) : BaseViewHolder(view) {
        val mTitleTv: TextView = view.findViewById(R.id.mTitleTv)
        val mAuthorTv: TextView = view.findViewById(R.id.mAuthorTv)
        val mCategoryTv: TextView = view.findViewById(R.id.mCategoryTv)
        val mTimeTv: TextView = view.findViewById(R.id.mTimeTv)
        val mLikeIv: ImageView = view.findViewById(R.id.mLikeIv)
        val mTagFlowL:TagFlowLayout = view.findViewById(R.id.mTagFlowL)
        @SuppressLint("SetTextI18n")
        fun dataBinding(mContext: Context, item: SearchArticleResp.DatasBean){
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
            if (item.collect) {
                mLikeIv.setImageResource(R.mipmap.icon_like)
            } else {
                mLikeIv.setImageResource(R.mipmap.icon_no_like)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun convert(helper: ArticleListViewHolder, item: SearchArticleResp.DatasBean) {

        helper.dataBinding(mContext,item)
        helper.mTagFlowL.adapter = mTagAdapter
        mTagData.clear()
        mTagData.addAll(item.tags)
        mTagAdapter.setNewData(mTagData)
        helper.mLikeIv.setOnClickListener {
            if (SPUtil.getString(BaseConstant.LOGINUSERNAME,"").isNullOrEmpty()||SPUtil.getString(BaseConstant.LOGINUSERPASSWORD,"").isNullOrEmpty()){
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
