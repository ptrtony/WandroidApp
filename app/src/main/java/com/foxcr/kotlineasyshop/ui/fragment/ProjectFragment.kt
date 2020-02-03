package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
import com.foxcr.kotlineasyshop.R

class ProjectFragment : BaseMvpLazyFragment<BasePresenter<BaseView>>(){
    override fun resLayoutId(): Int = R.layout.fragment_project

    override fun injectComponent() {
    }

    override fun initView(view: View) {
    }

    override fun onFragmentFirstVisible() {

    }

}