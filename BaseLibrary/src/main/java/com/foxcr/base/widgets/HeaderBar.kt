package com.foxcr.base.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.foxcr.base.R
import com.foxcr.base.ext.onClick
import kotlinx.android.synthetic.main.layout_header_bar.view.*

class HeaderBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var isShowBack = true
    private var titleText: String? = null
    private var rightText: String? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)
        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack,true)
        titleText = typedArray.getString(R.styleable.HeaderBar_titleText)
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText)
        typedArray.recycle()

        initView()
    }

    private fun initView() {
        View.inflate(context,R.layout.layout_header_bar,this)
        mLeftIv.visibility = if (isShowBack) View.VISIBLE else View.GONE
        titleText?.let { mTitleTv.text = it }
        rightText?.let { mRightTv.text = it }
    }

    /**
     * 点击右边文字
     */
    fun onRightClickListener(method:()->Unit){
        mRightTv.setOnClickListener{
            method()
        }
    }

    /**
     * 点击回退键
     */
    fun onBackClickListener(method:()->Unit){
        mLeftIv.setOnClickListener{
            method()
        }
    }


}