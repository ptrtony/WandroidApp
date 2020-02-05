package com.foxcr.kotlineasyshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foxcr.base.widgets.ColoredTextView
import com.foxcr.kotlineasyshop.R
import com.foxcr.kotlineasyshop.data.protocal.HomeNavigationResp
import com.foxcr.kotlineasyshop.ui.fragment.NavigationFragment.Companion.CONTENT

class NavigationListTwoAdapter constructor(
    val context: Context,
    var datas: MutableList<HomeNavigationResp.ArticlesBean>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == CONTENT) return ContentViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_navigation_list_two,
                parent,
                false
            )
        )
        return TitleViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_navigation_head_title,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    fun setNewData(data: MutableList<HomeNavigationResp.ArticlesBean>?) {
        this.datas = data ?: mutableListOf()
        notifyDataSetChanged()
    }

    fun addData(data: MutableList<HomeNavigationResp.ArticlesBean>) {
        datas.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TitleViewHolder) {
            holder.mTitleTv.text = datas[position].chapterName
        } else if (holder is ContentViewHolder) {
            holder.mContentView.text = datas[position].title
        }
        holder.itemView.setOnClickListener {
            onListTwoClickListener?.onNavigationListTwoClick(holder.itemView, holder.layoutPosition)
        }

    }

    class TitleViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        val mTitleTv: TextView = view.findViewById(R.id.mHeadTitleTv)

        init {
            itemView.tag = true
        }
    }

    class ContentViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        val mContentView: ColoredTextView = view.findViewById(R.id.mColorTextView)

        init {
            itemView.tag = false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return datas[position].itemType
    }

    private var onListTwoClickListener: OnNavigationListTwoClickListener? = null
    fun setOnNavigationListTwoClickListener(onListTwoClickListener: OnNavigationListTwoClickListener) {
        this.onListTwoClickListener = onListTwoClickListener
    }

    interface OnNavigationListTwoClickListener {
        fun onNavigationListTwoClick(view: View, position: Int)
    }


}