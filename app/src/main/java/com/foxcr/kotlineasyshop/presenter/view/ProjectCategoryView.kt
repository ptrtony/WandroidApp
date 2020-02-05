package com.foxcr.kotlineasyshop.presenter.view

import com.foxcr.base.data.protocal.BaseNoneResponseResult
import com.foxcr.base.presenter.view.BaseView
import com.foxcr.kotlineasyshop.data.protocal.ProjectCategoryListResp
import com.foxcr.kotlineasyshop.data.protocal.ProjectCategoryTreeResp

interface ProjectCategoryView:BaseView {
    fun onProjectCategoryTreeResult(projectCategoryTreeDatas:List<ProjectCategoryTreeResp>)
    fun onProjectCategoryListResult(projectCategoryListResp: ProjectCategoryListResp)

    fun onCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
    fun onUnCollectSuccessResult(baseNoneResponseResult: BaseNoneResponseResult)
}