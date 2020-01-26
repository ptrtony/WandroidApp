package com.foxcr.kotlineasyshop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foxcr.base.utils.GlideUtils
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeBannerResp
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter constructor(datas:List<HomeBannerResp>) :BannerAdapter<HomeBannerResp, HomeBannerAdapter.BannerViewHolder>(datas){

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_banner,parent,false)
        return BannerViewHolder(view)
    }

    override fun onBindView(holder: BannerViewHolder, data: HomeBannerResp, position: Int, size: Int
    ) {
        GlideUtils.loadImage(data.url,holder.bannerImage)
        holder.bannerTitle.text = data.title
    }


    inner class BannerViewHolder constructor(rootView: View) : RecyclerView.ViewHolder(rootView){
        val bannerImage:ImageView = rootView.findViewById(R.id.mHomeBannerIv)
        val bannerTitle:TextView = rootView.findViewById(R.id.mHomeBannerTitleTv)
        init {
            bannerImage.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

}