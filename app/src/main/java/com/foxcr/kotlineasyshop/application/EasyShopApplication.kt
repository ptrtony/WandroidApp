package com.foxcr.kotlineasyshop.application

import android.app.Application
import com.foxcr.base.utils.ToastUtils

class EasyShopApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
    }
}