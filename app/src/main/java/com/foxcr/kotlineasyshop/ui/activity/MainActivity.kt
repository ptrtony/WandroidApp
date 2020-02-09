package com.foxcr.kotlineasyshop.ui.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.common.AppManager
import com.foxcr.base.common.BaseConstant
import com.foxcr.base.common.BaseConstant.Companion.ISNIGHT
import com.foxcr.base.common.EasyNavigationCallback
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.utils.SPUtil
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.HeaderBar
import com.foxcr.base.widgets.tablayout.TabLayout
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.MainFragmentAdapter
import com.foxcr.kotlineasyshop.data.protocal.UserInfoCoinResp
import com.foxcr.kotlineasyshop.injection.component.DaggerMainComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.MainPresenter
import com.foxcr.kotlineasyshop.presenter.view.MainView
import com.google.android.material.navigation.NavigationView

@Route(path = "/easyshop/main")
class MainActivity : BaseMvpActivity<MainPresenter>(), MainView,
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var pressTime: Long = 0
    private var tabTexts = arrayListOf("首页", "广场", "导航", "问答", "体系", "项目", "公众号", "项目分类", "收藏")
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mHomeTab: TabLayout
    private lateinit var mMainVp2: ViewPager
    private lateinit var mUserStandCoinTv: TextView
    private lateinit var mCoinTv: TextView
    private lateinit var mLevelTv: TextView
    private lateinit var mRankTv: TextView
    private lateinit var mCoinRankTv: TextView
    private lateinit var mAppTv: TextView
    private lateinit var mOftenNetTv: TextView
    private lateinit var mMoonTv: TextView
    private lateinit var mQuitTv: TextView
    private lateinit var refreshBroadcastReceiver: RefreshUserInfoBroadCastReceiver
    private val mainAdapter: MainFragmentAdapter by lazy {
        MainFragmentAdapter(supportFragmentManager, tabTexts)
    }

    override fun initView() {
        mPresenter.mView = this
        refreshBroadcastReceiver = RefreshUserInfoBroadCastReceiver(mPresenter)
        val intentFilter = IntentFilter()
        intentFilter.priority = 100
        intentFilter.addAction("android.easyshop.refreshUserInfo")
        registerReceiver(refreshBroadcastReceiver, intentFilter)

        val mAppBarMain: LinearLayout = findViewById(R.id.mAppBarMain)
        mHomeTab = mAppBarMain.findViewById(R.id.mHomeTab)
        mMainVp2 = mAppBarMain.findViewById(R.id.mMainVp2)
        val mHomeHeaderBar: HeaderBar = mAppBarMain.findViewById(R.id.mHomeHeaderBar)
        mHomeHeaderBar.setCenterText(tabTexts[0])
        drawerLayout = findViewById(R.id.drawer_layout)

        val navView: NavigationView = findViewById(R.id.nav_view)
        val navHeaderView = navView.getHeaderView(0)
        val mOriginCodeTv = navHeaderView.findViewById<TextView>(R.id.mOriginCodeTv)
        mUserStandCoinTv = navHeaderView.findViewById(R.id.mUserStandCoinTv)
        mCoinTv = navHeaderView.findViewById(R.id.mCoinTv)
        mLevelTv = navHeaderView.findViewById(R.id.mLevelTv)
        mRankTv = navHeaderView.findViewById(R.id.mRankTv)
        mCoinRankTv = navHeaderView.findViewById(R.id.mCoinRankTv)
        mAppTv = navHeaderView.findViewById(R.id.mAppTv)
        mOftenNetTv = navHeaderView.findViewById(R.id.mOftenNetTv)
        mMoonTv = navHeaderView.findViewById(R.id.mMoonTv)
        mQuitTv = navHeaderView.findViewById(R.id.mQuitTv)
        mOriginCodeTv.setOnClickListener {
            ARouter.getInstance()
                .build("/easyshop/web")
                .withString("url", "https://github.com/ptrtony/WandroidApp")
                .navigation(this, object : EasyNavigationCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        super.onArrival(postcard)
                        drawerLayout.postDelayed({
                            drawerLayout.closeDrawer(GravityCompat.START)
                        },300)
                    }
                })
        }

        mUserStandCoinTv.setOnClickListener {
            ARouter.getInstance()
                .build("/easyshop/usercoin")
                .navigation(this, object : EasyNavigationCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        super.onArrival(postcard)
                        drawerLayout.postDelayed({
                            drawerLayout.closeDrawer(GravityCompat.START)
                        },300)
                    }
                })
        }

        mCoinRankTv.setOnClickListener {
            ARouter.getInstance()
                .build("/easyshop/coinrank")
                .navigation(this, object : EasyNavigationCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        super.onArrival(postcard)
                        drawerLayout.postDelayed({
                            drawerLayout.closeDrawer(GravityCompat.START)
                        },300)
                    }
                })
        }

        mRankTv.setOnClickListener {
            ARouter.getInstance()
                .build("/easyshop/usercoin")
                .navigation(this, object : EasyNavigationCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        super.onArrival(postcard)
                        drawerLayout.postDelayed({
                            drawerLayout.closeDrawer(GravityCompat.START)
                        },300)
                    }
                })
        }

        mOftenNetTv.setOnClickListener {
            ARouter.getInstance()
                .build("/eashshop/oftennet")
                .greenChannel()
                .navigation(this, object : EasyNavigationCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        super.onArrival(postcard)
                        drawerLayout.postDelayed({
                            drawerLayout.closeDrawer(GravityCompat.START)
                        },300)
                    }
                })
        }

        mMoonTv.setOnClickListener {
            if (SPUtil.getBoolean(ISNIGHT)){
                SPUtil.putBoolean(ISNIGHT,false)
                //设置为夜间模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }else{
                SPUtil.putBoolean(ISNIGHT,true)
                //设置为日间模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                recreate()
            }

            ARouter.getInstance()
                .build("/easyshop/main")
                .greenChannel()
                .navigation(this,object:EasyNavigationCallback(){
                    override fun onArrival(postcard: Postcard?) {
                        super.onArrival(postcard)
                        finish()
                    }
                })
        }

        mQuitTv.setOnClickListener {
            mPresenter.getUserLoginout()
        }


        navView.setNavigationItemSelectedListener(this)
        mHomeHeaderBar.onBackClickListener {
            drawerLayout.openDrawer(GravityCompat.START, true)
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

        if (!(SPUtil.getString(
                BaseConstant.LOGINUSERPASSWORD,
                ""
            ).isNullOrEmpty() || SPUtil.getString(BaseConstant.LOGINUSERNAME, "").isNullOrEmpty())
        ) {
            mPresenter.getUserInfoCoinData()
        }

    }

    override fun initActivityComponent() {
        DaggerMainComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun resLayoutId(): Int = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(refreshBroadcastReceiver)
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

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            ToastUtils.showToast("再按一次退出")
            pressTime = time
            return
        } else {
            AppManager.instance.exitApp(this)
        }
        super.onBackPressed()
    }


    class RefreshUserInfoBroadCastReceiver constructor(val mPresenter: MainPresenter) :
        BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (action == "android.easyshop.refreshUserInfo") {
                mPresenter.getUserInfoCoinData()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onUserInfoCoinResult(userInfoCoin: UserInfoCoinResp) {
        mCoinTv.text = "积分: ${userInfoCoin.coinCount}"
        mRankTv.text = "排名 ${userInfoCoin.rank}"
        mAppTv.text = userInfoCoin.username
    }

    override fun onUserLoginoutResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ARouter.getInstance()
            .build("/userCenter/login")
            .greenChannel()
            .navigation(this, object : EasyNavigationCallback() {
                override fun onArrival(postcard: Postcard?) {
                    super.onArrival(postcard)
                    SPUtil.putString(BaseConstant.LOGINUSERNAME,"")
                    SPUtil.putString(BaseConstant.LOGINUSERPASSWORD,"")
                }
            })
    }


}