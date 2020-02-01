package com.foxcr.kotlineasyshop.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.base.utils.GlideUtils
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener.Companion.NEWBLOGTYPE
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener.Companion.NEWPROJECT
import com.foxcr.kotlineasyshop.R
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
    RadioGroup.OnCheckedChangeListener, OnRefreshListener, OnRefreshOrLoadMoreListener {
    private var projectPage: Int = 0
    private var articlePage: Int = 0
    private var bannerDatas: MutableList<HomeBannerResp> = mutableListOf()
    private lateinit var mInflater: LayoutInflater
    private var isCheckArticle = true
    private val newBlogArticleFragment: NewBlogArticleFragment by lazy {
        NewBlogArticleFragment()
    }

    private val newProjectArticleFragment: NewProjectArticleFragment by lazy {
        NewProjectArticleFragment()
    }

    override fun resLayoutId(): Int = R.layout.fragment_home


    override fun initView(view: View) {
        mInflater = LayoutInflater.from(context)
        mHomeSmartRefresh.setOnLoadMoreListener(this)
        mHomeSmartRefresh.setOnRefreshListener(this)
        mHomeSmartRefresh.autoRefresh()
        mHomeNewArticleRg.setOnCheckedChangeListener(this)
        newBlogArticleFragment.setOnRefreshOrLoadMoreListener(this)
        newProjectArticleFragment.setOnRefreshOrLoadMoreListener(this)
        mPresenter.mView = this
        childFragmentManager.beginTransaction()
            .add(R.id.mFrameLayout, newBlogArticleFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
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
            newBlogArticleFragment.getNewBlogArticleList(articlePage)
        } else {
            newProjectArticleFragment.getNewProjectArticleList(projectPage)
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


    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.mHomeNewBlogRb -> {
                mHomeNewBlogRb.setTextColor(resources.getColor(R.color.common_blue))
                mHomeNewProjectRb.setTextColor(resources.getColor(R.color.text_light_dark))
                isCheckArticle = true
                childFragmentManager.beginTransaction().replace(R.id.mFrameLayout,newBlogArticleFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.mHomeNewProjectRb -> {
                mHomeNewBlogRb.setTextColor(resources.getColor(R.color.text_light_dark))
                mHomeNewProjectRb.setTextColor(resources.getColor(R.color.common_blue))
                isCheckArticle = false
                childFragmentManager.beginTransaction().replace(R.id.mFrameLayout,newProjectArticleFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
        mHomeSmartRefresh.autoRefresh()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.getHomeNetData()
        refreshData()
    }

    private fun refreshData(){
        projectPage = 0
        articlePage = 0
        if (isCheckArticle){
            newBlogArticleFragment.getNewBlogArticleList(articlePage)
        }else{
            newProjectArticleFragment.getNewProjectArticleList(projectPage)
        }
    }

    override fun finishRefresh() {
        mHomeSmartRefresh.finishRefresh()
    }

    override fun finishLoadMore() {
        mHomeSmartRefresh.finishLoadMore()
    }

    override fun enableLoadMore(isLoadMore: Boolean) {
        mHomeSmartRefresh.setEnableLoadMore(isLoadMore)
    }

    override fun enableRefresh(isRefresh: Boolean) {
        mHomeSmartRefresh.setEnableRefresh(isRefresh)
    }

    override fun loadPage(page: Int, type: Int) {
        if (type == NEWPROJECT) {
            projectPage = page
        } else if (type == NEWBLOGTYPE) {
            articlePage = page
        }
    }


}