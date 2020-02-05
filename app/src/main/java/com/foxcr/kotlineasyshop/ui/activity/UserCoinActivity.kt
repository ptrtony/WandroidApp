package com.foxcr.kotlineasyshop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.widgets.HeaderBar
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.UserCoinAdapter
import com.foxcr.kotlineasyshop.data.protocal.LgCoinListResp
import com.foxcr.kotlineasyshop.injection.component.DaggerUserCoinComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.UserCoinPresenter
import com.foxcr.kotlineasyshop.presenter.view.UserCoinView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

@Route(path = "/easyshop/usercoin")
class UserCoinActivity : BaseMvpActivity<UserCoinPresenter>(), UserCoinView, OnRefreshListener,
    OnLoadMoreListener {
    private var mCoinListData:MutableList<LgCoinListResp.DatasBean> = mutableListOf()
    private val mUserCoinAdapter:UserCoinAdapter by lazy {
        UserCoinAdapter(mCoinListData)
    }
    private lateinit var mCoinHeaderBar: HeaderBar
    private lateinit var mCoinSrl: SmartRefreshLayout
    private lateinit var mCoinRl: RecyclerView
    private var page = 1
    override fun initActivityComponent() {
        DaggerUserCoinComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun resLayoutId(): Int = R.layout.activity_user_coin

    override fun initView() {
        mPresenter.mView = this
        mCoinHeaderBar = findViewById(R.id.mCoinHeaderBar)
        mCoinSrl = findViewById(R.id.mCoinSrl)
        mCoinRl = findViewById(R.id.mCoinRl)

        mCoinHeaderBar.onBackClickListener { finish() }
        mCoinSrl.apply {
            autoRefresh()
            setOnRefreshListener(this@UserCoinActivity)
            setOnLoadMoreListener(this@UserCoinActivity)
        }

        mCoinRl.apply {
            layoutManager = LinearLayoutManager(this@UserCoinActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                RecycleViewDivider(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    DisplayUtils.dp2px(1f),
                    resources.getColor(R.color.common_divider),
                    DisplayUtils.dp2px(15f)
                )
            )
            adapter = mUserCoinAdapter
        }

    }

    override fun onUserCoinListResult(lgCoinListResp: LgCoinListResp) {
        if (page == 1 && lgCoinListResp.datas.size<=0){
            mUserCoinAdapter.emptyView = emptyView()
        }

        if (page == 1){
            mCoinListData.clear()
            mCoinListData.addAll(lgCoinListResp.datas)
            mUserCoinAdapter.setNewData(mCoinListData)
            mCoinSrl.finishRefresh()
        }else{
            mCoinListData.addAll(lgCoinListResp.datas)
            mUserCoinAdapter.addData(mCoinListData)
            mCoinSrl.finishLoadMore()
        }
        page++
        if (page>lgCoinListResp.pageCount){
            mCoinSrl.setEnableLoadMore(false)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        mCoinSrl.setEnableLoadMore(true)
        mPresenter.getUserCoinListData(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getUserCoinListData(page)
    }
}