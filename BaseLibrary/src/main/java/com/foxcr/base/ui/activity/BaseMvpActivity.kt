package com.foxcr.base.ui.activity
import android.os.Bundle
import androidx.annotation.LayoutRes
import com.foxcr.base.common.BaseApplication
import com.foxcr.base.injection.component.ActivityComponent
import com.foxcr.base.injection.component.DaggerActivityComponent
import com.foxcr.base.injection.module.ActivityModule
import com.foxcr.base.injection.module.LifecycleProvideModule
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.widgets.LoadingDialog
import javax.inject.Inject

open abstract class BaseMvpActivity<T:BasePresenter<*>> : BaseActivity(),BaseView {
    lateinit var activityComponent:ActivityComponent
    private val mLoadingDialog:LoadingDialog by lazy {
        LoadingDialog(this)
    }
    override fun showLoading() {
        if (!mLoadingDialog.isShowing){
            mLoadingDialog.showDialog()
        }
    }

    override fun hideLoading() {
        if (mLoadingDialog.isShowing){
            mLoadingDialog.cancelDialog()
        }
    }

    override fun onError(errorMsg:String) {

    }

    @Inject
    lateinit var mPresenter:T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityComponent()
        setContentView(resLayoutId())
        initView()
    }


    abstract fun initView()

    @LayoutRes
    abstract fun resLayoutId():Int

    private fun initActivityComponent() {
        activityComponent = DaggerActivityComponent.builder().appComponent((application as BaseApplication).appComponent)
            .activityModule(ActivityModule(this)).lifecycleProvideModule(LifecycleProvideModule(this)).build()
    }

    override fun onDestroy() {
        if (mLoadingDialog.isShowing){
            mLoadingDialog.cancelDialog()
        }
        super.onDestroy()
    }
}