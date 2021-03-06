package com.foxcr.base.common

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.R
import com.foxcr.base.common.BaseConstant.Companion.ISNIGHT
import com.foxcr.base.injection.component.AppComponent
import com.foxcr.base.injection.component.DaggerAppComponent
import com.foxcr.base.injection.module.AppModule
import com.foxcr.base.utils.SPUtil
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.smartrefreshlayout.UniClassicsFooter
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class BaseApplication : MultiDexApplication() {

    init {
        //设置全局的Header构建器
        //SmartRefreshLayout 初始化
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            MaterialHeader(context).apply {
                setShowBezierWave(false)
                setColorSchemeResources(R.color.common_blue)
//                setBackgroundColor(resources.getColor(R.color.common_white))
            }
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            UniClassicsFooter(context).setDrawableSize(20f)
        }


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)

        initApplicationInjection()
        context = this

        //设置为日间模式
        if (SPUtil.getBoolean(ISNIGHT)){
            SPUtil.putBoolean(ISNIGHT, true)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            SPUtil.putBoolean(ISNIGHT, false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


    }

    private fun initApplicationInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var context: Context
    }
}