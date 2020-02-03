package com.foxcr.kotlineasyshop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.base.common.BaseConstant
import com.foxcr.base.utils.SPUtil
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeRequestAnswerListResp
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
class HomeQuestAnswerAdapter constructor(datas:List<HomeRequestAnswerListResp.DatasBean>): BaseQuickAdapter<HomeRequestAnswerListResp.DatasBean
        , HomeQuestAnswerAdapter.HomeQuestAnswerViewHolder>(
    R.layout.item_home_quest_answer,datas){
    private var mTagData : MutableList<HomeRequestAnswerListResp.DatasBean.TagsBean> = mutableListOf()
    private val mTagAdapter:QuestAnswerTagAdapter by lazy {
        QuestAnswerTagAdapter(mContext,mTagData)
    }
    class HomeQuestAnswerViewHolder constructor(view:View):BaseViewHolder(view){
        val mTitleTv : TextView = view.findViewById(R.id.mTitleTv)
        val mTagFlowL : TagFlowLayout = view.findViewById(R.id.mTagFlowL)
        val mAuthorTv : TextView = view.findViewById(R.id.mAuthorTv)
        val mCategoryTv : TextView = view.findViewById(R.id.mCategoryTv)
        val mTimeTv :TextView = view.findViewById(R.id.mTimeTv)
        val mLikeIv : ImageView = view.findViewById(R.id.mLikeIv)

        @SuppressLint("SetTextI18n")
        fun dataBinding(item: HomeRequestAnswerListResp.DatasBean){
            mTitleTv.text = item.title
            val authorHtml = StringBuilder()
                .append("<font color='#666666'>")
                .append("作者: ")
                .append("<font/>")
                .append(item.author)
            mAuthorTv.text = Html.fromHtml(authorHtml.toString())
            val categoryHtml = StringBuilder()
                .append("<font color ='#666666'>")
                .append("分类")
                .append("<font/>")
                .append(item.superChapterName)
                .append("/")
                .append(item.chapterName)
            mCategoryTv.text = Html.fromHtml(categoryHtml.toString())
            mTimeTv.text = "时间: ${item.niceDate}"
            if (item.collect){
                mLikeIv.setImageResource(R.mipmap.icon_like)
            }else{
                mLikeIv.setImageResource(R.mipmap.icon_no_like)
            }

        }
    }

    override fun convert(
        helper: HomeQuestAnswerViewHolder,
        item: HomeRequestAnswerListResp.DatasBean
    ) {
        helper.dataBinding(item)
        helper.mTagFlowL.adapter = mTagAdapter
        mTagData.clear()
        mTagData.addAll(item.tags)
        mTagAdapter.setNewData(mTagData)
        helper.mLikeIv.setOnClickListener {
            if (SPUtil.getString(BaseConstant.LOGINUSERNAME,"").isNullOrEmpty()|| SPUtil.getString(
                    BaseConstant.LOGINUSERPASSWORD,"").isNullOrEmpty()){
                ARouter.getInstance().build("/userCenter/login").greenChannel().navigation()
                return@setOnClickListener
            }
            onLikeClickListener?.apply {
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

        helper.mTagFlowL.setOnTagClickListener { view, position, parent ->
            ToastUtils.showToast("点击标签成功")
            return@setOnTagClickListener true
        }

    }

    private var onLikeClickListener: OnLikeClickListener?=null

    fun setOnLikeClickListener(onLikeClickListener: OnLikeClickListener){
        this.onLikeClickListener = onLikeClickListener
    }


}