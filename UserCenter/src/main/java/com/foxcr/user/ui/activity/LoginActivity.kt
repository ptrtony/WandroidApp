package com.foxcr.user.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.foxcr.base.ext.onClick
import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.utils.ToastUtils
import com.foxcr.user.R
import com.foxcr.user.data.protocal.LoginResp
import com.foxcr.user.injection.component.DaggerUserLoginComponent
import com.foxcr.user.injection.module.UserRegisterModule
import com.foxcr.user.presenter.LoginPresenter
import com.foxcr.user.presenter.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*

@Route(path = "/userCenter/login")
class LoginActivity : BaseMvpActivity<LoginPresenter>(),LoginView {
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
        mLoginBtn.onClick {
            mPresenter.login(mUserNameEtn.text.toString().trim(),
                mPwdEtn.text.toString().trim())
        }
        mTitleBarHb.onBackClickListener { finish() }

    }

    override fun onLoginResult(loginResp: LoginResp) {

    }

    override fun onErrorMsg(errorMsg: String?) {
        errorMsg?.let {
            ToastUtils.showToast(it)
        }
    }
}
