package com.foxcr.kotlineasyshop.ui.activity

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.ext.hideKeyboard
import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.HeaderBar
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.base.widgets.statusbar.StatusBarUtils
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.SearchAdapter
import com.foxcr.kotlineasyshop.data.protocal.SearchArticleResp
import com.foxcr.kotlineasyshop.injection.component.DaggerSearchComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.SearchPresenter
import com.foxcr.kotlineasyshop.presenter.view.SearchView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

@Route(path = "/easyshop/search")
class SearchActivity : BaseMvpActivity<SearchPresenter>(), SearchView, OnLoadMoreListener,
    OnRefreshListener, OnLikeClickListener {
    private var mSearchData: MutableList<SearchArticleResp.DatasBean> = mutableListOf()
    private val mSearchAdapter: SearchAdapter by lazy {
        SearchAdapter(mSearchData)
    }

    private lateinit var mSearchSmartRefresh: SmartRefreshLayout
    private lateinit var mSearchRl: RecyclerView
    private lateinit var mSearchHeadBar: HeaderBar
    private lateinit var mSearchEtn: EditText
    private var page = 1
    private var keyStr = ""
    override fun initActivityComponent() {
        DaggerSearchComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun resLayoutId(): Int = R.layout.activity_search

    override fun initView() {
        initLoveLayout()
        StatusBarUtils.setImmersiveStatusBar(this, false)
        StatusBarUtils.setStatusBarColor(
            this,
            resources.getColor(com.foxcr.base.R.color.common_blue)
        )
        mSearchSmartRefresh = findViewById(R.id.mSearchSmartRefresh)
        mSearchRl = findViewById(R.id.mSearchRl)
        mSearchHeadBar = findViewById(R.id.mSearchHeadBar)
        mSearchEtn = findViewById(R.id.mSearchEtn)
        mSearchHeadBar.onBackClickListener { finish() }

        mSearchEtn.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                keyStr = mSearchEtn.text.toString().trim()
                page = 1
                mSearchEtn.hideKeyboard()
                mSearchSmartRefresh.autoRefresh()
            }
            return@setOnEditorActionListener true
        }
        mPresenter.mView = this
        mSearchSmartRefresh.apply {
            setOnLoadMoreListener(this@SearchActivity)
            setOnRefreshListener(this@SearchActivity)
        }

        mSearchRl.apply {
            layoutManager =
                LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                RecycleViewDivider(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    DisplayUtils.dp2px(1f),
                    resources.getColor(R.color.common_divider),
                    DisplayUtils.dp2px(15f)
                )
            )
            adapter = mSearchAdapter
        }

        mSearchAdapter.setOnLikeClickListener(this)
    }

    override fun onSearchResult(searchArticleResp: SearchArticleResp) {
        if (page == 1) {
            mSearchData.clear()
            mSearchData.addAll(searchArticleResp.datas)
            mSearchAdapter.setNewData(mSearchData)
            mSearchSmartRefresh.finishRefresh()
        } else {
            mSearchData.addAll(searchArticleResp.datas)
            mSearchAdapter.addData(mSearchData)
            mSearchSmartRefresh.finishLoadMore()
        }
        page++
        if (page >= searchArticleResp.pageCount) {
            mSearchSmartRefresh.setEnableLoadMore(false)
            if (mSearchAdapter.footerLayoutCount <= 0)
                mSearchAdapter.addFooterView(View.inflate(this, R.layout.item_not_more_data, null))
        } else {
            if (mSearchAdapter.footerLayoutCount > 0)
                mSearchAdapter.removeAllFooterView()
        }
    }

    override fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("收藏成功")
    }

    override fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("取消收藏")
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getSearchListData(page, keyStr)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mSearchSmartRefresh.setEnableLoadMore(true)
        mPresenter.getSearchListData(page, keyStr)
    }

    override fun onLikeInNetClick(view: View, id: Int) {
        mLoveView.addLoveView(view)
        mPresenter.collectInStandArticle(id)
    }

    override fun onLikeOutNetClick(view: View, title: String, author: String, link: String) {
        mLoveView.addLoveView(view)
        mPresenter.collectOutStandArticle(title, author, link)
    }

    override fun cancelCollectClick(id: Int, originId: Int) {
        mPresenter.uncollectArticle(id, originId)
    }
}