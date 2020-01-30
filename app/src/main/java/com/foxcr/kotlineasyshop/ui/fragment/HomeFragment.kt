package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.HomeArticleListAdapter
import com.foxcr.kotlineasyshop.adapter.HomeArticleProjectListAdapter
import com.foxcr.kotlineasyshop.adapter.HomeBannerAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleListResp
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleProjectListResp
import com.foxcr.kotlineasyshop.data.protocal.HomeBannerResp
import com.foxcr.kotlineasyshop.injection.component.DaggerHomeComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.HomePresenter
import com.foxcr.kotlineasyshop.presenter.view.HomeView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseMvpFragment<HomePresenter>(), OnLoadMoreListener, HomeView,
    RadioGroup.OnCheckedChangeListener, HomeArticleListAdapter.OnLikeClickListener,
    HomeArticleProjectListAdapter.OnClickListener {
    lateinit var homeBannerAdapter: HomeBannerAdapter
    lateinit var homeArticleListAdapter: HomeArticleListAdapter
    private lateinit var homeArticleProjectListAdapter: HomeArticleProjectListAdapter
    override fun resLayoutId(): Int = R.layout.fragment_home
    private var page: Int = 0
    private var bannerDatas: MutableList<HomeBannerResp> = mutableListOf()
    private var homeArticleDatas: MutableList<HomeArticleListResp.DatasBean> = mutableListOf()
    private var homeArticleProjectDatas: MutableList<HomeArticleProjectListResp.DatasBean> =
        mutableListOf()
    lateinit var homeBanner: Banner<HomeBannerResp, HomeBannerAdapter>
    override fun initView(view: View) {
        homeBanner = view.findViewById(R.id.mHomeBanner)
        mHomeSmartRefresh.setEnableRefresh(false)
        mHomeSmartRefresh.setOnLoadMoreListener(this)
        mHomeNewArticleRg.setOnCheckedChangeListener(this)

        mPresenter.mView = this
        mPresenter.getHomeNetData()

        homeBannerAdapter = HomeBannerAdapter(bannerDatas)
        homeArticleListAdapter = HomeArticleListAdapter(homeArticleDatas)
        homeArticleProjectListAdapter = HomeArticleProjectListAdapter(homeArticleProjectDatas)
        homeBanner.setAdapter(homeBannerAdapter)
            .setIndicator(CircleIndicator(activity))
            .setIndicatorSelectedColorRes(R.color.common_blue)
            .setIndicatorNormalColorRes(R.color.text_normal)
            .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
            .setIndicatorMargins(IndicatorConfig.Margins(BannerUtils.dp2px(10f).toInt()))

        mHomeArticleListRl.layoutManager = LinearLayoutManager(activity, VERTICAL, false)
        mHomeArticleListRl.adapter = homeArticleListAdapter
        homeArticleListAdapter.setOnLikeClickListener(this)
        homeArticleProjectListAdapter.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        homeBanner.start()
    }

    override fun onStop() {
        super.onStop()
        homeBanner.stop()
    }

    override fun injectComponent() {
        DaggerHomeComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.homeArticleList(page)
    }

    override fun homeBanner(banners: List<HomeBannerResp>) {
        if (bannerDatas.isNotEmpty()) bannerDatas.clear()
        bannerDatas.addAll(banners)
        homeBannerAdapter.setDatas(bannerDatas)
    }

    override fun homeArticleList(homeArticleListResp: HomeArticleListResp) {
        if (homeArticleDatas.size > 0 && page == 0) {
            homeArticleDatas.clear()
            homeArticleDatas.addAll(homeArticleListResp.datas)
            homeArticleListAdapter.setNewData(homeArticleDatas)
        } else {
            homeArticleDatas.addAll(homeArticleListResp.datas)
            homeArticleListAdapter.addData(homeArticleDatas)
        }
        mHomeArticleListRl.removeAllViews()
        mHomeArticleListRl.adapter = homeArticleListAdapter
        page = homeArticleListResp.curPage
        if (page < homeArticleListResp.pageCount) {
            mHomeSmartRefresh.finishLoadMore()
        } else {
            mHomeSmartRefresh.setEnableLoadMore(false)
            mHomeSmartRefresh.finishLoadMore()
        }
    }

    override fun homeArticleProjectList(homeArticleProjectListResp: HomeArticleProjectListResp) {
        if (homeArticleProjectDatas.size > 0 && page == 0) {
            homeArticleProjectDatas.clear()
            homeArticleProjectDatas.addAll(homeArticleProjectListResp.datas)
            homeArticleProjectListAdapter.setNewData(homeArticleProjectDatas)
        } else {
            homeArticleProjectDatas.addAll(homeArticleProjectListResp.datas)
            homeArticleProjectListAdapter.addData(homeArticleProjectDatas)
        }
        mHomeArticleListRl.removeAllViews()
        mHomeArticleListRl.adapter = homeArticleProjectListAdapter
        page = homeArticleProjectListResp.curPage
        if (page < homeArticleProjectListResp.pageCount) {
            mHomeSmartRefresh.finishLoadMore()
        } else {
            mHomeSmartRefresh.setEnableLoadMore(false)
            mHomeSmartRefresh.finishLoadMore()
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.mHomeNewBlogRb -> {
                mHomeNewBlogRb.autoSizeAnimation(true)
                mHomeNewProjectRb.autoSizeAnimation(false)
                page = 0
                mPresenter.homeArticleList(page)
            }
            R.id.mHomeNewProjectRb -> {
                mHomeNewBlogRb.autoSizeAnimation(false)
                mHomeNewProjectRb.autoSizeAnimation(true)
                page = 0
                mPresenter.homeArticleProjectList(page)
            }
        }
    }

    override fun onLikeClick(position: Int, collect: Boolean) {

    }

    override fun onProjectLikeClick(position: Int, collect: Boolean) {

    }

    override fun onCheckAsProjectClick() {

    }
}