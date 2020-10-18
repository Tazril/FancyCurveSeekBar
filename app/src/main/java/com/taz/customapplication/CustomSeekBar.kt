package com.taz.customapplication

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class CustomSeekBar(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    companion object {
        private const val DEFAULT_BUBBLE_COLOR = Color.DKGRAY
        private const val DEFAULT_THUMB_COLOR = Color.RED
        private const val DEFAULT_PROGRESS_BAR_COLOR = Color.BLUE
        private const val DEFAULT_BUBBLE_TEXT_COLOR = Color.WHITE
        private const val DEFAULT_MAX_VALUE = 100
        private const val DEFAULT_PROGRESS = 0
        private const val DEFAULT_STROKE_WIDTH = 6
    }

    private var bubbleColor = DEFAULT_BUBBLE_COLOR
    private var thumbColor = DEFAULT_THUMB_COLOR
    private var progressBarColor = DEFAULT_PROGRESS_BAR_COLOR
    private var bubbleTextColor = DEFAULT_BUBBLE_TEXT_COLOR
    private var maxValue = DEFAULT_MAX_VALUE
    var progress = DEFAULT_PROGRESS

    private var textView: TextView? = null
    private var seekBar: SeekBar? = null

    var onProgressUpdate: (( SeekBar, Int,Boolean) -> Unit)? = null
    var onProgressStart: (( SeekBar) -> Unit)? = null
    var onProgressStop: (( SeekBar) -> Unit)? = null

    init {
        loadAttributes(attrs)
        updateDrawables()
        loadLayout()
        seekBarOnChange()
    }

    private fun loadAttributes(attributeSet: AttributeSet?) {
        attributeSet?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomSeekBar, 0, 0)
            try {
//                bubbleTextColor =
//                    typedArray.getInt(
//                        R.styleable.CustomSeekBar_bubbleTextColor,
//                        DEFAULT_BUBBLE_TEXT_COLOR
//                    )
//                maxValue = typedArray.getInt(R.styleable.CustomSeekBar_xmaxValue, DEFAULT_MAX_VALUE)
//                progress = typedArray.getInt(R.styleable.CustomSeekBar_xprogress, DEFAULT_PROGRESS)
//                bubbleColor =
//                    typedArray.getColor(R.styleable.CustomSeekBar_bubbleColor, DEFAULT_BUBBLE_COLOR)
//                thumbColor =
//                    typedArray.getColor(R.styleable.CustomSeekBar_xthumbColor, DEFAULT_THUMB_COLOR)
//                progressBarColor = typedArray.getColor(
//                    R.styleable.CustomSeekBar_xprogressBarColor,
//                    DEFAULT_PROGRESS_BAR_COLOR
//                )
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun updateDrawables() {
        val thumbLayer = ContextCompat.getDrawable(context, R.drawable.thumb) as LayerDrawable
        (thumbLayer.getDrawable(2) as GradientDrawable).setColor(thumbColor)
        val semiCircle =
            ContextCompat.getDrawable(context, R.drawable.semicircle) as GradientDrawable
        semiCircle.setStroke(DEFAULT_STROKE_WIDTH,progressBarColor)
        val progressBar =
            ContextCompat.getDrawable(context, R.drawable.seekbarbackground) as GradientDrawable
        progressBar.setStroke(DEFAULT_STROKE_WIDTH,progressBarColor)
        val bubbleTextBg =
            ContextCompat.getDrawable(context, R.drawable.bubbletext) as GradientDrawable
        bubbleTextBg.setColor(bubbleColor)
    }

    private fun loadLayout() {

        val root = LayoutInflater.from(context).inflate(R.layout.seekbar, this, true)

        seekBar = root.findViewById(R.id.seekBar)
        textView = root.findViewById(R.id.textView) as TextView
        textView?.setTextColor(bubbleTextColor)
        seekBar?.max = maxValue
        seekBar?.progress = progress
        (seekBar?.progressDrawable as GradientDrawable).setColor(progressBarColor)
        textView?.setText(progress.toString());

    }

    private fun seekBarOnChange() {
        seekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                val percentage = ((seek.progress.toDouble() / maxValue.toDouble()) * 100).toInt()
                val displayText = "$percentage%"
                textView?.setText(displayText)
                val width = (seekBar!!.width
                        - seekBar!!.paddingLeft
                        - seekBar!!.paddingRight)
                val textPos = (seekBar!!.paddingLeft
                        + (width - textView!!.width)
                        * (percentage) /100 )
                textView?.setX(textPos.toFloat())
                onProgressUpdate?.invoke(seek,percentage,fromUser)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                textView?.visibility = View.VISIBLE
                onProgressStart?.invoke(seek)
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                textView?.visibility = View.INVISIBLE
                onProgressStop?.invoke(seek)
            }
        })
    }


}