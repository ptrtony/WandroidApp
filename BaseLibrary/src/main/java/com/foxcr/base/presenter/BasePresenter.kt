package com.foxcr.base.presenter

import com.foxcr.base.presenter.view.BaseView

open class BasePresenter<T:BaseView> {
    lateinit var mView:T
}