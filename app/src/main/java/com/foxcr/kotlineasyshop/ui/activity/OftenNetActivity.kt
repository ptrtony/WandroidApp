package com.foxcr.kotlineasyshop.ui.activity

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.foxcr.base.ui.activity.BaseMvpActivity
import com.foxcr.base.widgets.HeaderBar
import com.foxcr.base.widgets.recyclerview.FlowLayoutManager
import com.foxcr.base.widgets.statusbar.StatusBarUtils
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.OftenNetTagAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeOfenNetResp
import com.foxcr.kotlineasyshop.injection.component.DaggerOftenNetComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.OftenNetPresenter
import com.foxcr.kotlineasyshop.presenter.view.OftenNetView


@Route(path = "/eashshop/oftennet")
class OftenNetActivity : BaseMvpActivity<OftenNetPresenter>(),OftenNetView{
    private lateinit var mOftenNetHb : HeaderBar
    private lateinit var mOftenNetTFL:RecyclerView
    private var datas:MutableList<HomeOfenNetResp> = mutableListOf()
    private val mTagAdapter : OftenNetTagAdapter by lazy {
        OftenNetTagAdapter(this,datas)
    }
    override fun initActivityComponent() {
        DaggerOftenNetComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun resLayoutId(): Int = R.layout.activity_often_net

    override fun initView() {
        StatusBarUtils.setImmersiveStatusBar(this,false)
        StatusBarUtils.setStatusBarColor(this, resources.getColor(com.foxcr.base.R.color.common_blue))
        mPresenter.mView = this
        mPresenter.getOftenNetData()
        mOftenNetHb = findViewById(R.id.mOftenNetHb)
        mOftenNetTFL = findViewById(R.id.mOftenNetTFL)

        mOftenNetHb.setOnClickListener{ finish() }
//        mOftenNetTFL.setOnTagClickListener { view, position, parent ->
//            ARouter.getInstance()
//                .build("/easyshop/web")
//                .withString("url",datas[position].link)
//                .greenChannel()
//                .navigation()
//            return@setOnTagClickListener true
//        }
        mOftenNetTFL.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = mTagAdapter
        }

        mTagAdapter.setOnItemClickListener { adapter, view, position ->
            ARouter.getInstance()
                .build("/easyshop/web")
                .withString("url",datas[position].link)
                .greenChannel()
                .navigation()
        }
    }

    override fun onOftenNetResult(oftenNetDatas: List<HomeOfenNetResp>) {
        if (oftenNetDatas.isNotEmpty()){
            datas.clear()
            datas.addAll(oftenNetDatas)
            mTagAdapter.setNewData(datas)
        }
    }
}