package com.foxcr.kotlineasyshop.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.foxcr.kotlineasyshop.data.protocal.HomeBannerResp
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter constructor(datas:List<HomeBannerResp>) :BannerAdapter<HomeBannerResp, HomeBannerAdapter.BannerViewHolder>(datas){

    inner class BannerViewHolder constructor(imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {

    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: HomeBannerResp?,
        position: Int,
        size: Int
    ) {

    }
}