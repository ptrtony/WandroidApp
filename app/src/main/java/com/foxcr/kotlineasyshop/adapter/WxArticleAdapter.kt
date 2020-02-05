package com.foxcr.kotlineasyshop.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.base.common.BaseConstant
import com.foxcr.base.utils.SPUtil
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.WxArticleListResp

class WxArticleAdapter constructor(data: List<WxArticleListResp.DatasBean>) :
    BaseQuickAdapter<WxArticleListResp.DatasBean, WxArticleAdapter.WxArticleViewHolder>(
        R.layout.item_wx_article, data
    ) {
    class WxArticleViewHolder constructor(view: View) : BaseViewHolder(view) {
        private val mWxArticleTitleTv: TextView = view.findViewById(R.id.mWxArticleTitleTv)
        private val mWxArticleTimeTv: TextView = view.findViewById(R.id.mWxArticleTimeTv)
        val mWxArticleLoveIv: ImageView = view.findViewById(R.id.mWxArticleLoveIv)

        fun dataBinding(item: WxArticleListResp.DatasBean) {
            mWxArticleTitleTv.text = item.title
            mWxArticleTimeTv.text = "时间: ${item.niceDate}"
            if (item.collect) {
                mWxArticleLoveIv.setImageResource(R.mipmap.icon_like)
            } else {
                mWxArticleLoveIv.setImageResource(R.mipmap.icon_no_like)
            }
        }
    }

    override fun convert(helper: WxArticleViewHolder, item: WxArticleListResp.DatasBean) {
        helper.dataBinding(item)
        helper.mWxArticleLoveIv.setOnClickListener {
            if (SPUtil.getString(
                    BaseConstant.LOGINUSERNAME,
                    ""
                ).isNullOrEmpty() || SPUtil.getString(
                    BaseConstant.LOGINUSERPASSWORD, ""
                ).isNullOrEmpty()
            ) {
                ARouter.getInstance().build("/userCenter/login").greenChannel().navigation()
                return@setOnClickListener
            }
            onLikeClickListener?.apply {
                if (item.collect) {
                    helper.mWxArticleLoveIv.setImageResource(R.mipmap.icon_no_like)
                    if (item.originId == 0 || item.originId == null) {
                        item.originId = -1
                    }
                    cancelCollectClick(item.id, item.originId)

                } else {
                    helper.mWxArticleLoveIv.setImageResource(R.mipmap.icon_like)
                    if (item.link.isNullOrEmpty()) {
                        onLikeInNetClick(helper.mWxArticleLoveIv, item.id)
                    } else {
                        onLikeOutNetClick(
                            helper.mWxArticleLoveIv,
                            item.title,
                            item.author,
                            item.link
                        )
                    }
                }
                item.collect = !item.collect
            }
        }
    }

    private var onLikeClickListener: OnLikeClickListener? = null
    fun setOnLikeClickListener(onLikeClickListener: OnLikeClickListener) {
        this.onLikeClickListener = onLikeClickListener
    }
}