package com.foxcr.base.widgets

import android.content.Context
import android.graphics.Canvas
import android.text.StaticLayout
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 文本右对齐
 */
class MositifyTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var mLineY: Int = 0//总行高
    private var mViewWidth: Int = 0//TextView的总宽度


    init {
        paint.color = currentTextColor
        paint.drawableState = drawableState
    }


    override fun onDraw(canvas: Canvas) {
        mLineY = 0
        mViewWidth = measuredWidth //获取textview的实际宽度
        mLineY += textSize.toInt()

        val text = text.toString()

        val layout = layout
        val lineCount = layout.lineCount
        for (i in 0 until lineCount) {
            //每行循环
            val lineStart = layout.getLineStart(i)
            val lineEnd = layout.getLineEnd(i)
            val lineText = text.substring(lineStart, lineEnd)//获取TextView每行中的内容
            if (needScale(lineText)) {
                if (i == lineCount - 1) {//最后一行不需要重绘
                    canvas.drawText(lineText, 0f, mLineY.toFloat(), paint)
                } else {
                    val width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, paint)
                    drawScaleText(canvas, lineText, width)
                }
            } else {
                canvas.drawText(lineText, 0f, mLineY.toFloat(), paint)
            }
            mLineY += lineHeight//写完一行以后，高度增加一行的高度
        }
    }

    /**
     * 重绘此行.
     *
     * @param canvas    画布
     * @param lineText  该行所有的文字
     * @param lineWidth 该行每个文字的宽度的总和
     */
    private fun drawScaleText(canvas: Canvas, lineText: String, lineWidth: Float) {
        var x = 0f
        var lineTextStr: String = lineText
        if (isFirstLineOfParagraph(lineText)) {
            var blanks = "  "
            canvas.drawText(blanks, x, mLineY.toFloat(), paint)
            val width = StaticLayout.getDesiredWidth(blanks, paint)
            x += width
            lineTextStr = lineText.substring(3)
        }
        //比如说一共有5个字，中间有4个间隔，
        //那就用整个TextView的宽度 - 5个字的宽度，
        //然后除以4，填补到这4个空隙中
        var interval = (mViewWidth - lineWidth) / (lineTextStr.length - 1)
        for (i in lineTextStr.indices) {
            var character = lineTextStr[i].toString()
            val cw = StaticLayout.getDesiredWidth(character, paint)
            canvas.drawText(character, x, mLineY.toFloat(), paint)
            x += (cw + interval)
        }
    }


    /**
     * 判断是不是段落的第一行.
     * 一个汉字相当于一个字符，此处判断是否为第一行的依据是：
     * 字符长度大于3且前两个字符为空格
     *
     * @param lineText 该行所有的文字
     */
    private fun isFirstLineOfParagraph(lineText: String): Boolean {
        return lineText.length > 3 && lineText[0] == ' ' && lineText[1] == ' '
    }

    /**
     * 判断需不需要缩放.
     *
     * @param lineText 该行所有的文字
     * @return true 该行最后一个字符不是换行符  false 该行最后一个字符是换行符
     */
    private fun needScale(lineText: String): Boolean {
        return if (lineText.isEmpty()) {
            false
        } else {
            lineText[lineText.length - 1] != '\n'
        }
    }

}
