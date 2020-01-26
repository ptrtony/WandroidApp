package com.foxcr.base.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideUtils {
    fun loadImage(url:String,imageView:ImageView){
        Glide.with(imageView)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
}