package com.foxcr.kotlineasyshop.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.common.BaseConstant
import com.foxcr.base.utils.SPUtil

@Interceptor(priority = 8,name = "appInterceptor")
class AppRouteInterceptor : IInterceptor{
    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        if (!(SPUtil.getString(BaseConstant.LOGINUSERNAME,"").isNullOrEmpty()
            && SPUtil.getString(BaseConstant.LOGINUSERPASSWORD,"").isNullOrEmpty())){
            callback?.onContinue(postcard)
        }else{
            ARouter.getInstance()
                .build("/userCenter/login")
                .greenChannel()
                .navigation()
        }
    }

    override fun init(context: Context?) {
    }
}