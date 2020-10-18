package com.taz.customapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import com.taz.customapplication.CurveTextSeekBar.Companion.DEFAULT_MAX_VALUE
import com.taz.customapplication.CurveTextSeekBar.Companion.DEFAULT_PROGRESS_BAR_COLOR
import com.taz.customapplication.CurveTextSeekBar.Companion.DEFAULT_THUMB_COLOR
import com.taz.customapplication.CurveTextSeekBar.Companion.DEFAULT_THUMB_STROKE_COLOR
import java.lang.Math.min


class CurveSeekBar(context: Context?, attrs: AttributeSet?) :
    AppCompatSeekBar(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val progressBarPath = Path()
    var thumbColor = DEFAULT_THUMB_COLOR
    var progressBarColor = DEFAULT_PROGRESS_BAR_COLOR
    var thumbStrokeColor = DEFAULT_THUMB_STROKE_COLOR
    var thresholdValue = DEFAULT_MAX_VALUE

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure((0.6 * widthMeasureSpec).toInt(), heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawProgressBar(canvas!!)
    }

    private fun drawProgressBar(canvas: Canvas) {

        val fraction =    min(thresholdValue, progress).toFloat() / max.toFloat()
        val centerPos = 0.05f + (fraction) * 0.90f
        val leftSEnd = centerPos - 0.05f
        val rightStart = centerPos + 0.05f
        progressBarPath.reset()
        progressBarPath.moveTo(width * 0.0f, height * 0.35f)
        progressBarPath.lineTo(width * leftSEnd, height * 0.35f)
        progressBarPath.quadTo(
            width * centerPos,
            height * 0.15f,
            width * rightStart,
            height * 0.35f
        )
        progressBarPath.lineTo(width * 1f, height * 0.35f)
        paint.color = progressBarColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4.0f
        canvas.drawPath(progressBarPath, paint)
        progressDrawable = null
        val thumbCircle = GradientDrawable()
        thumbCircle.setColor(thumbColor)
        thumbCircle.shape = GradientDrawable.OVAL
        val size:Int = (height * 0.3f).toInt()
        if(progress > thresholdValue)   thumbCircle.setSize(size/2, size/2)
        else    thumbCircle.setSize(size, size)
        thumbCircle.setStroke(2, thumbStrokeColor)
        thumb = thumbCircle

    }


}
