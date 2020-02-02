package com.foxcr.base.ui.activity
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.LoadingDialog
import javax.inject.Inject

open abstract class BaseMvpActivity<T:BasePresenter<*>> : BaseActivity(),BaseView {
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
        ToastUtils.showToast(errorMsg)
    }

    @Inject
    lateinit var mPresenter:T


    override fun onDestroy() {
        if (mLoadingDialog.isShowing){
            mLoadingDialog.cancelDialog()
        }
        super.onDestroy()
    }
}