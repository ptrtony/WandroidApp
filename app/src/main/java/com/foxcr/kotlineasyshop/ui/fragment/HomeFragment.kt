package com.foxcr.kotlineasyshop.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.utils.GlideUtils
import com.foxcr.base.widgets.RecycleViewDivider

import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.HomeArticleListAdapter
import com.foxcr.kotlineasyshop.adapter.HomeArticleProjectListAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleListResp
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleProjectListResp
import com.foxcr.kotlineasyshop.data.protocal.HomeBannerResp
import com.foxcr.kotlineasyshop.injection.component.DaggerHomeComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.HomePresenter
import com.foxcr.kotlineasyshop.presenter.view.HomeView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseMvpFragment<HomePresenter>(), OnLoadMoreListener, HomeView,
    RadioGroup.OnCheckedChangeListener, HomeArticleListAdapter.OnLikeClickListener,
    HomeArticleProjectListAdapter.OnClickListener, OnRefreshListener {
    lateinit var homeArticleListAdapter: HomeArticleListAdapter
    private lateinit var homeArticleProjectListAdapter: HomeArticleProjectListAdapter
    override fun resLayoutId(): Int = R.layout.fragment_home
    private var projectPage: Int = 0
    private var articlePage: Int = 0
    private var bannerDatas: MutableList<HomeBannerResp> = mutableListOf()
    private var homeArticleDatas: MutableList<HomeArticleListResp.DatasBean> = mutableListOf()
    private var homeArticleProjectDatas: MutableList<HomeArticleProjectListResp.DatasBean> =
        mutableListOf()
    private lateinit var mInflater: LayoutInflater
    private var isCheckArticle = true
    override fun initView(view: View) {
        mArticleListRv.visibility = View.VISIBLE
        mProjectListRv.visibility = View.GONE
        mInflater = LayoutInflater.from(context)
        mHomeSmartRefresh.setOnLoadMoreListener(this)
        mHomeSmartRefresh.setOnRefreshListener(this)
        mHomeSmartRefresh.autoRefresh()
        mHomeNewArticleRg.setOnCheckedChangeListener(this)
        mPresenter.mView = this

        homeArticleListAdapter = HomeArticleListAdapter(homeArticleDatas)
        homeArticleProjectListAdapter = HomeArticleProjectListAdapter(homeArticleProjectDatas)
        //首页最新博文列表
        mArticleListRv.layoutManager = LinearLayoutManager(activity, VERTICAL, false)
        mArticleListRv.addItemDecoration(
            RecycleViewDivider(
                context,
                LinearLayoutManager.HORIZONTAL,
                DisplayUtils.dp2px(1f),
                resources.getColor(R.color.common_divider),
                DisplayUtils.dp2px(15f)
            )
        )
        mArticleListRv.adapter = homeArticleListAdapter
        //首页最新项目列表
        mProjectListRv.layoutManager = LinearLayoutManager(activity, VERTICAL, false)
        mProjectListRv.addItemDecoration(
            RecycleViewDivider(
                context,
                LinearLayoutManager.HORIZONTAL,
                DisplayUtils.dp2px(1f),
                resources.getColor(R.color.common_divider),
                DisplayUtils.dp2px(15f)
            )
        )
        mProjectListRv.adapter = homeArticleProjectListAdapter

        homeArticleListAdapter.setOnLikeClickListener(this)
        homeArticleProjectListAdapter.setOnClickListener(this)

    }

    override fun onStop() {
        super.onStop()
        mHomeBanner.stopPlay()
    }

    override fun injectComponent() {
        DaggerHomeComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        if (isCheckArticle) {
            mPresenter.homeArticleList(articlePage)
        } else {
            mPresenter.homeArticleProjectList(projectPage)
        }
    }

    override fun homeBanner(banners: List<HomeBannerResp>) {
        if (bannerDatas.isNotEmpty()) bannerDatas.clear()
        if (banners.isNotEmpty()) {
            bannerDatas.addAll(banners)
            startHomeBanner(bannerDatas)
        }
    }

    /**
     * 启动首页显示的banner
     */
    private fun startHomeBanner(bannerDatas: List<HomeBannerResp>) {
        if (bannerDatas.isEmpty()) {
            mHomeBanner.visibility = View.GONE
            return
        }
        if (mHomeBanner.childCount > 0) {
            mHomeBanner.removeAllViews()
        }

        for (i in bannerDatas.indices) {
            val bannerView = mInflater.inflate(R.layout.item_home_banner, null)
            val bannerImage: ImageView = bannerView.findViewById(R.id.mHomeBannerIv)
            val bannerTitle: TextView = bannerView.findViewById(R.id.mHomeBannerTitleTv)
            GlideUtils.loadImage(bannerDatas[i].imagePath, bannerImage)
            bannerTitle.text = bannerDatas[i].title
            if (i == bannerDatas.size - 2) {
                bannerTitle.setTextColor(resources.getColor(R.color.text_light_dark))
            }
            mHomeBanner.addView(bannerView)
        }
        mHomeBanner.startPlay()
    }

    override fun homeArticleList(homeArticleListResp: HomeArticleListResp) {
        if (articlePage == 0) {
            homeArticleDatas.clear()
            if (homeArticleListResp.datas.size <= 0) return
            homeArticleDatas.addAll(homeArticleListResp.datas)
            homeArticleListAdapter.setNewData(homeArticleDatas)
            mHomeSmartRefresh.finishRefresh()
        } else {
            homeArticleDatas.addAll(homeArticleListResp.datas)
            homeArticleListAdapter.addData(homeArticleDatas)
        }
        articlePage = homeArticleListResp.curPage
        if (articlePage < homeArticleListResp.pageCount) {
            mHomeSmartRefresh.finishLoadMore()
        } else {
            mHomeSmartRefresh.setEnableLoadMore(false)
            mHomeSmartRefresh.finishLoadMore()
        }
    }

    override fun homeArticleProjectList(homeArticleProjectListResp: HomeArticleProjectListResp) {
        if (projectPage == 0) {
            homeArticleProjectDatas.clear()
            if (homeArticleProjectListResp.datas.size <= 0) return
            homeArticleProjectDatas.addAll(homeArticleProjectListResp.datas)
            homeArticleProjectListAdapter.setNewData(homeArticleProjectDatas)
            mHomeSmartRefresh.finishRefresh()
        } else {
            homeArticleProjectDatas.addAll(homeArticleProjectListResp.datas)
            homeArticleProjectListAdapter.addData(homeArticleProjectDatas)
        }
        projectPage = homeArticleProjectListResp.curPage
        if (projectPage < homeArticleProjectListResp.pageCount) {
            mHomeSmartRefresh.finishLoadMore()
        } else {
            mHomeSmartRefresh.setEnableLoadMore(false)
            mHomeSmartRefresh.finishLoadMore()
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.mHomeNewBlogRb -> {
                mHomeNewBlogRb.setTextColor(resources.getColor(R.color.common_blue))
                mHomeNewProjectRb.setTextColor(resources.getColor(R.color.text_light_dark))
                mArticleListRv.visibility = View.VISIBLE
                mProjectListRv.visibility = View.GONE
                isCheckArticle = true
            }
            R.id.mHomeNewProjectRb -> {
                mHomeNewBlogRb.setTextColor(resources.getColor(R.color.text_light_dark))
                mHomeNewProjectRb.setTextColor(resources.getColor(R.color.common_blue))
                mProjectListRv.visibility = View.VISIBLE
                mArticleListRv.visibility = View.GONE
                isCheckArticle = false
            }
        }
    }

    override fun onLikeClick(position: Int, collect: Boolean) {

    }

    override fun onProjectLikeClick(position: Int, collect: Boolean) {

    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        projectPage = 0
        articlePage = 0
        mPresenter.getHomeNetData()
    }


}