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
import com.foxcr.kotlineasyshop.adapter.HomeSquareUserArticleAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeSquareUserArticleListResp
import com.foxcr.kotlineasyshop.injection.component.DaggerSquareComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.SquarePresenter
import com.foxcr.kotlineasyshop.presenter.view.SquareView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

class SquareFragment : BaseMvpLazyFragment<SquarePresenter>(), OnLoadMoreListener,
    OnRefreshListener, SquareView,
    OnLikeClickListener {
    private var squareUserArticleDatas: MutableList<HomeSquareUserArticleListResp.DatasBean> =
        mutableListOf()
    private val homeSquareUserArticleAdapter: HomeSquareUserArticleAdapter by lazy {
        HomeSquareUserArticleAdapter(squareUserArticleDatas)
    }
    private var page: Int = 0
    private lateinit var mSquareSrl: SmartRefreshLayout
    private lateinit var mSquareRv: RecyclerView

    override fun resLayoutId(): Int = R.layout.fragment_square

    override fun injectComponent() {
        DaggerSquareComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun initView(view: View) {
        mPresenter.mView = this
        mSquareSrl = view.findViewById(R.id.mSquareSrl)
        mSquareRv = view.findViewById(R.id.mSquareRv)
        mSquareSrl.apply {
            setOnLoadMoreListener(this@SquareFragment)
            setOnRefreshListener(this@SquareFragment)
        }

        mSquareRv.apply {
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
            adapter = homeSquareUserArticleAdapter
        }
        homeSquareUserArticleAdapter.setOnLikeClickListener(this)
        homeSquareUserArticleAdapter.setOnItemClickListener { adapter, view, position ->
            ARouter.getInstance()
                .build("/easyshop/web")
                .greenChannel()
                .withString("url", squareUserArticleDatas[position].link)
                .navigation()
        }
        homeSquareUserArticleAdapter.openLoadAnimation()
        initLoveLayout()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getSquareArticleUserList(page)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        mSquareSrl.setEnableLoadMore(true)
        mPresenter.getSquareArticleUserList(page)
    }

    override fun onHomeSquareUserArticleList(homeSquareUserArticleListResp: HomeSquareUserArticleListResp) {
        if (page == 0 && homeSquareUserArticleListResp.datas.size <= 0) {
            homeSquareUserArticleAdapter.emptyView = emptyView(mSquareRv)
        }
        if (page == 0) {
            squareUserArticleDatas.clear()
            squareUserArticleDatas.addAll(homeSquareUserArticleListResp.datas)
            homeSquareUserArticleAdapter.setNewData(squareUserArticleDatas)
            mSquareSrl.finishRefresh()
        } else {
            squareUserArticleDatas.addAll(homeSquareUserArticleListResp.datas)
            homeSquareUserArticleAdapter.addData(squareUserArticleDatas)
            mSquareSrl.finishLoadMore()
        }
        page = homeSquareUserArticleListResp.curPage
        if (page >= homeSquareUserArticleListResp.pageCount) {
            mSquareSrl.setEnableLoadMore(false)
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
        mPresenter.collectInStandArticle(id)
    }

    override fun onLikeOutNetClick(view: View, title: String, author: String, link: String) {
        mLoveView.addLoveView(view)
        mPresenter.collectOutStandArticle(title, author, link)
    }

    override fun cancelCollectClick(id: Int, originId: Int) {
        mPresenter.uncollectArticle(id, originId)
    }

    override fun onFragmentFirstVisible() {
        mSquareSrl.postDelayed({
            mSquareSrl.autoRefresh()
        }, 500)
    }
}