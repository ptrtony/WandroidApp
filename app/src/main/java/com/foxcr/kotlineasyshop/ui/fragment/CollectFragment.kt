package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.CollectAdapter
import com.foxcr.kotlineasyshop.data.protocal.CollectArticleListResp
import com.foxcr.kotlineasyshop.injection.component.DaggerCollectComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.CollectPresenter
import com.foxcr.kotlineasyshop.presenter.view.CollectView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

class CollectFragment : BaseMvpLazyFragment<CollectPresenter>(),CollectView, OnRefreshListener,
    OnLoadMoreListener {
    private lateinit var mCollectSmartRefresh:SmartRefreshLayout
    private lateinit var mCollectRl:RecyclerView
    private var mCollectData : MutableList<CollectArticleListResp.DatasBean> = mutableListOf()
    private val mCollectAdapter: CollectAdapter by lazy {
        CollectAdapter(mCollectData)
    }
    private var page = 1
    override fun resLayoutId(): Int = R.layout.fragment_collect

    override fun injectComponent() {
        DaggerCollectComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun initView(view: View) {
        mPresenter.mView = this
        mCollectSmartRefresh = view.findViewById(R.id.mCollectSmartRefresh)
        mCollectRl = view.findViewById(R.id.mCollectRl)

        mCollectSmartRefresh.apply {
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setOnRefreshListener(this@CollectFragment)
            setOnLoadMoreListener(this@CollectFragment)
        }

        mCollectRl.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(
                RecycleViewDivider(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    DisplayUtils.dp2px(1f),
                    resources.getColor(R.color.common_divider),
                    DisplayUtils.dp2px(15f)
                )
            )

            adapter = mCollectAdapter
        }
        mCollectAdapter.openLoadAnimation()
    }

    override fun onFragmentFirstVisible() {
        mCollectSmartRefresh.postDelayed({
            mCollectSmartRefresh.autoRefresh()
        },500)
    }

    override fun onError(errorMsg: String) {
        super.onError(errorMsg)
        mCollectSmartRefresh.finishRefresh()
    }

    override fun onCollectListResult(collectArticleList: CollectArticleListResp) {
        if (page == 1 && collectArticleList.datas.size<=0){
            mCollectAdapter.emptyView = emptyView(mCollectRl)
        }
        if (page == 1){
            mCollectData.clear()
            mCollectData.addAll(collectArticleList.datas)
            mCollectAdapter.setNewData(mCollectData)
            mCollectSmartRefresh.finishRefresh()
        }else{
            mCollectData.addAll(collectArticleList.datas)
            mCollectAdapter.addData(mCollectData)
            mCollectSmartRefresh.finishLoadMore()
        }

        page++
        if (page>collectArticleList.pageCount){
            mCollectSmartRefresh.setEnableLoadMore(false)
        }
    }

    override fun onUncollectResult(baseNoneResponseResult: BaseNoneResponseResult) {

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        mPresenter.getCollectListData(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getCollectListData(page)
    }


}