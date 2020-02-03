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
import kotlinx.android.synthetic.main.fragment_square.*

class SquareFragment : BaseMvpLazyFragment<SquarePresenter>(), OnLoadMoreListener, OnRefreshListener,SquareView,
    OnLikeClickListener {
    private var squareUserArticleDatas:MutableList<HomeSquareUserArticleListResp.DatasBean> = mutableListOf()
    private val homeSquareUserArticleAdapter:HomeSquareUserArticleAdapter by lazy {
        HomeSquareUserArticleAdapter(squareUserArticleDatas)
    }
    private var page:Int = 1
    private lateinit var mSquareSrl:SmartRefreshLayout
    private lateinit var mSquareRv:RecyclerView

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
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(
                RecycleViewDivider(context,LinearLayoutManager.HORIZONTAL,
                    DisplayUtils.dp2px(1f),resources.getColor(R.color.common_divider), DisplayUtils.dp2px(15f))
            )
            adapter = homeSquareUserArticleAdapter
        }
        homeSquareUserArticleAdapter.setOnLikeClickListener(this)
        initLoveLayout()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getSquareArticleUserList(page)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        mPresenter.getSquareArticleUserList(page)
    }

    override fun onHomeSquareUserArticleList(homeSquareUserArticleListResp: HomeSquareUserArticleListResp) {
        squareUserArticleDatas.clear()
        squareUserArticleDatas.addAll(homeSquareUserArticleListResp.datas)
        if (page == 1){
            homeSquareUserArticleAdapter.setNewData(squareUserArticleDatas)
            mSquareSrl.finishRefresh()
            mSquareSrl.setEnableRefresh(false)
        }else{
            homeSquareUserArticleAdapter.addData(squareUserArticleDatas)
            mSquareSrl.finishLoadMore()
        }
        page = homeSquareUserArticleListResp.curPage
        if (page>=homeSquareUserArticleListResp.pageCount){
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
        val locations = IntArray(2)
        view.getLocationOnScreen(locations)
        mLoveView.addLoveView(view,locations)
        mPresenter.collectInStandArticle(id)
    }

    override fun onLikeOutNetClick(view: View, title: String, author: String, link: String) {
        val locations = IntArray(2)
        view.getLocationOnScreen(locations)
        mLoveView.addLoveView(view,locations)
        mPresenter.collectOutStandArticle(title, author, link)
    }

    override fun cancelCollectClick(id: Int, originId: Int) {
        mPresenter.uncollectArticle(id, originId)
    }

    override fun onFragmentFirstVisible() {
        mSquareSrl.autoRefresh()
    }
}