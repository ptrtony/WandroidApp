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
import com.foxcr.kotlineasyshop.adapter.ProjectCagetoryListAdapter
import com.foxcr.kotlineasyshop.adapter.ProjectCategoryOneAdapter
import com.foxcr.kotlineasyshop.data.protocal.ProjectCategoryListResp
import com.foxcr.kotlineasyshop.data.protocal.ProjectCategoryTreeResp
import com.foxcr.kotlineasyshop.injection.component.DaggerProjectCategoryComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.ProjectCategoryPresenter
import com.foxcr.kotlineasyshop.presenter.view.ProjectCategoryView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProjectCategoryFragment : BaseMvpLazyFragment<ProjectCategoryPresenter>(),
    ProjectCategoryView, OnRefreshListener, OnLoadMoreListener,
    ProjectCategoryOneAdapter.OnProjectCategoryOneClickListener, OnLikeClickListener {
    private var projectCategoryOneData: MutableList<ProjectCategoryTreeResp> = mutableListOf()
    private val mOneAdapter: ProjectCategoryOneAdapter by lazy {
        ProjectCategoryOneAdapter(projectCategoryOneData)
    }

    private var projectCategoryListData: MutableList<ProjectCategoryListResp.DatasBean> =
        mutableListOf()
    private val mTwoAdapter: ProjectCagetoryListAdapter by lazy {
        ProjectCagetoryListAdapter(projectCategoryListData)
    }
    private lateinit var mProjectCategorySmartRefresh: SmartRefreshLayout
    private lateinit var mCategoryOneRl: RecyclerView
    private lateinit var mCategoryTwoRl: RecyclerView

    private var page = 1
    private var cid = 0
    private var isCurrentLoad = true
    override fun resLayoutId(): Int = R.layout.fragment_project_category

    override fun injectComponent() {
        DaggerProjectCategoryComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun initView(view: View) {
        mPresenter.mView = this
        mProjectCategorySmartRefresh = view.findViewById(R.id.mProjectCategorySmartRefresh)
        mCategoryOneRl = view.findViewById(R.id.mCategoryOneRl)
        mCategoryTwoRl = view.findViewById(R.id.mCategoryTwoRl)

        mProjectCategorySmartRefresh.apply {
            setEnableLoadMore(true)
            setEnableRefresh(true)
            setOnRefreshListener(this@ProjectCategoryFragment)
            setOnLoadMoreListener(this@ProjectCategoryFragment)
        }

        mCategoryOneRl.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mOneAdapter
        }

        mCategoryTwoRl.apply {
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

            adapter = mTwoAdapter
        }

        mOneAdapter.setProjectCategoryClickListener(this)
        mTwoAdapter.setOnLikeClickListener(this)

        mTwoAdapter.setOnItemClickListener { adapter, view, position ->
            ARouter.getInstance()
                .build("/easyshop/web")
                .withString("url", projectCategoryListData[position].link)
                .greenChannel()
                .navigation()
        }

        mTwoAdapter.openLoadAnimation()


    }

    override fun onFragmentFirstVisible() {
        initLoveLayout()
        mProjectCategorySmartRefresh.postDelayed({
            mProjectCategorySmartRefresh.autoRefresh()
        }, 500)
    }

    override fun onProjectCategoryTreeResult(projectCategoryTreeDatas: List<ProjectCategoryTreeResp>) {
        cid = projectCategoryTreeDatas[0].id
        projectCategoryOneData.clear()
        projectCategoryOneData.addAll(projectCategoryTreeDatas)
        projectCategoryOneData[0].isSelect = true
        mOneAdapter.setNewData(projectCategoryOneData)
    }

    override fun onProjectCategoryListResult(projectCategoryListResp: ProjectCategoryListResp) {
        if (page == 1 && projectCategoryListResp.datas.size <= 0) {
            mTwoAdapter.emptyView = emptyView(mCategoryTwoRl)
        }
        if (page == 1) {
            projectCategoryListData.clear()
            projectCategoryListData.addAll(projectCategoryListResp.datas)
            mTwoAdapter.setNewData(projectCategoryListData)
            mProjectCategorySmartRefresh.finishRefresh()
        } else {
            projectCategoryListData.addAll(projectCategoryListResp.datas)
            mTwoAdapter.addData(projectCategoryListData)
            mProjectCategorySmartRefresh.finishLoadMore()
        }

        page++
        if (page > projectCategoryListResp.pageCount) {
            mProjectCategorySmartRefresh.setEnableLoadMore(false)
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
        mProjectCategorySmartRefresh.setEnableLoadMore(true)
        if (isCurrentLoad) {
            GlobalScope.launch(Dispatchers.Main) {
                mPresenter.getProjectCategoryTreeData()
                mPresenter.getProjectCategoryListData(page, cid)
            }
        } else {
            mPresenter.getProjectCategoryListData(page, cid)
        }

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getProjectCategoryListData(page, cid)
    }

    override fun onCategoryTwoClick(view: View, position: Int, item: ProjectCategoryTreeResp) {
        cid = item.id
        projectCategoryOneData.forEach {
            it.isSelect = false
        }
        isCurrentLoad = false
        projectCategoryOneData[position].isSelect = true
        mOneAdapter.notifyDataSetChanged()
        mProjectCategorySmartRefresh.autoRefresh()
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
        mProjectCategorySmartRefresh.finishRefresh()
    }

}