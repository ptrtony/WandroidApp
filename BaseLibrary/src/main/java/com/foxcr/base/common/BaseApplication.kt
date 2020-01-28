package com.foxcr.base.common

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.R
import com.foxcr.base.injection.component.AppComponent
import com.foxcr.base.injection.component.DaggerAppComponent
import com.foxcr.base.injection.module.AppModule
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.smartrefreshlayout.UniClassicsFooter
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class BaseApplication :Application(){
    init {
        //设置全局的Header构建器
        //SmartRefreshLayout 初始化
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            MaterialHeader(context).apply {
                setShowBezierWave(true)
                setColorSchemeResources(R.color.common_blue)
            }
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator{ context, layout ->
            UniClassicsFooter(context).setDrawableSize(20f)
        }
    }
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