//package com.foxcr.kotlineasyshop.ui.fragment
//
//import android.view.View
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.foxcr.base.data.protocal.BaseNoneResponseResult
//import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
//import com.foxcr.base.utils.DisplayUtils
//import com.foxcr.base.utils.ToastUtils
//import com.foxcr.base.widgets.OnLikeClickListener
//import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener
//import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener.Companion.NEWPROJECT
//import com.foxcr.base.widgets.RecycleViewDivider
//import com.foxcr.kotlineasyshop.R
//import com.foxcr.kotlineasyshop.adapter.HomeArticleProjectListAdapter
//import com.foxcr.kotlineasyshop.data.protocal.HomeArticleProjectListResp
//import com.foxcr.kotlineasyshop.injection.component.DaggerNewProjectArticleComponent
//import com.foxcr.kotlineasyshop.injection.module.HomeModule
//import com.foxcr.kotlineasyshop.presenter.NewProjectArticlePresenter
//import com.foxcr.kotlineasyshop.presenter.view.NewProjectArticleView
//
//class NewProjectArticleFragment : BaseMvpLazyFragment<NewProjectArticlePresenter>(),NewProjectArticleView,
//    OnLikeClickListener {
//    private var projectDatas :  MutableList<HomeArticleProjectListResp.DatasBean> = mutableListOf()
//    private val mAdapter: HomeArticleProjectListAdapter by lazy {
//        HomeArticleProjectListAdapter(projectDatas)
//    }
//    private var page : Int = 1
//    override fun resLayoutId(): Int = R.layout.fragment_new_project_article
//
//    override fun injectComponent() {
//        DaggerNewProjectArticleComponent.builder().activityComponent(activityComponent)
//            .homeModule(HomeModule()).build().inject(this)
//    }
//
//    private lateinit var mNewProjectArticleRv:RecyclerView
//
//    override fun initView(view: View) {
//        mNewProjectArticleRv = view.findViewById(R.id.mNewProjectArticleRv)
//        mPresenter.mView = this
//        //首页最新项目列表
//        mNewProjectArticleRv.apply {
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//            addItemDecoration(
//                RecycleViewDivider(
//                    context,
//                    LinearLayoutManager.HORIZONTAL,
//                    DisplayUtils.dp2px(1f),
//                    resources.getColor(R.color.common_divider),
//                    DisplayUtils.dp2px(15f)
//                )
//            )
//            adapter = mAdapter
//
//        }
//        mAdapter.setOnLikeClickListener(this)
//    }
//
//    fun getNewProjectArticleList(page:Int){
//        this.page = page
//        mPresenter.getNewProjectArticleList(page)
//    }
//
//    override fun onNewProjectArticleResult(homeArticleProjectListResp: HomeArticleProjectListResp) {
//        if (page == 1) {
//            projectDatas.clear()
//            if (homeArticleProjectListResp.datas.size <= 0) return
//            projectDatas.addAll(homeArticleProjectListResp.datas)
//            mAdapter.setNewData(projectDatas)
//            onRefreshOrLoadMoreListener?.finishRefresh()
//
//        } else {
//            projectDatas.addAll(homeArticleProjectListResp.datas)
//            mAdapter.addData(projectDatas)
//        }
//        page ++
//        onRefreshOrLoadMoreListener?.loadPage(page,NEWPROJECT)
//        if (page <= homeArticleProjectListResp.pageCount) {
//            onRefreshOrLoadMoreListener?.finishLoadMore()
//        } else {
//            onRefreshOrLoadMoreListener?.apply {
//                enableLoadMore(false)
//                finishLoadMore()
//            }
//        }
//    }
//
//    override fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
//        ToastUtils.showToast("收藏成功")
//    }
//
//    override fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
//        ToastUtils.showToast("取消收藏")
//    }
//
//    fun setOnRefreshOrLoadMoreListener(onRefreshOrLoadMoreListener: OnRefreshOrLoadMoreListener){
//        this.onRefreshOrLoadMoreListener = onRefreshOrLoadMoreListener
//    }
//
//    private var onRefreshOrLoadMoreListener: OnRefreshOrLoadMoreListener?=null
//
//    override fun onLikeInNetClick(view: View, id: Int) {
//        mLoveView.addLoveView(view)
//        mPresenter.collectInStandArticle(id)
//    }
//
//    override fun onLikeOutNetClick(view: View, title: String, author: String, link: String) {
//        mLoveView.addLoveView(view)
//        mPresenter.collectOutStandArticle(title,author, link)
//    }
//
//    override fun cancelCollectClick(id: Int, originId: Int) {
//        mPresenter.uncollectArticle(id, originId)
//    }
//
//    override fun onFragmentFirstVisible() {
//        initLoveLayout()
//    }
//}