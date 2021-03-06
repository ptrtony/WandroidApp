package com.foxcr.user.ui.activity

import android.content.Intent
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.common.AppManager
import com.foxcr.base.common.BaseConstant
import com.foxcr.base.common.EasyNavigationCallback
import com.foxcr.base.ext.enable
import com.foxcr.base.ext.onClick
import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.utils.SPUtil
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.statusbar.StatusBarUtils
import com.foxcr.user.R
import com.foxcr.user.data.protocal.RegisterResp
import com.foxcr.user.injection.component.DaggerUserRegisterComponent
import com.foxcr.user.injection.module.UserRegisterModule
import com.foxcr.user.presenter.RegisterPresenter
import com.foxcr.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*


/**
 * 注册
 */
@Route(path = "/userCenter/register")
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {

    private var backgroundColors:IntArray = intArrayOf(R.drawable.common_button_enable_bg,R.drawable.common_button_disenable_bg)
    override fun onRegisterResult(registerResp: RegisterResp) {
        SPUtil.putString(BaseConstant.LOGINUSERNAME,mUserNameEtn.text.toString().trim())
        SPUtil.putString(BaseConstant.LOGINUSERPASSWORD,mPwdEtn.text.toString().trim())
        val intent = Intent()
        intent.action = "android.easyshop.refreshUserInfo"
        intent.action = "android.easyshop.loginquite"
        sendBroadcast(intent)
        finish()
    }

    override fun onErrorMsg(errorMsg: String?) {
        if (errorMsg != null) { ToastUtils.showToast(errorMsg) }
    }

    override fun initView() {
        StatusBarUtils.setImmersiveStatusBar(this,false)
        StatusBarUtils.setStatusBarColor(this, resources.getColor(com.foxcr.base.R.color.common_blue))
        mTitleBarHb.onBackClickListener { finish() }
        mRegisterBtn.setBackgroundResource(R.drawable.common_button_disenable_bg)
        mRegisterBtn.enable(mUserNameEtn,backgroundColors) {isEnable()}
        mRegisterBtn.enable(mPwdEtn,backgroundColors) {isEnable()}
        mRegisterBtn.enable(mRePwdEtn,backgroundColors) {isEnable()}

        mRegisterBtn.onClick {
            mPresenter.register(
                mUserNameEtn.text.toString().trim(),
                mPwdEtn.text.toString().trim(),
                mRePwdEtn.text.toString().trim()
            )
        }


    }


    override fun resLayoutId(): Int = R.layout.activity_register

    override fun initActivityComponent() {
        DaggerUserRegisterComponent.builder()
            .activityComponent(activityComponent)
            .userRegisterModule(UserRegisterModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }




    private fun isEnable():Boolean{
        return mUserNameEtn.text.toString().trim().isNotEmpty()
                && mPwdEtn.text.toString().trim().isNotEmpty()
                && mRePwdEtn.text.toString().trim().isNotEmpty()
    }

}
