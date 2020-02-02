package com.foxcr.kotlineasyshop.ui.fragment

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.presenter.BasePresenter
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.base.ui.fragment.BaseMvpFragment
import com.foxcr.base.utils.DisplayUtils
import com.foxcr.base.utils.ToastUtils
import com.foxcr.base.widgets.OnLikeClickListener
import com.foxcr.base.widgets.RecycleViewDivider
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.adapter.HomeQuestAnswerAdapter
import com.foxcr.kotlineasyshop.data.protocal.HomeRequestAnswerListResp
import com.foxcr.kotlineasyshop.injection.component.DaggerQuestionAnswersComponent
import com.foxcr.kotlineasyshop.injection.module.HomeModule
import com.foxcr.kotlineasyshop.presenter.QuestAnswerPresenter
import com.foxcr.kotlineasyshop.presenter.view.QuestAnswerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_question_answers.*

class QuestionAnswersFragment : BaseMvpFragment<QuestAnswerPresenter>(),QuestAnswerView,
    OnRefreshListener, OnLoadMoreListener, OnLikeClickListener {
    private var datas:MutableList<HomeRequestAnswerListResp.DatasBean> = mutableListOf()
    private val mQuestAnswerAdapter:HomeQuestAnswerAdapter by lazy {
        HomeQuestAnswerAdapter(datas)
    }
    private var page:Int = 0
    override fun resLayoutId(): Int = R.layout.fragment_question_answers

    override fun injectComponent() {
        DaggerQuestionAnswersComponent.builder().activityComponent(activityComponent)
            .homeModule(HomeModule()).build().inject(this)
    }

    override fun initView(view: View) {
        mPresenter.mView = this
        mQuestAnswerSmartRefresh?.apply {
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setOnRefreshListener(this@QuestionAnswersFragment)
            setOnLoadMoreListener(this@QuestionAnswersFragment)
            autoRefresh()
        }

        mQuestAnswerRl?.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(
                RecycleViewDivider(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    DisplayUtils.dp2px(1f),
                    resources.getColor(R.color.common_divider),
                    DisplayUtils.dp2px(15f)
                )
            )
            adapter = mQuestAnswerAdapter
        }

        mQuestAnswerAdapter.setOnLikeClickListener(this)
        initLoveLayout()

    }

    override fun onQuestAnswerResult(homeRequestAnswerListResp: HomeRequestAnswerListResp) {
        if (page == 0){
            datas.clear()
            datas.addAll(homeRequestAnswerListResp.datas)
            mQuestAnswerAdapter.setNewData(datas)
            mQuestAnswerSmartRefresh.finishRefresh()
        }else{
            datas.addAll(homeRequestAnswerListResp.datas)
            mQuestAnswerAdapter.addData(datas)
            mQuestAnswerSmartRefresh.finishLoadMore()
        }
        page ++
        if (page>homeRequestAnswerListResp.pageCount){
            mQuestAnswerSmartRefresh.setEnableLoadMore(false)
        }

    }

    override fun onError(errorMsg: String) {
        super.onError(errorMsg)
        mQuestAnswerSmartRefresh.finishRefresh()
        mQuestAnswerSmartRefresh.finishLoadMore()
    }

    override fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("收藏成功")
    }

    override fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult) {
        ToastUtils.showToast("取消收藏")
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        mPresenter.getQuestAnswerData(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getQuestAnswerData(page)
    }

    override fun onLikeInNetClick(view: View, id: Int) {
        val locations = IntArray(2)
        view.getLocationOnScreen(locations)
        mLoveView.addLoveView(view, locations)
        mPresenter.collectInStandArticle(id)
    }

    override fun onLikeOutNetClick(view: View, title: String, author: String, link: String) {
        val locations = IntArray(2)
        view.getLocationOnScreen(locations)
        mLoveView.addLoveView(view, locations)
        mPresenter.collectOutStandArticle(title, author, link)
    }

    override fun cancelCollectClick(id: Int, originId: Int) {
        mPresenter.uncollectArticle(id, originId)
    }

}