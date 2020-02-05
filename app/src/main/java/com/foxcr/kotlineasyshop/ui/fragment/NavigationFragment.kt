package com.foxcr.kotlineasyshop.ui.fragment

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.widgets.LogUtils
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.base.widgets.recyclerview.CenterLayoutManager
import com.foxcr.base.widgets.sticky.StickyItemDecoration
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.NavigationListOneAdapter
import com.foxcr.kotlineasyshop.adapter.NavigationListTwoAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeNavigationResp
import com.foxcr.kotlineasyshop.data.protocal.NavigationListOneBean
import com.foxcr.kotlineasyshop.injection.component.DaggerNavigationComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.NavigationPresenter
import com.foxcr.kotlineasyshop.presenter.view.NavigationView
import kotlinx.android.synthetic.main.fragment_home.*


class NavigationFragment : BaseMvpLazyFragment<NavigationPresenter>(), NavigationView,
    NavigationListOneAdapter.OnNavigationListOneClickListener,
    NavigationListTwoAdapter.OnNavigationListTwoClickListener {
    companion object {
        const val TITLE = 1
        const val CONTENT = 2
    }

    private val mListOneDatas: MutableList<NavigationListOneBean> = mutableListOf()
    private val mListTwoDatas: MutableList<HomeNavigationResp.ArticlesBean> = mutableListOf()
    private val mNavigationListOneAdapter: NavigationListOneAdapter by lazy {
        NavigationListOneAdapter(mListOneDatas)
    }

    private val mNavigationListTwoAdapter: NavigationListTwoAdapter by lazy {
        NavigationListTwoAdapter(activity!!, mListTwoDatas)
    }


    private lateinit var centerLayoutManager: CenterLayoutManager
    private var mListOneLastPosition = 0
    private var mListOneMovePosition = 0

    private lateinit var mNavigationOneRl:RecyclerView
    private lateinit var mNavigationTwoRl:RecyclerView
    override fun resLayoutId(): Int = R.layout.fragment_navigation
    override fun injectComponent() {
        DaggerNavigationComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView(view: View) {
        mPresenter.mView = this
        mNavigationOneRl = view.findViewById(R.id.mNavigationOneRl)
        mNavigationTwoRl = view.findViewById(R.id.mNavigationTwoRl)

        centerLayoutManager = CenterLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        mNavigationOneRl.apply {
            layoutManager = centerLayoutManager
            addItemDecoration(
                RecycleViewDivider(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    DisplayUtils.dp2px(1f),
                    resources.getColor(R.color.common_divider),
                    DisplayUtils.dp2px(15f)
                )
            )
            adapter = mNavigationListOneAdapter
        }

        mNavigationTwoRl.apply {
            layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(StickyItemDecoration())
            adapter = mNavigationListTwoAdapter
            setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                val firstVisiblePosition = mNavigationTwoRl.getChildLayoutPosition(mNavigationTwoRl.getChildAt(0))
                for (i in 0 until mListOneDatas.size){
                    if (mListTwoDatas[firstVisiblePosition].chapterName==mListOneDatas[i].title){
                        if (mListOneMovePosition!=i){
                            mListOneDatas.forEachIndexed { index, oneBean ->
                                oneBean.isSuccess = index == i
                            }
                            centerLayoutManager.smoothScrollToPosition(mNavigationOneRl, RecyclerView.State(),
                                i
                            )
                            mNavigationListOneAdapter.notifyDataSetChanged()
                        }
                        mListOneMovePosition = i

                        break
                    }
                }
            }
        }


        mNavigationListOneAdapter.setOnNavigationListOneClickListener(this)
        mNavigationListTwoAdapter.setOnNavigationListTwoClickListener(this)
    }

    override fun onNavigationResult(navigationDatas: List<HomeNavigationResp>) {
        navigationDatas.forEachIndexed { index, homeNavigationResp ->
            if (index == 0) {
                mListOneDatas.add(NavigationListOneBean(homeNavigationResp.name, true))
            } else {
                mListOneDatas.add(NavigationListOneBean(homeNavigationResp.name, false))
            }
            val titleBean = HomeNavigationResp.ArticlesBean()
            titleBean.itemType = TITLE
            titleBean.chapterName = homeNavigationResp.name

            mListTwoDatas.add(titleBean)
            homeNavigationResp.articles.forEach {
                it.itemType = CONTENT
                mListTwoDatas.add(it)
            }
        }
        mNavigationListOneAdapter.setNewData(mListOneDatas)
        mNavigationListTwoAdapter.setNewData(mListTwoDatas)

    }

    override fun onNavigationListOneClick(view: View, position: Int,navigationListOneBean: NavigationListOneBean) {
        var mLastMovePosition = 0
        if (position != mListOneLastPosition){
            mListOneDatas.forEachIndexed { index, oneBean ->
                oneBean.isSuccess = index == position
            }
            centerLayoutManager.smoothScrollToPosition(mNavigationOneRl,RecyclerView.State(),position)
            mNavigationListOneAdapter.notifyDataSetChanged()
            for (i in 0 until mListTwoDatas.size){
                if (mListOneDatas[position].title == mListTwoDatas[i].chapterName){
                    mLastMovePosition = i
                    LogUtils.d("name:>>>>${mListTwoDatas[i].chapterName}>>>>>>>>position:>>>$mLastMovePosition")
                    break
                }
            }
            val mLayoutManager =
                (mNavigationTwoRl.layoutManager as LinearLayoutManager)
            mLayoutManager.scrollToPositionWithOffset(mLastMovePosition, 0)
        }
        mListOneLastPosition = position


    }

    override fun onNavigationListTwoClick(view: View, position: Int) {
        ARouter.getInstance()
            .build("/easyshop/web")
            .withString("url",mListTwoDatas[position].link)
            .greenChannel()
            .navigation()
    }

    override fun onFragmentFirstVisible() {
        mNavigationOneRl.postDelayed({
            mPresenter.getNavigationData()
        },500)
    }



}