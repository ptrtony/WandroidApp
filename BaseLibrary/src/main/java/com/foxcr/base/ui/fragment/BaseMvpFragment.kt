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
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.widgets.LoadingDialog
import javax.inject.Inject

open abstract class BaseMvpFragment<T:BasePresenter<*>> : BaseFragment(),BaseView {
    lateinit var activityComponent:ActivityComponent
    private val mLoadingDialog:LoadingDialog by lazy {

        LoadingDialog(activity?.applicationContext!!)
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resLayoutId(),null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActivityComponent()
        injectComponent()
        initView()
    }



    abstract fun initView()

    abstract fun injectComponent()

    @LayoutRes
    abstract fun resLayoutId():Int

    private fun initActivityComponent() {
        activityComponent = DaggerActivityComponent.builder().appComponent((activity?.applicationContext as BaseApplication).appComponent)
            .activityModule(ActivityModule(activity!!)).lifecycleProvideModule(LifecycleProvideModule(this)).build()
    }

    override fun onDestroy() {
        if (mLoadingDialog.isShowing){
            mLoadingDialog.cancelDialog()
        }
        super.onDestroy()
    }
}