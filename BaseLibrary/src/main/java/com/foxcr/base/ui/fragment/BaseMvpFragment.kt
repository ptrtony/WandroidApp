package com.foxcr.base.ui.fragment
import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.LoadingDialog
import com.foxcr.base.widgets.LoveLayout
import javax.inject.Inject

open abstract class BaseMvpFragment<T:BasePresenter<*>> : BaseFragment(),BaseView {
    lateinit var mLoveView:LoveLayout
    private val mLoadingDialog:LoadingDialog by lazy {
        LoadingDialog(activity!!)
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

    fun initLoveLayout(){
        mLoveView = LoveLayout(context,null)
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT)
        layoutParams.gravity = Gravity.TOP
        layoutParams.x = 0
        layoutParams.y = 0
        windowManager.addView(mLoveView,layoutParams)
    }

    override fun onError(errorMsg:String) {
        if (!(errorMsg == null || errorMsg == "null")) ToastUtils.showToast(errorMsg)
    }

    @Inject
    lateinit var mPresenter:T




    private fun initActivityComponent() {
    }

    override fun onDestroy() {
        if (mLoadingDialog.isShowing){
            mLoadingDialog.cancelDialog()
        }
        super.onDestroy()
    }

}