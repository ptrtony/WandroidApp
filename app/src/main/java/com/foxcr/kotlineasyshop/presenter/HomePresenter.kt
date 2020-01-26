package com.foxcr.kotlineasyshop.presenter

import com.foxcr.base.presenter.BasePresenter
import com.foxcr.kotlineasyshop.presenter.view.HomeView
import com.foxcr.kotlineasyshop.service.impl.HomeServiceImpl
import javax.inject.Inject

class HomePresenter @Inject constructor(): BasePresenter<HomeView>() {

    @Inject
    lateinit var homeServiceImpl: HomeServiceImpl
}