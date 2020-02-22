package com.foxcr.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.foxcr.base.common.BaseApplication
import com.foxcr.base.injection.component.ActivityComponent
import com.foxcr.base.injection.component.DaggerActivityComponent
import com.foxcr.base.injection.module.ActivityModule
import com.foxcr.base.injection.module.LifecycleProvideModule
import com.trello.rxlifecycle3.components.support.RxFragment

open abstract class  BaseFragment : RxFragment(){
    lateinit var activityComponent: ActivityComponent
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resLayoutId(),null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityComponent = DaggerActivityComponent.builder().appComponent((activity?.applicationContext as BaseApplication).appComponent)
            .activityModule(ActivityModule(activity!!)).lifecycleProvideModule(
                LifecycleProvideModule(this)
            ).build()
        injectComponent()
        initView(view)
    }

    @LayoutRes
    abstract fun resLayoutId():Int

    /**
     * 注册dagger2
     */
    abstract fun injectComponent()

    /**
     * 初始化视图
     */
    abstract fun initView(view:View)

}