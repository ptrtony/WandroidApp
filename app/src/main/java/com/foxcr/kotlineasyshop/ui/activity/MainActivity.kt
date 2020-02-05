package com.foxcr.kotlineasyshop.ui.activity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.ui.activity.BaseActivity
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.HeaderBar
import com.foxcr.base.widgets.tablayout.TabLayout
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.MainFragmentAdapter
import com.google.android.material.navigation.NavigationView

@Route(path = "/easyshop/main")
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var tabTexts = arrayListOf("首页", "广场", "导航", "问答", "体系", "项目", "公众号", "项目分类", "收藏")
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mHomeTab: TabLayout
    private lateinit var mMainVp2: ViewPager
    private val mainAdapter: MainFragmentAdapter by lazy {
        MainFragmentAdapter(supportFragmentManager, tabTexts)
    }

    override fun initView() {
        val mAppBarMain: LinearLayout = findViewById(R.id.mAppBarMain)
        mHomeTab = mAppBarMain.findViewById(R.id.mHomeTab)
        mMainVp2 = mAppBarMain.findViewById(R.id.mMainVp2)
        val mHomeHeaderBar: HeaderBar = mAppBarMain.findViewById(R.id.mHomeHeaderBar)
        mHomeHeaderBar.setCenterText(tabTexts[0])
        drawerLayout = findViewById(R.id.drawer_layout)

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        mHomeHeaderBar.onBackClickListener {
            drawerLayout.openDrawer(GravityCompat.START,true)
        }

        mHomeHeaderBar.onRightIconClickListener {
            ARouter.getInstance()
                .build("/easyshop/search")
                .greenChannel()
                .navigation()
        }

        mHomeTab.run {
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
                    mHomeHeaderBar.setCenterText(tabTexts[tab.position])
                }

            })
            setTabTextColors(
                resources.getColor(R.color.text_light_dark),
                resources.getColor(R.color.common_blue)
            )
            mHomeTab.setupWithViewPager(mMainVp2)
        }

        mMainVp2.run {
            adapter = mainAdapter
            offscreenPageLimit = tabTexts.size - 1
            currentItem = 0
        }

    }

    override fun initActivityComponent() {

    }

    override fun resLayoutId(): Int = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> ToastUtils.showToast("首页")
            R.id.nav_gallery -> ToastUtils.showToast("相册")
            R.id.nav_slideshow -> ToastUtils.showToast("视频")
            R.id.nav_tools -> ToastUtils.showToast("工具")
            R.id.nav_share -> ToastUtils.showToast("分享")
            R.id.nav_send -> ToastUtils.showToast("消息")
        }
        //关闭Drawer
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}