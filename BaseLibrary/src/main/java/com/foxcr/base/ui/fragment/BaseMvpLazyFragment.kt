package com.foxcr.base.ui.fragment
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.foxcr.base.R
import com.foxcr.base.common.BaseApplication
import com.foxcr.base.injection.component.ActivityComponent
import com.foxcr.base.injection.component.DaggerActivityComponent
import com.foxcr.base.injection.module.ActivityModule
import com.foxcr.base.injection.module.LifecycleProvideModule
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.LoadingDialog
import com.foxcr.base.widgets.LogUtils
import com.foxcr.base.widgets.LoveLayout
import com.trello.rxlifecycle3.components.support.RxFragment
import javax.inject.Inject

open abstract class BaseMvpLazyFragment<T:BasePresenter<*>> : RxFragment(),BaseView {
    lateinit var activityComponent: ActivityComponent
    private var rootView: View?=null
    private var mIsFirstVisible : Boolean = true
    private var isViewCreated : Boolean = false
    private var currentVisibleState:Boolean = false
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null){
            rootView = inflater.inflate(resLayoutId(), container, false)
        }
        activityComponent = DaggerActivityComponent.builder().appComponent((activity?.applicationContext as BaseApplication).appComponent)
            .activityModule(ActivityModule(activity!!)).lifecycleProvideModule(
                LifecycleProvideModule(this)
            ).build()
        injectComponent()
        initView(rootView!!)
        return rootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        // 对于默认 tab 和 间隔 checked tab 需要等到 isViewCreated = true 后才可以通过此通知用户可见
        // 这种情况下第一次可见不是在这里通知 因为 isViewCreated = false 成立,等从别的界面回到这里后会使用 onFragmentResume 通知可见
        // 对于非默认 tab mIsFirstVisible = true 会一直保持到选择则这个 tab 的时候，因为在 onActivityCreated 会返回 false
        if (isViewCreated) {
            if (isVisibleToUser && !currentVisibleState) {
                dispatchUserVisibleHint(true)
            } else if (!isVisibleToUser && currentVisibleState) {
                dispatchUserVisibleHint(false)
            }
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        // !isHidden() 默认为 true  在调用 hide show 的时候可以使用
        if (!isHidden && userVisibleHint) {
            dispatchUserVisibleHint(true)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogUtils.e(javaClass.simpleName + "  onHiddenChanged dispatchChildVisibleState  hidden " + hidden)

        if (hidden) {
            dispatchUserVisibleHint(false)
        } else {
            dispatchUserVisibleHint(true)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!mIsFirstVisible) {
            if (!isHidden && !currentVisibleState && userVisibleHint) {
                dispatchUserVisibleHint(true)
            }
        }
    }


    override fun onPause() {
        super.onPause()
        // 当前 Fragment 包含子 Fragment 的时候 dispatchUserVisibleHint 内部本身就会通知子 Fragment 不可见
        // 子 fragment 走到这里的时候自身又会调用一遍 ？
        if (currentVisibleState && userVisibleHint) {
            dispatchUserVisibleHint(false)
        }
    }

    /**
     * 统一处理 显示隐藏
     *
     * @param visible
     */
    private fun dispatchUserVisibleHint(visible:Boolean){
        //当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment getUserVisibleHint = true
        //但当父 fragment 不可见所以 currentVisibleState = false 直接 return 掉
        // 这里限制则可以限制多层嵌套的时候子 Fragment 的分发
        if (visible && isParentInvisible()) return
        //此处是对子 Fragment 不可见的限制，因为 子 Fragment 先于父 Fragment回调本方法 currentVisibleState 置位 false
        // 当父 dispatchChildVisibleState 的时候第二次回调本方法 visible = false 所以此处 visible 将直接返回
        if (currentVisibleState == visible) {
            return
        }

        currentVisibleState = visible
        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false
                onFragmentFirstVisible()
            }
            onFragmentResume()
            dispatchChildVisibleState(true)
        } else {
            dispatchChildVisibleState(false)
            onFragmentPause()
        }
    }


    /**
     * 用于分发可见时间的时候父获取 fragment 是否隐藏
     *
     * @return true fragment 不可见， false 父 fragment 可见
     */
    private fun isParentInvisible() :Boolean{
        val parentFragment = parentFragment
        return if (parentFragment is  BaseMvpLazyFragment<*>) {
            !parentFragment.isSupportVisible()
        }else {
            false
        }
    }

    private fun isSupportVisible() : Boolean {
        return currentVisibleState
    }

    /**
     * 当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment 的唯一或者嵌套 VP 的第一 fragment 时 getUserVisibleHint = true
     * 但是由于父 Fragment 还进入可见状态所以自身也是不可见的， 这个方法可以存在是因为庆幸的是 父 fragment 的生命周期回调总是先于子 Fragment
     * 所以在父 fragment 设置完成当前不可见状态后，需要通知子 Fragment 我不可见，你也不可见，
     * <p>
     * 因为 dispatchUserVisibleHint 中判断了 isParentInvisible 所以当 子 fragment 走到了 onActivityCreated 的时候直接 return 掉了
     * <p>
     * 当真正的外部 Fragment 可见的时候，走 setVisibleHint (VP 中)或者 onActivityCreated (hide show) 的时候
     * 从对应的生命周期入口调用 dispatchChildVisibleState 通知子 Fragment 可见状态
     *
     * @param visible
     */

    private fun dispatchChildVisibleState(visible:Boolean) {
        val childFragmentManager = childFragmentManager
        val fragments = childFragmentManager.fragments
        if (fragments.isNotEmpty()) {
            fragments.forEach { fragment->
                if (fragment is BaseMvpLazyFragment<*> && !fragment.isHidden() && fragment.getUserVisibleHint() && fragment.isViewCreated) {
                    (fragment.dispatchUserVisibleHint(visible))
                }
            }
        }
    }


    /**
     * 对用户第一次可见
     */
    abstract fun onFragmentFirstVisible()


    fun onFragmentResume() {
        LogUtils.e(javaClass.simpleName + "  对用户可见")
    }

    fun onFragmentPause() {
        LogUtils.e(javaClass.simpleName + "  对用户不可见")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isViewCreated = false
        mIsFirstVisible = true
    }


    fun initLoveLayout(){
        mLoveView = LoveLayout(context,null)
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT)
        layoutParams.gravity = Gravity.TOP
        layoutParams.x = 0
        layoutParams.y = DisplayUtils.dp2px(24f)
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

    /**
     * recyclerview列表为空显示的布局
     */
    fun emptyView(parent:ViewGroup):View{
        return View.inflate(activity,R.layout.empty_view,null)
    }

    fun footerView():View{
        return View.inflate(activity,R.layout.item_not_more_data,null)
    }

}