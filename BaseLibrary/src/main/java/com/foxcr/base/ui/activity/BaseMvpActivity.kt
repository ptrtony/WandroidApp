package com.foxcr.base.ui.activity
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.widgets.LoadingDialog

open class BaseMvpActivity<T:BasePresenter<*>> : BaseActivity(),BaseView {
    private val mLoadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }
    override fun showLoading() {
        if (!mLoadingDialog.isShowing){
            mLoadingDialog.show()
        }
    }

    override fun hideLoading() {
        if (mLoadingDialog.isShowing){
            mLoadingDialog.cancel()
        }
    }

    override fun onError() {

    }

    lateinit var mPresenter:T

    override fun onDestroy() {
        super.onDestroy()
        if (mLoadingDialog.isShowing){
            mLoadingDialog.cancel()
        }
    }
}