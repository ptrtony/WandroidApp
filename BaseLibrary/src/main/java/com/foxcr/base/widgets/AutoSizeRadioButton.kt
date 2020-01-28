package com.foxcr.base.widgets

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.RadioButton
import com.foxcr.base.R
import com.foxcr.base.utils.DisplayUtils
import kotlin.math.abs

class AutoSizeRadioButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RadioButton(context, attributeSet, defStyleAttr) {
    private var largeSize: Int = 0
    private var smallSize: Int = 0
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bounds = Rect()
    private var fraction = 0f
    private var autoTextColor:Int
    private var scaleAnimator:ObjectAnimator?=null
    private var isLargeSize:Boolean
    init {
        val typeArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.AutoSizeRadioButton)
        largeSize = typeArray.getDimensionPixelSize(
            R.styleable.AutoSizeRadioButton_largeSize,
            DisplayUtils.dp2px(24.toFloat())
        )
        autoTextColor = typeArray.getColor(R.styleable.AutoSizeRadioButton_autoTextColor,Color.parseColor("#333333"))
        smallSize = typeArray.getDimensionPixelSize(
            R.styleable.AutoSizeRadioButton_smallSize,
            DisplayUtils.dp2px(18.toFloat())
        )
        isLargeSize = typeArray.getBoolean(R.styleable.AutoSizeRadioButton_isLargeSize,false)
        typeArray.recycle()
        autoSizeAnimation(isLargeSize)

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val text = text.toString()
        paint.textSize = smallSize + (largeSize - smallSize)*fraction
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = DisplayUtils.dp2px(4f).toFloat()

        paint.color = autoTextColor
        paint.getTextBounds(text, 0, text.length, bounds)
        val offsetX = (bounds.right - bounds.left) / 2f
        val offsetY = (bounds.bottom - bounds.top) / 2f
        canvas.drawText(text, abs(width / 2f - offsetX), abs(height / 2f - offsetY),paint)
    }

    fun setFraction(fraction:Float){
        this.fraction = fraction
        invalidate()
    }


    fun getFraction():Float{
        return fraction
    }


    private fun getScaleAnimator(): ObjectAnimator{
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "fraction", 0f)
        }
        scaleAnimator!!.setFloatValues(0.toFloat(), 1.toFloat())
        return scaleAnimator!!
    }

    fun autoSizeAnimation(isLargeScale:Boolean){
        if (isLargeScale){
            autoTextColor = Color.parseColor("#999999")
            getScaleAnimator().reverse()
        }else{
            autoTextColor = Color.parseColor("#333333")
            getScaleAnimator().start()
        }
    }





}