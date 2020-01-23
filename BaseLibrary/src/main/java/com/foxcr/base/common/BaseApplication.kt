package com.foxcr.base.common

import android.app.Application
import android.os.Debug
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.injection.component.AppComponent
import com.foxcr.base.injection.component.DaggerAppComponent
import com.foxcr.base.injection.module.AppModule
import com.foxcr.base.utils.ToastUtils

class BaseApplication :Application(){
    lateinit var appComponent:AppComponent
    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
        initApplicationInjection()
    }

    private fun initApplicationInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}