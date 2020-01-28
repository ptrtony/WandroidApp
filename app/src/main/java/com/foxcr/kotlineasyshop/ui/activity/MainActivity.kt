package com.foxcr.kotlineasyshop.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.MainFragmentAdapter
import com.foxcr.kotlineasyshop.ui.fragment.MainFragment
import com.foxcr.kotlineasyshop.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*


@Route(path = "/app/main")
class MainActivity : AppCompatActivity() {
    lateinit var mainPagerAdapter: MainFragmentAdapter
    private val fragments = mutableListOf<Fragment>()
    private lateinit var mainFragment: MainFragment
    private lateinit var mineFragment: MineFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainFragment = MainFragment()
        mineFragment = MineFragment()
        fragments.add(mainFragment)
        fragments.add(mineFragment)
        mainPagerAdapter = MainFragmentAdapter(supportFragmentManager, fragments)
        mHomeVg.adapter = mainPagerAdapter
        mHomeVg.currentItem = 0
        mHomeVg.offscreenPageLimit = fragments.size

        mHomeBNB.setTabSelectedListener(object :
            BottomNavigationBar.SimpleOnTabSelectedListener() {
            override fun onTabSelected(position: Int) {
                super.onTabSelected(position)
                when (position) {
                    0 -> {
                        mHomeVg.currentItem = 0
                    }

                    1 -> mHomeVg.currentItem = 1
                }
            }

        })

    }
}
