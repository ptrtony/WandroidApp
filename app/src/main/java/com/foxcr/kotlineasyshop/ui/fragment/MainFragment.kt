package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.base.widgets.tablayout.TabLayout
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.MainFragmentAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseMvpFragment<BasePresenter<BaseView>>() {
    private var tabTexts = arrayListOf("首页", "广场", "导航", "问答", "体系", "项目", "公众号", "项目分类", "工具")

    private val mainAdapter: MainFragmentAdapter by lazy {
        MainFragmentAdapter(childFragmentManager,tabTexts)
    }

    override fun initView(view: View) {
        mHomeTab?.run {
            tabMode = TabLayout.MODE_SCROLLABLE
            addTab(mHomeTab.newTab().setText(tabTexts[0]))
            addTab(mHomeTab.newTab().setText(tabTexts[1]))
            addTab(mHomeTab.newTab().setText(tabTexts[2]))
            addTab(mHomeTab.newTab().setText(tabTexts[3]))
            addTab(mHomeTab.newTab().setText(tabTexts[4]))
            addTab(mHomeTab.newTab().setText(tabTexts[5]))
            addTab(mHomeTab.newTab().setText(tabTexts[6]))
            addTab(mHomeTab.newTab().setText(tabTexts[7]))
            addTab(mHomeTab.newTab().setText(tabTexts[8]))
            setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }
                override fun onTabSelected(tab: TabLayout.Tab) {
                    mMainVp2.currentItem = tab.position
                }

            })
            setTabTextColors(resources.getColor(R.color.text_light_dark),resources.getColor(R.color.common_blue))
            mHomeTab.setupWithViewPager(mMainVp2)
        }

        mMainVp2?.run {
            adapter = mainAdapter
            offscreenPageLimit = tabTexts.size
            setCurrentItem(0,false)
        }

    }


    override fun injectComponent() {

    }

    override fun resLayoutId(): Int = R.layout.fragment_main

}