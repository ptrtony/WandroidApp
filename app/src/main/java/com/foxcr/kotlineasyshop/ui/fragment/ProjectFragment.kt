package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.HomeArticleProjectListAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleProjectListResp
import com.foxcr.kotlineasyshop.injection.component.DaggerProjectComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.ProjectPresenter
import com.foxcr.kotlineasyshop.presenter.view.ProjectView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

class ProjectFragment : BaseMvpLazyFragment<ProjectPresenter>(),ProjectView, OnRefreshListener,
    OnLoadMoreListener, OnLikeClickListener {
    private var projectDatas :  MutableList<HomeArticleProjectListResp.DatasBean> = mutableListOf()
    private val mAdapter: HomeArticleProjectListAdapter by lazy {
        HomeArticleProjectListAdapter(projectDatas)
    }

    private lateinit var mProjectSmartRefresh:SmartRefreshLayout
    private lateinit var mProjectRl:RecyclerView

    private var page = 1

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
            adapter = mAdapter
        }

        mAdapter.setOnLikeClickListener(this)

    }

    override fun onFragmentFirstVisible() {
        mProjectSmartRefresh.postDelayed({
            mProjectSmartRefresh.autoRefresh()
        },500)
        initLoveLayout()
    }

    override fun onNewProjectArticleResult(homeArticleProjectListResp: HomeArticleProjectListResp) {
        if (page == 1){
            projectDatas.clear()
            projectDatas.addAll(homeArticleProjectListResp.datas)
            mAdapter.setNewData(projectDatas)
            mProjectSmartRefresh.finishRefresh()
        }else{
            projectDatas.addAll(homeArticleProjectListResp.datas)
            mAdapter.addData(projectDatas)
            mProjectSmartRefresh.finishLoadMore()
        }
        page ++
        if (page>homeArticleProjectListResp.pageCount){
            mProjectSmartRefresh.setEnableLoadMore(false)
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

}