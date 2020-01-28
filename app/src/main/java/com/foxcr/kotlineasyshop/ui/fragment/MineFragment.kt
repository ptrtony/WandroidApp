package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.kotlineasyshop.R

class MineFragment : BaseMvpFragment<BasePresenter<BaseView>>(){
    override fun initView(view:View) {
    }

    override fun injectComponent() {
    }

    override fun resLayoutId(): Int  = R.layout.fragment_mine
}