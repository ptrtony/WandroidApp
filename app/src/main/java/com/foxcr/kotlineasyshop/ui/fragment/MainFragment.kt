package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.base.widgets.tablayout.TabLayout
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.MainFragmentAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseMvpFragment<BasePresenter<BaseView>>(){
    private var tabTexts = arrayListOf("首页","广场","导航","问答","体系","项目","公众号","项目分类","工具")
    private var fragments:MutableList<Fragment> = mutableListOf()
    lateinit var mainFragmentAdapter:MainFragmentAdapter
    override fun initView(view:View) {
        fragments.add(HomeFragment())
        fragments.add(SquareFragment())
        fragments.add(NavigationFragment())
        fragments.add(QuestionAnswersFragment())
        fragments.add(SystemFragment())
        fragments.add(ProjectFragment())
        fragments.add(PublicNumberFragment())
        fragments.add(ProjectCategoryFragment())
        fragments.add(ToolsFragment())
        mainFragmentAdapter = MainFragmentAdapter(childFragmentManager,fragments)
        mHomeTab.addTab(mHomeTab.newTab().setText(tabTexts[0]))
        mHomeTab.addTab(mHomeTab.newTab().setText(tabTexts[1]))
        mHomeTab.addTab(mHomeTab.newTab().setText(tabTexts[2]))
        mHomeTab.addTab(mHomeTab.newTab().setText(tabTexts[3]))
        mHomeTab.addTab(mHomeTab.newTab().setText(tabTexts[4]))
        mHomeTab.addTab(mHomeTab.newTab().setText(tabTexts[5]))
        mHomeTab.addTab(mHomeTab.newTab().setText(tabTexts[6]))
        mHomeTab.addTab(mHomeTab.newTab().setText(tabTexts[7]))
        mHomeTab.addTab(mHomeTab.newTab().setText(tabTexts[8]))
        mHomeTab.setOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabSelected(tab: TabLayout.Tab) {

                mMainVp2.currentItem = tab.position
            }

        })

        mMainVp2.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mHomeTab.setScrollPosition(position,0.toFloat(),false)
            }
        })

    }

    override fun injectComponent() {

    }

    override fun resLayoutId(): Int = R.layout.fragment_main
}