package com.foxcr.kotlineasyshop.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.utils.GlideUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener.Companion.NEWBLOGTYPE
import com.foxcr.base.widgets.OnRefreshOrLoadMoreListener.Companion.NEWPROJECT
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.base.widgets.slideview.SlidingPlayViewWithDot
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.ArticleEnum
import com.foxcr.kotlineasyshop.adapter.HomeArticleAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeArticleResp
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
import kotlinx.android.synthetic.main.item_home_article_project.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseMvpLazyFragment<HomePresenter>(), OnLoadMoreListener, HomeView,
    RadioGroup.OnCheckedChangeListener, OnRefreshListener, OnLikeClickListener {
    private var bannerDatas: MutableList<HomeBannerResp> = mutableListOf()
    private lateinit var mInflater: LayoutInflater
    private var isCheckArticle = true
    private var mHomeData: MutableList<HomeArticleResp.DatasBean> = mutableListOf()
    private val mHomeArticleAdapter: HomeArticleAdapter by lazy {
        HomeArticleAdapter(mHomeData)
    }

    private var page = 1

    private lateinit var mHomeSmartRefresh: SmartRefreshLayout
    private lateinit var mHomeArticleRl: RecyclerView
    private lateinit var mHomeNewArticleRg: RadioGroup
    private lateinit var mHomeBanner: SlidingPlayViewWithDot
    private lateinit var mHomeNewBlogRb: RadioButton
    private lateinit var mHomeNewProjectRb: RadioButton
    private var isCurrentLoad = true
    override fun resLayoutId(): Int = R.layout.fragment_home


    override fun initView(view: View) {
        mPresenter.mView = this
        mHomeSmartRefresh = view.findViewById(R.id.mHomeSmartRefresh)
        mHomeArticleRl = view.findViewById(R.id.mHomeArticleRl)
        mHomeArticleRl.apply {
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
            adapter = mHomeArticleAdapter
        }
        mInflater = LayoutInflater.from(context)
        val headView = LayoutInflater.from(activity)
            .inflate(R.layout.layout_home_head_container, mHomeArticleRl, false)
        mHomeNewArticleRg = headView.findViewById(R.id.mHomeNewArticleRg)
        mHomeBanner = headView.findViewById(R.id.mHomeBanner)
        mHomeNewBlogRb = headView.findViewById(R.id.mHomeNewBlogRb)
        mHomeNewProjectRb = headView.findViewById(R.id.mHomeNewProjectRb)
        mHomeArticleAdapter.addHeaderView(headView)
        mHomeSmartRefresh.apply {
            setOnLoadMoreListener(this@HomeFragment)
            setOnRefreshListener(this@HomeFragment)
        }
        mHomeArticleAdapter.setOnLikeClickListener(this)
        mHomeNewArticleRg.setOnCheckedChangeListener(this)
        mHomeArticleAdapter.setOnItemClickListener { adapter, view, position ->
            ARouter.getInstance()
                .build("/easyshop/web")
                .withString("url", mHomeData[position].link)
                .greenChannel()
                .navigation()
        }

        mHomeArticleAdapter.openLoadAnimation()
    }


    override fun onStop() {
        super.onStop()
        mHomeBanner.stopPlay()
    }

    override fun injectComponent() {
        DaggerHomeComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }


    override fun homeBanner(banners: List<HomeBannerResp>) {
        if (bannerDatas.isNotEmpty()) bannerDatas.clear()
        if (banners.isNotEmpty()) {
            bannerDatas.addAll(banners)
            startHomeBanner(bannerDatas)
        }
    }

    override fun homeArticleList(homeArticleResp: HomeArticleResp) {
        if (page == 1) {
            mHomeData.clear()
            mHomeData.addAll(homeArticleResp.datas)
            mHomeData.forEach { homeArticleResp ->
                homeArticleResp.itemType = ArticleEnum.ITEM_ARTICLE_TYPE
            }
            mHomeArticleAdapter.setNewData(mHomeData)
            mHomeSmartRefresh.finishRefresh()
        } else {
            mHomeData.addAll(homeArticleResp.datas)
            mHomeData.forEach { homeArticleResp ->
                homeArticleResp.itemType = ArticleEnum.ITEM_ARTICLE_TYPE
            }
            mHomeArticleAdapter.addData(mHomeData)
            mHomeSmartRefresh.finishLoadMore()
        }
        page++
        if (page > homeArticleResp.pageCount) {
            mHomeSmartRefresh.setEnableLoadMore(false)
        }
    }

    override fun homeArticleProjectList(homeArticleResp: HomeArticleResp) {
        if (page == 1 && homeArticleResp.datas.size <= 0) {
            mHomeArticleAdapter.emptyView = emptyView(mHomeArticleRl)
        }
        if (page == 1) {
            mHomeData.clear()
            mHomeData.addAll(homeArticleResp.datas)
            mHomeData.forEach { homeArticleResp ->
                homeArticleResp.itemType = ArticleEnum.ITEM_PROJECT_TYPE
            }
            mHomeArticleAdapter.setNewData(mHomeData)
            mHomeSmartRefresh.finishRefresh()
        } else {
            mHomeData.addAll(homeArticleResp.datas)
            mHomeData.forEach { homeArticleResp ->
                homeArticleResp.itemType = ArticleEnum.ITEM_PROJECT_TYPE
            }
            mHomeArticleAdapter.addData(mHomeData)
            mHomeSmartRefresh.finishLoadMore()
        }
        page++
        if (page > homeArticleResp.pageCount) {
            mHomeSmartRefresh.setEnableLoadMore(false)
        }
    }

    override fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("收藏成功")
    }

    override fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("取消收藏")
    }

    /**
     * 启动首页显示的banner
     */
    private fun startHomeBanner(bannerDatas: List<HomeBannerResp>) {
        isCurrentLoad = false
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
            }
            R.id.mHomeNewProjectRb -> {
                mHomeNewBlogRb.setTextColor(resources.getColor(R.color.text_light_dark))
                mHomeNewProjectRb.setTextColor(resources.getColor(R.color.common_blue))
                isCheckArticle = false
            }
        }
        mHomeSmartRefresh.autoRefresh()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        mHomeSmartRefresh.setEnableLoadMore(true)
        GlobalScope.launch(Dispatchers.Main) {
            if (isCurrentLoad) {
                withContext(Dispatchers.IO) {
                    mPresenter.homeBanner()
                }

            }
            withContext(Dispatchers.IO) {
                if (isCheckArticle) {
                    mPresenter.homeArticleList(page)
                } else {
                    mPresenter.homeArticleProjectList(page)
                }
            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        if (isCheckArticle) {
            mPresenter.homeArticleList(page)
        } else {
            mPresenter.homeArticleProjectList(page)
        }
    }

    override fun onFragmentFirstVisible() {
        mHomeSmartRefresh.postDelayed({
            mHomeSmartRefresh.autoRefresh()
        }, 500)
        initLoveLayout()
    }

    override fun onLikeInNetClick(view: View, id: Int) {
        mPresenter.homeArticleList(id)
        mLoveView.addLoveView(view)

    }

    override fun onLikeOutNetClick(view: View, title: String, author: String, link: String) {
        mPresenter.collectOutStandArticle(title, author, link)
        mLoveView.addLoveView(view)
    }

    override fun cancelCollectClick(id: Int, originId: Int) {
        mPresenter.uncollectArticle(id, originId)
    }

    override fun onError(errorMsg: String) {
        super.onError(errorMsg)
        mHomeSmartRefresh.finishRefresh()
    }

}