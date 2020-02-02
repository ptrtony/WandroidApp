package com.foxcr.base.widgets

import android.view.View

interface OnLikeClickListener {
    fun onLikeInNetClick(view: View, id: Int)
    fun onLikeOutNetClick(view: View, title: String, author: String, link: String)
    fun cancelCollectClick(id: Int, originId: Int)
}