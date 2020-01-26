package com.foxcr.base.ui.activity

import android.os.Bundle
import android.view.MotionEvent
import androidx.annotation.LayoutRes
import com.foxcr.base.common.AppManager
import com.foxcr.base.common.BaseApplication
import com.foxcr.base.injection.component.ActivityComponent
import com.foxcr.base.injection.component.DaggerActivityComponent
import com.foxcr.base.injection.module.ActivityModule
import com.foxcr.base.injection.module.LifecycleProvideModule
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

open abstract class BaseActivity : RxAppCompatActivity() {
    private var lastClickTime: Long = 0
    lateinit var activityComponent: ActivityComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBaseActivityComponent()
        AppManager.instance.addActivity(this)
        setContentView(resLayoutId())
        initActivityComponent()
        initView()
    }

    /**
     * 初始化dagger2组件
     */
    abstract fun initActivityComponent()

    /**
     * 布局资源id
     */
    @LayoutRes
    abstract fun resLayoutId(): Int

    /**
     * 初始化view
     */
    abstract fun initView()

    override fun onDestroy() {
        AppManager.instance.finishActivity(this)
        super.onDestroy()
    }

    private fun initBaseActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
            .appComponent((application as BaseApplication).appComponent)
            .activityModule(ActivityModule(this))
            .lifecycleProvideModule(LifecycleProvideModule(this)).build()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {
                return true
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isFastDoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        lastClickTime = time
        return timeD <= 300
    }
}