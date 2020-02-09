package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.ext.hideKeyboard
import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.WxArticleAdapter
import com.foxcr.kotlineasyshop.data.protocal.WxArticleListResp
import com.foxcr.kotlineasyshop.injection.component.DaggerWxArticleListComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.WxArticleListPresenter
import com.foxcr.kotlineasyshop.presenter.view.WxArticleListView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.item_home_article_or_project.*

class WxArticleFragment : BaseMvpLazyFragment<WxArticleListPresenter>(), WxArticleListView,
    OnRefreshListener,
    OnLoadMoreListener, OnLikeClickListener {
    private var mData: MutableList<WxArticleListResp.DatasBean> = mutableListOf()
    private val mAdapter: WxArticleAdapter by lazy {
        WxArticleAdapter(mData)
    }

    companion object {
        //1  普通获取公众号文章列表数据
        //2  通过搜索公众号关键字获取文章列表数据
        var TYPE = 1
    }

    private lateinit var mWxArticleSmartRefresh: SmartRefreshLayout
    private lateinit var mWxArticleRl: RecyclerView
    private var page: Int = 0
    private var isShowLoading: Boolean = false
    private var wxArticleId = -1
    private var key = ""
    override fun onFragmentFirstVisible() {
        initLoveLayout()
    }

    override fun resLayoutId(): Int = R.layout.fragment_wx_article


    override fun injectComponent() {
        DaggerWxArticleListComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }


    fun getWxArticleListData(id: Int, key: String, isShowLoading: Boolean) {
        this.isShowLoading = isShowLoading
        this.wxArticleId = id
        this.key = key
        page = 0
        if (TYPE == 1) {
            if (isShowLoading) {
                mPresenter.getWxArticleListData(id, page, isShowLoading)
            } else {
                mWxArticleSmartRefresh.autoRefresh()
            }
        } else {
            if (key.isNotEmpty()) {
                mWxArticleSmartRefresh.autoRefresh()
            }
        }
    }

    override fun initView(view: View) {
        mPresenter.mView = this
        mWxArticleSmartRefresh = view.findViewById(R.id.mWxArticleSmartRefresh)
        mWxArticleRl = view.findViewById(R.id.mWxArticleRl)

        mWxArticleSmartRefresh.apply {
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setOnRefreshListener(this@WxArticleFragment)
            setOnLoadMoreListener(this@WxArticleFragment)
        }

        mWxArticleRl.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                RecycleViewDivider(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    DisplayUtils.dp2px(1f),
                    resources.getColor(R.color.common_divider),
                    DisplayUtils.dp2px(15f)
                )
            )
            adapter = mAdapter
        }

        mAdapter.setOnLikeClickListener(this)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (position < mData.size)
                ARouter.getInstance()
                    .build("/easyshop/web")
                    .withString("url", mData[position].link)
                    .greenChannel()
                    .navigation()
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        mWxArticleSmartRefresh.setEnableLoadMore(true)
        if (TYPE == 1) {
            mPresenter.getWxArticleListData(wxArticleId, page, false)
        } else {
            if (key.isNotEmpty()) {
                mPresenter.searchWxArticleListData(wxArticleId, page, key)
            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        if (TYPE == 1) {
            mPresenter.getWxArticleListData(wxArticleId, page, false)
        } else {
            if (key.isNotEmpty()) {
                mPresenter.searchWxArticleListData(wxArticleId, page, key)
            }
        }
    }

    override fun onWxArticleListResult(wxArticleListResp: WxArticleListResp) {
        mWxArticleSmartRefresh.finishRefresh()
        if (wxArticleListResp.datas.size <= 0 && page == 1) {
            mAdapter.emptyView = emptyView(mWxArticleRl)
        }
        if (page == 0) {
            mData.clear()
            mData.addAll(wxArticleListResp.datas)
            mAdapter.setNewData(mData)
        } else {
            mData.addAll(wxArticleListResp.datas)
            mAdapter.addData(mData)
            mWxArticleSmartRefresh.finishLoadMore()
        }

        page++
        if (page >= wxArticleListResp.pageCount) {
            mWxArticleSmartRefresh.setEnableLoadMore(false)
            if (mAdapter.footerLayoutCount <= 0)
                mAdapter.addFooterView(footerView())
        } else {
            if (mAdapter.footerLayoutCount > 0)
                mAdapter.removeAllFooterView()
        }
    }

    override fun onSearchWxArticleListResult(wxArticleListResp: WxArticleListResp) {
        mWxArticleSmartRefresh.finishRefresh()
        if (page == 0 && wxArticleListResp.datas.size <= 0) {
            mAdapter.emptyView = emptyView(mWxArticleRl)
        }
        if (page == 0) {
            mData.clear()
            mData.addAll(wxArticleListResp.datas)
            mAdapter.setNewData(mData)
        } else {
            mData.addAll(wxArticleListResp.datas)
            mAdapter.addData(mData)
            mWxArticleSmartRefresh.finishLoadMore()
        }

        page++
        if (page > wxArticleListResp.pageCount) {
            mWxArticleSmartRefresh.setEnableLoadMore(false)
            mAdapter.addFooterView(footerView())
        } else {
            if (mAdapter.footerLayoutCount > 0)
                mAdapter.removeAllFooterView()
        }
    }

    override fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("收藏成功")
    }

    override fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("取消收藏")
    }

    override fun onLikeInNetClick(view: View, id: Int) {
        mLoveView.addLoveView(view)
        mWxArticleSmartRefresh.hideKeyboard()
        mPresenter.collectInStandArticle(id)
    }

    override fun onLikeOutNetClick(view: View, title: String, author: String, link: String) {
        mLoveView.addLoveView(view)
        mWxArticleSmartRefresh.hideKeyboard()
        mPresenter.collectOutStandArticle(title, author, link)
    }

    override fun cancelCollectClick(id: Int, originId: Int) {
        mPresenter.uncollectArticle(id, originId)
    }

    override fun onError(errorMsg: String) {
        super.onError(errorMsg)
        mWxArticleSmartRefresh.finishRefresh()
    }
}