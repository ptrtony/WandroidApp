package com.foxcr.user.ui.activity

import android.os.Bundle
import android.widget.Toast
import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.utils.ToastUtils
import com.foxcr.user.R
import com.foxcr.user.data.protocal.RegisterResp
import com.foxcr.user.presenter.RegisterPresenter
import com.foxcr.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(),RegisterView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mPresenter = RegisterPresenter()
        mPresenter.mView = this
        mRegisterBtn.setOnClickListener {
            mPresenter.register(mUserNameEtn.text.toString().trim(),
                mPwdEtn.text.toString().trim(),
                mRePwdEtn.text.toString().trim())
        }
    }

    override fun onRegisterResult(registerResp: RegisterResp) {
        ToastUtils.showToast("注册成功")
    }

    override fun onErrorMsg(errorMsg: String) {
        ToastUtils.showToast(errorMsg)
    }


}
