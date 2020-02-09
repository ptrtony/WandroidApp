package com.foxcr.kotlineasyshop.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.postDelayed
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.common.EasyNavigationCallback
import com.foxcr.kotlineasyshop.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        setContentView(R.layout.activity_splash)
        mSplashIv.postDelayed({
            ARouter.getInstance()
                .build("/easyshop/main")
                .greenChannel()
                .navigation(this, object : EasyNavigationCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        super.onArrival(postcard)
                        finish()
                    }
                })
        }, 2000)

    }
}
