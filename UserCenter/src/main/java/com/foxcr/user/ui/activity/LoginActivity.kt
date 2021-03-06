package com.foxcr.user.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.common.BaseConstant
import com.foxcr.base.common.EasyNavigationCallback
import com.foxcr.base.ext.enable
import com.foxcr.base.ext.onClick
import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.utils.SPUtil
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.statusbar.StatusBarUtils
import com.foxcr.user.R
import com.foxcr.user.data.protocal.LoginResp
import com.foxcr.user.injection.component.DaggerUserLoginComponent
import com.foxcr.user.injection.module.UserRegisterModule
import com.foxcr.user.presenter.LoginPresenter
import com.foxcr.user.presenter.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.mPwdEtn
import kotlinx.android.synthetic.main.activity_login.mTitleBarHb
import kotlinx.android.synthetic.main.activity_login.mUserNameEtn


/**
 * 登录
 */
@Route(path = "/userCenter/login")
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView {
    private lateinit var loginQuitBroadcastReceiver:LoginQuitBroadcastReceiver
    private var backgroundColors: IntArray =
        intArrayOf(R.drawable.common_button_enable_bg, R.drawable.common_button_disenable_bg)

    override fun initActivityComponent() {
        DaggerUserLoginComponent.builder()
            .activityComponent(activityComponent)
            .userRegisterModule(UserRegisterModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun resLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        loginQuitBroadcastReceiver = LoginQuitBroadcastReceiver(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.easyshop.loginquite")
        registerReceiver(loginQuitBroadcastReceiver,intentFilter)
        StatusBarUtils.setImmersiveStatusBar(this,false)
        StatusBarUtils.setStatusBarColor(this, resources.getColor(com.foxcr.base.R.color.common_blue))
        mLoginBtn.setBackgroundResource(R.drawable.common_button_disenable_bg)
        mLoginBtn.enable(mUserNameEtn, backgroundColors) { isEnable() }
        mLoginBtn.enable(mPwdEtn, backgroundColors) { isEnable() }
        mLoginBtn.onClick {
            mPresenter.login(
                mUserNameEtn.text.toString().trim(),
                mPwdEtn.text.toString().trim()
            )
        }
        mTitleBarHb.onBackClickListener { finish() }
        mTitleBarHb.onRightClickListener {
            ARouter.
                getInstance().
                build("/userCenter/register")
                .greenChannel()
                .navigation()
        }
    }

    override fun onLoginResult(loginResp: LoginResp) {
        SPUtil.putString(BaseConstant.LOGINUSERNAME,mUserNameEtn.text.toString().trim())
        SPUtil.putString(BaseConstant.LOGINUSERPASSWORD,mPwdEtn.text.toString().trim())
        val intent = Intent()
        intent.action = "android.easyshop.refreshUserInfo"
        sendBroadcast(intent)
        finish()
    }

    override fun onErrorMsg(errorMsg: String?) {
        errorMsg?.let {
            ToastUtils.showToast(it)
        }
    }

    private fun isEnable(): Boolean {
        return mUserNameEtn.text.toString().trim().isNotEmpty()
                && mPwdEtn.text.toString().trim().isNotEmpty()
    }

    private class LoginQuitBroadcastReceiver constructor(val activity: LoginActivity) : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.easyshop.loginquite"){
                activity.finish()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(loginQuitBroadcastReceiver)
    }
}
