package com.foxcr.kotlineasyshop.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.foxcr.kotlineasyshop.ui.fragment.*

class MainFragmentAdapter constructor(fm: FragmentManager, private val titles: List<String>) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SquareFragment()
            2 -> NavigationFragment()
            3 -> QuestionAnswersFragment()
            4 -> SystemFragment()
            5 -> ProjectFragment()
            6 -> PublicNumberFragment()
            7 -> ProjectCategoryFragment()
            else -> ToolsFragment()
        }
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }


}