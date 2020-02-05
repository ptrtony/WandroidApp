package com.foxcr.base.ui.activity

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.foxcr.base.R
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.LoadingDialog
import com.foxcr.base.widgets.LoveLayout
import javax.inject.Inject

open abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {
    lateinit var mLoveView: LoveLayout
    private val mLoadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    override fun showLoading() {
        if (!mLoadingDialog.isShowing) {
            mLoadingDialog.showDialog()
        }
    }

    override fun hideLoading() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.cancelDialog()
        }
    }

    override fun onError(errorMsg: String) {
        ToastUtils.showToast(errorMsg)
    }

    @Inject
    lateinit var mPresenter: T


    override fun onDestroy() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.cancelDialog()
        }
        super.onDestroy()
    }

    fun initLoveLayout() {
        mLoveView = LoveLayout(this, null)
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )
        layoutParams.gravity = Gravity.TOP
        layoutParams.x = 0
        layoutParams.y = 0
        windowManager.addView(mLoveView, layoutParams)
    }

    /**
     * recyclerview列表为空显示的布局
     */
    fun emptyView(): View {
        return View.inflate(this, R.layout.empty_view,null)
    }
}