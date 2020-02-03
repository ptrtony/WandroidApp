package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.CategorySystemOneAdapter
import com.foxcr.kotlineasyshop.adapter.CategorySystemTwoAdapter
import com.foxcr.kotlineasyshop.adapter.HomeKnowledgeSystemListAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeKnowledgeSystemListResp
import com.foxcr.kotlineasyshop.data.protocal.HomeKnowledgeSystemResp
import com.foxcr.kotlineasyshop.injection.component.DaggerSystemComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.KnowledgeSystemPresenter
import com.foxcr.kotlineasyshop.presenter.view.KnowledgeSystemView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_system.*

class SystemFragment : BaseMvpLazyFragment<KnowledgeSystemPresenter>(), KnowledgeSystemView,
    OnRefreshListener, OnLoadMoreListener, OnLikeClickListener,
    CategorySystemOneAdapter.OnCategorySystemOneClickListener,
    CategorySystemTwoAdapter.OnCategoryTwoSystemClickListener {
    private val mCategoryOneData: MutableList<HomeKnowledgeSystemResp> = mutableListOf()
    private val mCategoryTwoData: MutableList<HomeKnowledgeSystemResp.ChildrenBean> =
        mutableListOf()
    private val mSystemData: MutableList<HomeKnowledgeSystemListResp.DatasBean> = mutableListOf()
    private val mCategoryOneAdapter: CategorySystemOneAdapter by lazy {
        CategorySystemOneAdapter(mCategoryOneData)
    }
    private val mCategoryTwoAdapter: CategorySystemTwoAdapter by lazy {
        CategorySystemTwoAdapter(mCategoryTwoData)
    }
    private val mSystemAdapter: HomeKnowledgeSystemListAdapter by lazy {
        HomeKnowledgeSystemListAdapter(mSystemData)
    }
    private lateinit var mCategoryOneRl:RecyclerView
    private lateinit var mCategoryTwoRl:RecyclerView
    private lateinit var mKnowledgeSystemListRl:RecyclerView
    private lateinit var mSystemSmartRefresh:SmartRefreshLayout
    private var page: Int = 1
    private var cid = 0
    override fun resLayoutId(): Int = R.layout.fragment_system

    override fun injectComponent() {
        DaggerSystemComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    companion object {
        // 1 默认刷新  2 分类一级  3  分类二级
        var REFRESH_TYPE = 1
    }

    override fun initView(view: View) {
        mCategoryOneRl = view.findViewById(R.id.mCategoryOneRl)
        mCategoryTwoRl = view.findViewById(R.id.mCategoryTwoRl)
        mKnowledgeSystemListRl = view.findViewById(R.id.mKnowledgeSystemListRl)
        mSystemSmartRefresh = view.findViewById(R.id.mSystemSmartRefresh)
        REFRESH_TYPE = 1
        mPresenter.mView = this
        mPresenter.getKnowledgeSystemData()
        mCategoryOneRl.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = mCategoryOneAdapter
        }

        mCategoryTwoRl.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mCategoryTwoAdapter
        }

        mKnowledgeSystemListRl.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = mSystemAdapter
        }

        mSystemSmartRefresh.apply {
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setOnRefreshListener(this@SystemFragment)
            setOnLoadMoreListener(this@SystemFragment)
            autoRefresh()
        }



        mSystemAdapter.setOnLikeClickListener(this)
        mCategoryOneAdapter.setOnCategorySystemOneClickListener(this)
        mCategoryTwoAdapter.setOnCategoryTwoSystemClickListener(this)
        initLoveLayout()
    }

    override fun onKnowledgeSystemResult(knowledgeSystemData: List<HomeKnowledgeSystemResp>) {
        mCategoryOneData.clear()
        knowledgeSystemData[0].userControlSetTop = true
        mCategoryOneData.addAll(knowledgeSystemData)
        mCategoryOneAdapter.setNewData(mCategoryOneData)
        mCategoryTwoData.clear()
        knowledgeSystemData[0].children[0].userControlSetTop = true
        mCategoryTwoData.addAll(knowledgeSystemData[0].children)
        mCategoryTwoAdapter.setNewData(mCategoryTwoData)
        cid = knowledgeSystemData[0].children[0].id
    }

    override fun onKnowledgeSystemListResult(knowledgeSystemListResp: HomeKnowledgeSystemListResp) {
        mSystemData.clear()
        if (page == 0) {
            mSystemData.addAll(knowledgeSystemListResp.datas)
            mSystemAdapter.setNewData(mSystemData)
            mSystemSmartRefresh.finishRefresh()
        } else {
            mSystemData.addAll(knowledgeSystemListResp.datas)
            mSystemAdapter.addData(mSystemData)
            mSystemSmartRefresh.finishLoadMore()
        }
        page++
        if (page > knowledgeSystemListResp.pageCount) {
            mSystemSmartRefresh.setEnableLoadMore(false)
        }
    }

    override fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("收藏成功")
    }

    override fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("取消收藏")
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        mSystemSmartRefresh.setEnableLoadMore(true)
        when (REFRESH_TYPE) {
            1 -> mPresenter.getKnowledgeSystemData()
            2, 3 -> mPresenter.getKnowledgeSystemListData(page, cid)
        }

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getKnowledgeSystemListData(page, cid)
    }

    override fun onLikeInNetClick(view: View, id: Int) {
        val locations = IntArray(2)
        view.getLocationOnScreen(locations)
        mLoveView.addLoveView(view, locations)
        mPresenter.collectInStandArticle(id)
    }

    override fun onLikeOutNetClick(view: View, title: String, author: String, link: String) {
        val locations = IntArray(2)
        view.getLocationOnScreen(locations)
        mLoveView.addLoveView(view, locations)
        mPresenter.collectOutStandArticle(title, author, link)
    }

    override fun cancelCollectClick(id: Int, originId: Int) {
        mPresenter.uncollectArticle(id, originId)
    }

    override fun onCategorySystemOneClick(
        view: View,
        position: Int,
        item: HomeKnowledgeSystemResp
    ) {
        mCategoryTwoData.clear()
        mCategoryTwoData.addAll(mCategoryOneData[position].children)
        mCategoryTwoData.forEach { childrenBean: HomeKnowledgeSystemResp.ChildrenBean ->
            childrenBean.userControlSetTop = false
        }
        mCategoryOneData[position].children[0].userControlSetTop = true
        mCategoryTwoAdapter.setNewData(mCategoryTwoData)
        mCategoryOneData.forEach { homeKnowledgeSystemResp: HomeKnowledgeSystemResp ->
            homeKnowledgeSystemResp.userControlSetTop = false
        }
        mCategoryOneData[position].userControlSetTop = true
        cid = mCategoryTwoData[0].id
        mCategoryOneAdapter.notifyDataSetChanged()
        REFRESH_TYPE = 2
        mSystemSmartRefresh.autoRefresh()
    }

    override fun onCategoryTwoClick(
        view: View,
        position: Int,
        item: HomeKnowledgeSystemResp.ChildrenBean
    ) {
        REFRESH_TYPE = 3
        mCategoryTwoData.forEach { childrenBean: HomeKnowledgeSystemResp.ChildrenBean ->
            childrenBean.userControlSetTop = false
        }
        mCategoryTwoData[position].userControlSetTop = true
        mCategoryTwoAdapter.notifyDataSetChanged()
        cid = mCategoryTwoData[position].id
        mSystemSmartRefresh.autoRefresh()
    }

    override fun onFragmentFirstVisible() {

    }


}