package com.foxcr.kotlineasyshop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.widgets.HeaderBar
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.CoinRankAdapter
import com.foxcr.kotlineasyshop.data.protocal.CoinRankListResp
import com.foxcr.kotlineasyshop.injection.component.DaggerCoinBankComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.CoinRankPresenter
import com.foxcr.kotlineasyshop.presenter.view.CoinRankView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener


@Route(path = "/easyshop/coinrank")
class CoinRankActivity : BaseMvpActivity<CoinRankPresenter>(),CoinRankView, OnRefreshListener,
    OnLoadMoreListener {
    private lateinit var mCoinRankHeaderBar:HeaderBar
    private lateinit var mCoinRankSrl:SmartRefreshLayout
    private lateinit var mCoinRankRl:RecyclerView
    private var page = 1

    private var mCoinRankData : MutableList<CoinRankListResp.DatasBean> = mutableListOf()
    private val mCoinRankAdapter: CoinRankAdapter by lazy {
        CoinRankAdapter(mCoinRankData)
    }
    override fun initActivityComponent() {
        DaggerCoinBankComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun resLayoutId(): Int  = R.layout.activity_coin_rank

    override fun initView() {
        mPresenter.mView = this
        mCoinRankHeaderBar = findViewById(R.id.mCoinRankHeaderBar)
        mCoinRankSrl = findViewById(R.id.mCoinRankSrl)
        mCoinRankRl = findViewById(R.id.mCoinRankRl)
        mCoinRankHeaderBar.onBackClickListener { finish() }

        mCoinRankSrl.apply {
            autoRefresh()
            setOnRefreshListener(this@CoinRankActivity)
            setOnLoadMoreListener(this@CoinRankActivity)
        }

        mCoinRankRl.apply {
            layoutManager = LinearLayoutManager(this@CoinRankActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                RecycleViewDivider(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    DisplayUtils.dp2px(1f),
                    resources.getColor(R.color.common_divider),
                    DisplayUtils.dp2px(15f)
                )
            )
            adapter = mCoinRankAdapter
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        mCoinRankSrl.setEnableLoadMore(true)
        mPresenter.getCoinRankListData(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getCoinRankListData(page)
    }

    override fun onCoinBankResult(coinRankListResp: CoinRankListResp) {
        if (page == 1 && coinRankListResp.datas.size<=0){
            mCoinRankAdapter.emptyView = emptyView()
        }
        if (page == 1){
            mCoinRankData.clear()
            mCoinRankData.addAll(coinRankListResp.datas)
            mCoinRankAdapter.setNewData(mCoinRankData)
            mCoinRankSrl.finishRefresh()
        }else{
            mCoinRankData.addAll(coinRankListResp.datas)
            mCoinRankAdapter.addData(mCoinRankData)
            mCoinRankSrl.finishLoadMore()
        }
        page++
        if (page>coinRankListResp.pageCount){
            mCoinRankSrl.setEnableLoadMore(false)
        }
    }
}