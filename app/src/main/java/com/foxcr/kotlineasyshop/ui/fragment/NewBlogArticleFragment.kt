package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener.Companion.NEWBLOGTYPE
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.HomeArticleListAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleListResp
import com.foxcr.kotlineasyshop.data.protocal.NoResponseResult
import com.foxcr.kotlineasyshop.injection.component.DaggerNewBlogArticleComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.NewBlogArticlePresenter
import com.foxcr.kotlineasyshop.presenter.view.NewBlogArticleView
import kotlinx.android.synthetic.main.fragment_new_blog_article.*

class NewBlogArticleFragment : BaseMvpFragment<NewBlogArticlePresenter>(), NewBlogArticleView,
    OnLikeClickListener {
    private var articleDatas: MutableList<HomeArticleListResp.DatasBean> = mutableListOf()
    private val mAdapter: HomeArticleListAdapter by lazy {
        HomeArticleListAdapter(articleDatas)
    }
    private var page: Int = 0

    override fun resLayoutId(): Int = R.layout.fragment_new_blog_article

    override fun injectComponent() {
        DaggerNewBlogArticleComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun initView(view: View) {
        mPresenter.mView = this
        //首页最新博文列表
        mNewBlogArticleRv.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mNewBlogArticleRv.addItemDecoration(
            RecycleViewDivider(
                context,
                LinearLayoutManager.HORIZONTAL,
                DisplayUtils.dp2px(1f),
                resources.getColor(R.color.common_divider),
                DisplayUtils.dp2px(15f)
            )
        )
        mNewBlogArticleRv.adapter = mAdapter
        mAdapter.setOnLikeClickListener(this)

        initLoveLayout()

    }


    fun getNewBlogArticleList(page: Int) {
        this.page = page
        mPresenter.getNewBlogArticleList(page)
    }

    override fun onNewBlogArticleListResult(homeArticleListResp: HomeArticleListResp) {
        if (page == 0) {
            articleDatas.clear()
            if (homeArticleListResp.datas.size <= 0) return
            articleDatas.addAll(homeArticleListResp.datas)
            mAdapter.setNewData(articleDatas)

            onRefreshLoadMoreListener?.finishRefresh()

        } else {
            articleDatas.addAll(homeArticleListResp.datas)
            mAdapter.addData(articleDatas)
        }
        page = homeArticleListResp.curPage

        onRefreshLoadMoreListener?.loadPage(page, NEWBLOGTYPE)

        if (page < homeArticleListResp.pageCount) {

            onRefreshLoadMoreListener?.finishLoadMore()

        } else {
            onRefreshLoadMoreListener?.apply {
                enableLoadMore(false)
                finishLoadMore()
            }
        }
    }

    fun setOnRefreshOrLoadMoreListener(onRefreshLoadMoreListener: OnRefreshOrLoadMoreListener) {
        this.onRefreshLoadMoreListener = onRefreshLoadMoreListener
    }

    private var onRefreshLoadMoreListener: OnRefreshOrLoadMoreListener? = null

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

    override fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("收藏成功")
    }

    override fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("取消收藏")
    }
}