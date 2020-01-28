package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.HomeSquareUserArticleAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeSquareUserArticleListResp
import com.foxcr.kotlineasyshop.presenter.SquarePresenter
import com.foxcr.kotlineasyshop.presenter.view.SquareView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_square.*

class SquareFragment :BaseMvpFragment<SquarePresenter>(), OnLoadMoreListener, OnRefreshListener,SquareView {
    lateinit var homeSquareUserArticleAdapter:HomeSquareUserArticleAdapter
    private var squareUserArticleDatas:MutableList<HomeSquareUserArticleListResp.DatasBean> = mutableListOf()
    private var page:Int = 0
    override fun resLayoutId(): Int = R.layout.fragment_square

    override fun injectComponent() {

    }
    override fun initView(view: View) {
        mSquareSrl.autoRefresh()
        mSquareSrl.setOnLoadMoreListener(this)
        mSquareSrl.setOnRefreshListener(this)
        mSquareRv.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        homeSquareUserArticleAdapter = HomeSquareUserArticleAdapter(squareUserArticleDatas)
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