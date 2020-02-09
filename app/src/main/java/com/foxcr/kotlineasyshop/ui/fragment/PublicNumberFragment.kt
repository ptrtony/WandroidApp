package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foxcr.base.ext.hideKeyboard
import com.foxcr.base.ui.fragment.BaseMvpLazyFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.base.widgets.recyclerview.CenterLayoutManager
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.WxArticleChapterAdapter
import com.foxcr.kotlineasyshop.data.protocal.WxArticleChaptersResp
import com.foxcr.kotlineasyshop.injection.component.DaggerPublicNumberComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.WxArticleChapterPresenter
import com.foxcr.kotlineasyshop.presenter.view.WxArticleChaptersView

class PublicNumberFragment : BaseMvpLazyFragment<WxArticleChapterPresenter>(),
    WxArticleChaptersView, WxArticleChapterAdapter.OnWxArticleChapterClickListener {
    private lateinit var mWxChapterRl: RecyclerView
    private lateinit var mWxFrameLayout: FrameLayout
    private lateinit var mSearchContainerEtn: EditText
    private var wxArticleId: Int = 0
    private lateinit var centerLayoutManager: CenterLayoutManager
    private val wxArticleFragment: WxArticleFragment by lazy {
        WxArticleFragment()
    }

    private var mData: MutableList<WxArticleChaptersResp> = mutableListOf()
    private val mWxArticleChapterAdapter: WxArticleChapterAdapter by lazy {
        WxArticleChapterAdapter(mData)
    }

    override fun resLayoutId(): Int = R.layout.fragment_public_number

    override fun injectComponent() {
        DaggerPublicNumberComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun initView(view: View) {
        mPresenter.mView = this
        mWxChapterRl = view.findViewById(R.id.mWxChapterRl)
        mWxFrameLayout = view.findViewById(R.id.mWxFrameLayout)
        mSearchContainerEtn = view.findViewById(R.id.mSearchContainerEtn)

        centerLayoutManager = CenterLayoutManager(activity, CenterLayoutManager.VERTICAL, false)
        mWxChapterRl.apply {
            layoutManager = centerLayoutManager
            addItemDecoration(
                RecycleViewDivider(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    DisplayUtils.dp2px(1f),
                    resources.getColor(R.color.common_divider),
                    DisplayUtils.dp2px(15f)
                )
            )
            adapter = mWxArticleChapterAdapter
        }
        childFragmentManager.beginTransaction()
            .replace(R.id.mWxFrameLayout, wxArticleFragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        mSearchContainerEtn.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                WxArticleFragment.TYPE = 2
                mSearchContainerEtn.hideKeyboard()
                val key = mSearchContainerEtn.text.toString().trim()
                wxArticleFragment.getWxArticleListData(wxArticleId, key, false)
            }
            return@setOnEditorActionListener true
        }
        mWxArticleChapterAdapter.setWxArticleClickListener(this)
        mWxArticleChapterAdapter.openLoadAnimation()
    }

    override fun onFragmentFirstVisible() {
        mPresenter.getWxArticleChaptersData()
    }

    override fun onWxArticleChaptersResult(wxArticleChapers: List<WxArticleChaptersResp>) {
        WxArticleFragment.TYPE = 1
        wxArticleId = wxArticleChapers[0].id
        wxArticleFragment.getWxArticleListData(wxArticleChapers[0].id, "", false)
        mData.clear()
        wxArticleChapers[0].isSuccess = true
        mData.addAll(wxArticleChapers)
        mWxArticleChapterAdapter.setNewData(mData)
    }

    override fun onWxArticleChapterClick(
        view: View,
        position: Int,
        wxArticleChaptersResp: WxArticleChaptersResp
    ) {
        mSearchContainerEtn.hideKeyboard()
        WxArticleFragment.TYPE = 1
        wxArticleId = wxArticleChaptersResp.id
        mData.forEach {
            it.isSuccess = false
        }
        mData[position].isSuccess = true
        centerLayoutManager.smoothScrollToPosition(
            mWxChapterRl, RecyclerView.State(),
            position
        )
        mWxArticleChapterAdapter.notifyDataSetChanged()
        wxArticleFragment.getWxArticleListData(wxArticleId, "", false)
    }


}