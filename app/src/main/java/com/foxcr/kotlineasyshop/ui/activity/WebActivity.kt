package com.foxcr.kotlineasyshop.ui.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Message
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.ui.activity.BaseActivity
import com.foxcr.kotlineasyshop.R
import kotlinx.android.synthetic.main.activity_web.*


@Route(path = "/easyshop/web")
class WebActivity : BaseActivity() {
    @Autowired(name = "url")
    @JvmField var mUrl: String = ""

    private var mWebView: WebView? = null
    private lateinit var mLoadingPb: ProgressBar
    override fun initActivityComponent() {

    }

    override fun resLayoutId(): Int = R.layout.activity_web
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        ARouter.getInstance().inject(this)
        mWebView = findViewById(R.id.mWebView)
        mLoadingPb = findViewById(R.id.mLoadingPb)
        mHeaderBar.onBackClickListener { finish() }

        val mWebSettings = mWebView?.settings
        mWebSettings?.apply {
            setSupportZoom(true)
            loadWithOverviewMode = true
            useWideViewPort = true
            defaultTextEncodingName = "utf-8"
            loadsImagesAutomatically = true

            //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
            javaScriptEnabled = true

            saveData(mWebSettings)

            newWin(mWebSettings)
        }

        mWebView?.apply {
            webChromeClient = mWebChromeClient
            webViewClient = mWebViewClient
            loadUrl(mUrl)
        }


    }

    override fun onResume() {
        super.onResume()

        mWebView?.apply {
            //恢复webview的状态（不靠谱）
            resumeTimers()
            //激活webView的状态，能正常加载网页
            onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        mWebView?.apply {
            //当页面被失去焦点被切换到后台不可见状态，需要执行onPause
            //通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
            onPause()
            //当应用程序(存在webview)被切换到后台时，这个方法不仅仅针对当前的webview而是全局的全应用程序的webview
            //它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。（不靠谱）
            pauseTimers()
        }
    }

    //在关闭了Activity时，如果Webview的音乐或视频，还在播放。就必须销毁Webview
    //但是注意：webview调用destory时,webview仍绑定在Activity上
    //这是由于自定义webview构建时传入了该Activity的context对象
    //因此需要先从父容器中移除webview,然后再销毁webview:
    override fun onDestroy() {
        super.onDestroy()
        mWebView?.apply {
            clearHistory()
            (parent as ViewGroup).removeView(this)
            loadUrl("about:blank")
            stopLoading()
            webChromeClient = null
            webViewClient = null
            destroy()
            mWebView = null
        }
    }

    /**
     * 多窗口的问题
     */
    private fun newWin(mWebSettings: WebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(false)
        mWebSettings.javaScriptCanOpenWindowsAutomatically = true
    }


    /**
     * HTML5数据存储
     */
    private fun saveData(mWebSettings: WebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        mWebSettings.domStorageEnabled = true
        mWebSettings.databaseEnabled = true
        mWebSettings.setAppCacheEnabled(true)
        val appCachePath = applicationContext.cacheDir.absolutePath
        mWebSettings.setAppCachePath(appCachePath)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView?.canGoBack()!!) {
            mWebView?.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            url: String?
        ): Boolean {
            view?.loadUrl(url)
            return true
        }
    }

    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
            super.onReceivedIcon(view, icon)
        }

        override fun onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt()
        }

        override fun onGeolocationPermissionsShowPrompt(
            origin: String?,
            callback: GeolocationPermissions.Callback?
        ) {
            //注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            callback?.invoke(origin, true, false)
            super.onGeolocationPermissionsShowPrompt(origin, callback)
        }

        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            val transport = resultMsg?.obj as WebView.WebViewTransport
            transport.webView = view
            resultMsg.sendToTarget()
            return true
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100){
                mLoadingPb.visibility = View.GONE
            }else{
                mLoadingPb.progress = newProgress
            }
        }
    }


}