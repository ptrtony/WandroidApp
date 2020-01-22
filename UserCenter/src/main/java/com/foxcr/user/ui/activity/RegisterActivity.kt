package com.foxcr.user.ui.activity

import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.utils.ToastUtils
import com.foxcr.user.R
import com.foxcr.user.data.protocal.RegisterResp
import com.foxcr.user.injection.component.DaggerUserRegisterComponent
import com.foxcr.user.injection.module.UserRegisterModule
import com.foxcr.user.presenter.RegisterPresenter
import com.foxcr.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(),RegisterView {

    private fun initInjection() {
        DaggerUserRegisterComponent.builder()
            .activityComponent(activityComponent)
            .userRegisterModule(UserRegisterModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onRegisterResult(registerResp: RegisterResp) {
        ToastUtils.showToast("注册成功")
    }

    override fun onErrorMsg(errorMsg: String?) {
        if (errorMsg!=null){
            ToastUtils.showToast(errorMsg)
        }
    }

    override fun initView() {
        initInjection()
        mRegisterBtn.setOnClickListener {
            mPresenter.register(mUserNameEtn.text.toString().trim(),
                mPwdEtn.text.toString().trim(),
                mRePwdEtn.text.toString().trim())
        }
    }

    override fun resLayoutId(): Int = R.layout.activity_register


}
