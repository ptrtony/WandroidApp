package com.foxcr.base.widgets

interface OnRefreshOrLoadMoreListener {
    fun finishRefresh()
    fun finishLoadMore()
    fun enableLoadMore(isLoadMore: Boolean)
    fun enableRefresh(isRefresh: Boolean)
    fun loadPage(page: Int, type: Int)

    companion object {
        const val NEWBLOGTYPE = 1
        const val NEWPROJECT = 2
    }
}