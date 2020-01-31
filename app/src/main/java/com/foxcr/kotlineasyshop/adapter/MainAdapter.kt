package com.foxcr.kotlineasyshop.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainAdapter constructor(fm:FragmentManager, val fragments:List<Fragment>): FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}