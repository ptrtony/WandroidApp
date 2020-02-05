package com.foxcr.kotlineasyshop.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
import com.foxcr.base.utils.GlideUtils
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener.Companion.NEWBLOGTYPE
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener.Companion.NEWPROJECT
import com.foxcr.base.widgets.slideview.SlidingPlayViewWithDot
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeBannerResp
import com.foxcr.kotlineasyshop.injection.component.DaggerHomeComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.HomePresenter
import com.foxcr.kotlineasyshop.presenter.view.HomeView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseMvpLazyFragment<HomePresenter>(), OnLoadMoreListener, HomeView,
    RadioGroup.OnCheckedChangeListener, OnRefreshListener, OnRefreshOrLoadMoreListener {
    private var projectPage: Int = 1
    private var articlePage: Int = 1
    private var bannerDatas: MutableList<HomeBannerResp> = mutableListOf()
    private lateinit var mInflater: LayoutInflater
    private var isCheckArticle = true
    private val newBlogArticleFragment: NewBlogArticleFragment by lazy {
        NewBlogArticleFragment()
    }

    private val newProjectArticleFragment: NewProjectArticleFragment by lazy {
        NewProjectArticleFragment()
    }

    private lateinit var mHomeSmartRefresh:SmartRefreshLayout
    private lateinit var mHomeNewArticleRg:RadioGroup
    private lateinit var mHomeBanner: SlidingPlayViewWithDot
    private lateinit var mHomeNewBlogRb:RadioButton
    private lateinit var mHomeNewProjectRb:RadioButton
    private lateinit var mFrameLayout:FrameLayout
    override fun resLayoutId(): Int = R.layout.fragment_home


    override fun initView(view: View) {
        mInflater = LayoutInflater.from(context)

        mHomeSmartRefresh = view.findViewById(R.id.mHomeSmartRefresh)
        mHomeNewArticleRg = view.findViewById(R.id.mHomeNewArticleRg)
        mHomeBanner = view.findViewById(R.id.mHomeBanner)
        mHomeNewBlogRb = view.findViewById(R.id.mHomeNewBlogRb)
        mHomeNewProjectRb = view.findViewById(R.id.mHomeNewProjectRb)
        mFrameLayout = view.findViewById(R.id.mFrameLayout)
        mHomeSmartRefresh.apply {
            setOnLoadMoreListener(this@HomeFragment)
            setOnRefreshListener(this@HomeFragment)
        }
        mHomeNewArticleRg.setOnCheckedChangeListener(this)
        if (mFrameLayout.childCount>0) mFrameLayout.removeAllViews()
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
        GlobalScope.launch (Dispatchers.Main){
            withContext(Dispatchers.IO){
                mPresenter.homeBanner()
            }

            withContext(Dispatchers.IO){
                refreshData()
            }
        }
    }

    private fun refreshData(){
        projectPage = 1
        articlePage = 1
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

    override fun onFragmentFirstVisible() {
        mHomeSmartRefresh.postDelayed({
            mHomeSmartRefresh.autoRefresh()
        },500)

    }


}