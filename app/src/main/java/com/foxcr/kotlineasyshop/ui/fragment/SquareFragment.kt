package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.HomeSquareUserArticleAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeSquareUserArticleListResp
import com.foxcr.kotlineasyshop.injection.component.DaggerSquareComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.SquarePresenter
import com.foxcr.kotlineasyshop.presenter.view.SquareView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_square.*

class SquareFragment :BaseMvpFragment<SquarePresenter>(), OnLoadMoreListener, OnRefreshListener,SquareView {
    lateinit var homeSquareUserArticleAdapter:HomeSquareUserArticleAdapter
    private var squareUserArticleDatas:MutableList<HomeSquareUserArticleListResp.DatasBean> = mutableListOf()
    private var page:Int = 0
    override fun resLayoutId(): Int = R.layout.fragment_square

    override fun injectComponent() {
        DaggerSquareComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }
    override fun initView(view: View) {
        mPresenter.mView = this
        mSquareSrl.autoRefresh()
        mSquareSrl.setOnLoadMoreListener(this)
        mSquareSrl.setOnRefreshListener(this)
        mSquareRv.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        homeSquareUserArticleAdapter = HomeSquareUserArticleAdapter(squareUserArticleDatas)
        mSquareRv.addItemDecoration(
            RecycleViewDivider(context,LinearLayoutManager.HORIZONTAL,
                DisplayUtils.dp2px(1f),resources.getColor(R.color.common_divider), DisplayUtils.dp2px(15f))
        )
        mSquareRv.adapter = homeSquareUserArticleAdapter
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getSquareArticleUserList(page)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.getSquareArticleUserList(page)
    }

    override fun onHomeSquareUserArticleList(homeSquareUserArticleListResp: HomeSquareUserArticleListResp) {
        if (page == 0 && squareUserArticleDatas.size>0){
            squareUserArticleDatas.clear()
        }
        squareUserArticleDatas.addAll(homeSquareUserArticleListResp.datas)
        if (page == 0){
            homeSquareUserArticleAdapter.setNewData(squareUserArticleDatas)
            mSquareSrl.finishRefresh()
            mSquareSrl.setEnableRefresh(false)
        }else{
            homeSquareUserArticleAdapter.addData(squareUserArticleDatas)
            mSquareSrl.finishLoadMore()
        }
        page = homeSquareUserArticleListResp.curPage
        mSquareSrl.setEnableRefresh(false)
        if (page>=homeSquareUserArticleListResp.pageCount){
            mSquareSrl.setEnableLoadMore(false)
        }
    }
}