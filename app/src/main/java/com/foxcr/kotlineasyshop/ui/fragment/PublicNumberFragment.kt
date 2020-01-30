package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.kotlineasyshop.R

class PublicNumberFragment : BaseMvpFragment<BasePresenter<BaseView>>(){
    override fun resLayoutId(): Int = R.layout.fragment_public_number

    override fun injectComponent() {
    }

    override fun initView(view: View) {
    }
}