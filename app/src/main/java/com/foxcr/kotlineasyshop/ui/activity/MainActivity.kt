package com.foxcr.kotlineasyshop.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.foxcr.base.common.AppManager
import com.foxcr.base.utils.ToastUtils
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.MainAdapter
import com.foxcr.kotlineasyshop.ui.fragment.MainFragment
import com.foxcr.kotlineasyshop.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*


@Route(path = "/easyshop/main")
class MainActivity : AppCompatActivity() {
    private var pressTime: Long = 0L
    private lateinit var mainPagerAdapter: MainAdapter
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
        mainPagerAdapter = MainAdapter(supportFragmentManager, fragments)
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

        mHomeVg.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                mHomeBNB.selectTab(position)
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
