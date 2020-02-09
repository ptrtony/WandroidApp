package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.ProjectAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleProjectListResp
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleResp
import com.foxcr.kotlineasyshop.injection.component.DaggerProjectComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.ProjectPresenter
import com.foxcr.kotlineasyshop.presenter.view.ProjectView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

class ProjectFragment : BaseMvpLazyFragment<ProjectPresenter>(), ProjectView, OnRefreshListener,
    OnLoadMoreListener, OnLikeClickListener {
    private var projectDatas: MutableList<HomeArticleResp.DatasBean> = mutableListOf()
    private val mAdapter: ProjectAdapter by lazy {
        ProjectAdapter(projectDatas)
    }

    private lateinit var mProjectSmartRefresh: SmartRefreshLayout
    private lateinit var mProjectRl: RecyclerView

    private var page = 0

    override fun resLayoutId(): Int = R.layout.fragment_project
    override fun injectComponent() {
        DaggerProjectComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun initView(view: View) {
        mPresenter.mView = this
        mProjectSmartRefresh = view.findViewById(R.id.mProjectSmartRefresh)
        mProjectRl = view.findViewById(R.id.mProjectRl)

        mProjectSmartRefresh.apply {
            setEnableLoadMore(true)
            setEnableRefresh(true)
            setOnRefreshListener(this@ProjectFragment)
            setOnLoadMoreListener(this@ProjectFragment)
        }

        mProjectRl.apply {
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
            if (position<projectDatas.size)
            ARouter.getInstance()
                .build("/easyshop/web")
                .greenChannel()
                .withString("url", projectDatas[position].link)
                .navigation()
        }
        mAdapter.openLoadAnimation()

    }

    override fun onFragmentFirstVisible() {
        mProjectSmartRefresh.postDelayed({
            mProjectSmartRefresh.autoRefresh()
        }, 500)
        initLoveLayout()
    }

    override fun onNewProjectArticleResult(homeArticleResp: HomeArticleResp) {
        if (page == 0 && homeArticleResp.datas.size <= 0) {
            mAdapter.emptyView = emptyView(mProjectRl)
        }
        if (page == 0) {
            projectDatas.clear()
            projectDatas.addAll(homeArticleResp.datas)
            mAdapter.setNewData(projectDatas)
            mProjectSmartRefresh.finishRefresh()
        } else {
            projectDatas.addAll(homeArticleResp.datas)
            mAdapter.addData(projectDatas)
            mProjectSmartRefresh.finishLoadMore()
        }
        page++
        if (page >= homeArticleResp.pageCount) {
            mProjectSmartRefresh.setEnableLoadMore(false)
            if (mAdapter.footerLayoutCount<=0)
            mAdapter.addFooterView(footerView())
        }else{
            if (mAdapter.footerLayoutCount>0)
                mAdapter.removeAllFooterView()
        }
    }

    override fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("收藏成功")
    }

    override fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("取消收藏")
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        mProjectSmartRefresh.setEnableLoadMore(true)
        mPresenter.getNewProjectArticleList(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getNewProjectArticleList(page)
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

    override fun onError(errorMsg: String) {
        super.onError(errorMsg)
        mProjectSmartRefresh.finishRefresh()
    }

}