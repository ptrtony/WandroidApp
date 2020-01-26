package com.foxcr.kotlineasyshop.ui.fragment

import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeBannerResp
import com.foxcr.kotlineasyshop.presenter.HomePresenter
import com.foxcr.kotlineasyshop.presenter.view.HomeView

class HomeFragment : BaseMvpFragment<HomePresenter>(),HomeView{

    override fun initView() {

    }

    override fun injectComponent() {

    }

    override fun resLayoutId(): Int = R.layout.fragment_home


    override fun homeBanner(banners: List<HomeBannerResp>) {

    }
}