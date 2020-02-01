package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener.Companion.NEWPROJECT
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.HomeArticleProjectListAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleProjectListResp
import com.foxcr.kotlineasyshop.injection.component.DaggerNewProjectArticleComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.NewProjectArticlePresenter
import com.foxcr.kotlineasyshop.presenter.view.NewProjectArticleView
import kotlinx.android.synthetic.main.fragment_new_project_article.*

class NewProjectArticleFragment : BaseMvpFragment<NewProjectArticlePresenter>(),NewProjectArticleView,
    HomeArticleProjectListAdapter.OnLikeClickListener {
    private var projectDatas :  MutableList<HomeArticleProjectListResp.DatasBean> = mutableListOf()
    private val mAdapter: HomeArticleProjectListAdapter by lazy {
        HomeArticleProjectListAdapter(projectDatas)
    }
    private var page : Int = 0
    override fun resLayoutId(): Int = R.layout.fragment_new_project_article

    override fun injectComponent() {
        DaggerNewProjectArticleComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun initView(view: View) {
        mPresenter.mView = this
        //首页最新项目列表
        mNewProjectArticleRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mNewProjectArticleRv.addItemDecoration(
            RecycleViewDivider(
                context,
                LinearLayoutManager.HORIZONTAL,
                DisplayUtils.dp2px(1f),
                resources.getColor(R.color.common_divider),
                DisplayUtils.dp2px(15f)
            )
        )
        mNewProjectArticleRv.adapter = mAdapter
        mAdapter.setOnLikeClickListener(this)
    }

    fun getNewProjectArticleList(page:Int){
        this.page = page
        mPresenter.getNewProjectArticleList(page)
    }

    override fun onNewProjectArticleResult(homeArticleProjectListResp: HomeArticleProjectListResp) {
        if (page == 0) {
            projectDatas.clear()
            if (homeArticleProjectListResp.datas.size <= 0) return
            projectDatas.addAll(homeArticleProjectListResp.datas)
            mAdapter.setNewData(projectDatas)
            if (onRefreshOrLoadMoreListener!=null){
                onRefreshOrLoadMoreListener?.finishRefresh()
            }

        } else {
            projectDatas.addAll(homeArticleProjectListResp.datas)
            mAdapter.addData(projectDatas)
        }
        page = homeArticleProjectListResp.curPage
        if (onRefreshOrLoadMoreListener!=null){
            onRefreshOrLoadMoreListener?.loadPage(page,NEWPROJECT)
        }
        if (page < homeArticleProjectListResp.pageCount) {
            if (onRefreshOrLoadMoreListener!=null){
                onRefreshOrLoadMoreListener?.finishLoadMore()
            }
        } else {
            if (onRefreshOrLoadMoreListener!=null){
                onRefreshOrLoadMoreListener?.enableLoadMore(false)
                onRefreshOrLoadMoreListener?.finishLoadMore()
            }
        }
    }

    fun setOnRefreshOrLoadMoreListener(onRefreshOrLoadMoreListener: OnRefreshOrLoadMoreListener){
        this.onRefreshOrLoadMoreListener = onRefreshOrLoadMoreListener
    }

    private var onRefreshOrLoadMoreListener: OnRefreshOrLoadMoreListener?=null

    override fun onLikeInNetClick(view: View, id: Int) {

    }

    override fun onLikeOutNetClick(view: View, title: String, author: String, link: String) {

    }

    override fun cancelCollectClick(id: Int, originId: Int) {

    }
}