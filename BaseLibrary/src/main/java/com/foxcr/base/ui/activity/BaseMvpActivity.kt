package com.foxcr.base.ui.activity

import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView

open class BaseMvpActivity<T:BasePresenter<*>> : BaseActivity(),BaseView {
    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError() {

    }

    lateinit var mPresenter:T

}