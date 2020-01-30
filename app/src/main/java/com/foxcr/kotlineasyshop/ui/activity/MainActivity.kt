package com.foxcr.kotlineasyshop.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.foxcr.base.common.AppManager
import com.foxcr.base.utils.ToastUtils
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.MainFragmentAdapter
import com.foxcr.kotlineasyshop.ui.fragment.MainFragment
import com.foxcr.kotlineasyshop.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*


@Route(path = "/app/main")
class MainActivity : AppCompatActivity() {
    private var pressTime: Long = 0L
    private lateinit var mainPagerAdapter: MainFragmentAdapter
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

    override fun onBackPressed() {
        super.onBackPressed()
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            ToastUtils.showToast("再按一次退出")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }
}
