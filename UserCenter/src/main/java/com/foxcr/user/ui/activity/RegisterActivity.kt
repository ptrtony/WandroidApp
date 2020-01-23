package com.foxcr.user.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.common.AppManager
import com.foxcr.base.ext.onClick
import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.utils.ToastUtils
import com.foxcr.user.R
import com.foxcr.user.data.protocal.RegisterResp
import com.foxcr.user.injection.component.DaggerUserRegisterComponent
import com.foxcr.user.injection.module.UserRegisterModule
import com.foxcr.user.presenter.RegisterPresenter
import com.foxcr.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*

@Route(path = "/userCenter/register")
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {

    private var pressTime: Long = 0L
    override fun onRegisterResult(registerResp: RegisterResp) {
        ToastUtils.showToast("注册成功")
    }

    override fun onErrorMsg(errorMsg: String?) {
        if (errorMsg != null) {
            ToastUtils.showToast(errorMsg)
        }
    }

    override fun initView() {
        mRegisterBtn.onClick {
            mPresenter.register(
                mUserNameEtn.text.toString().trim(),
                mPwdEtn.text.toString().trim(),
                mRePwdEtn.text.toString().trim()
            )
        }

        mTitleBarHb.onRightClickListener{
            ARouter.getInstance().build("/userCenter/login")
                .navigation()
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


    override fun onBackPressed() {
        super.onBackPressed()
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            ToastUtils.showToast("再按一次退出")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }

}
