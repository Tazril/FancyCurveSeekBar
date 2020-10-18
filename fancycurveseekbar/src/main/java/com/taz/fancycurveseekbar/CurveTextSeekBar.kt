package com.taz.fancycurveseekbar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import java.lang.Math.min


class CurveTextSeekBar(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    companion object {
        const val DEFAULT_TEXT_BOX_COLOR = Color.DKGRAY
        const val DEFAULT_THUMB_COLOR = Color.WHITE
        const val DEFAULT_THUMB_STROKE_COLOR = Color.BLACK
        const val DEFAULT_PROGRESS_BAR_COLOR = Color.BLUE
        const val DEFAULT_TEXT_COLOR = Color.WHITE
        const val DEFAULT_MAX_VALUE = 100
        const val DEFAULT_PROGRESS = 0
    }

    private var textBoxColor = DEFAULT_TEXT_BOX_COLOR
    private var thumbColor = DEFAULT_THUMB_COLOR
    private var thumbStrokeColor = DEFAULT_THUMB_COLOR
    private var progressBarColor = DEFAULT_PROGRESS_BAR_COLOR
    private var textColor = DEFAULT_TEXT_COLOR
    private var maxValue = DEFAULT_MAX_VALUE
    private var thresholdValue = DEFAULT_MAX_VALUE
    private var progress = DEFAULT_PROGRESS

    init {
        loadAttributes(attrs)
        loadLayout()
        seekBarOnChange()
    }

    private lateinit var textView: TextView
    private lateinit var seekBar: CurveSeekBar

    var onProgressUpdate: ((SeekBar, Int, Boolean) -> Unit)? = null
    var onProgressStart: ((SeekBar) -> Unit)? = null
    var onProgressStop: ((SeekBar) -> Unit)? = null

    private fun loadAttributes(attributeSet: AttributeSet?) {
        attributeSet?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CurveTextSeekBar, 0, 0)
            try {
                thumbColor =
                    typedArray.getInt(
                        R.styleable.CurveTextSeekBar_textColor,
                        DEFAULT_TEXT_COLOR
                    )
                maxValue = typedArray.getInt(
                    R.styleable.CurveTextSeekBar_maxValue,
                    DEFAULT_MAX_VALUE
                )
                thresholdValue = min(
                    typedArray.getInt(
                        R.styleable.CurveTextSeekBar_threshold,
                        DEFAULT_MAX_VALUE
                    ), DEFAULT_MAX_VALUE
                )

                progress = typedArray.getInt(
                    R.styleable.CurveTextSeekBar_progress,
                    DEFAULT_PROGRESS
                )
                textBoxColor =
                    typedArray.getColor(
                        R.styleable.CurveTextSeekBar_textBoxColor,
                        DEFAULT_TEXT_BOX_COLOR
                    )
                thumbColor =
                    typedArray.getColor(
                        R.styleable.CurveTextSeekBar_thumbColor,
                        DEFAULT_THUMB_COLOR
                    )
                progressBarColor = typedArray.getColor(
                    R.styleable.CurveTextSeekBar_progressBarColor,
                    DEFAULT_PROGRESS_BAR_COLOR
                )
                thumbStrokeColor = typedArray.getColor(
                    R.styleable.CurveTextSeekBar_thumbStrokeColor,
                    DEFAULT_THUMB_STROKE_COLOR
                )

            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun loadLayout() {

        val root = LayoutInflater.from(context).inflate(R.layout.layout_seekbar, this, true)

        seekBar = root.findViewById(R.id.mSeekBar)
        seekBar.thumbColor = thumbColor
        seekBar.thresholdValue = thresholdValue
        seekBar.max = maxValue
        seekBar.progress = progress

        textView = root.findViewById(R.id.mTextView)
        val textBox = GradientDrawable()
        textBox.setColor(textBoxColor)
        textBox.shape = GradientDrawable.RECTANGLE
        textBox.setSize(150, 100)
        textBox.cornerRadius = 25.0f
        textView.background = textBox
        textView.setTextColor(textColor)
        textView.setText(progress.toString());


    }

    private fun seekBarOnChange() {
        seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                val percentage = ((seek.progress.toDouble() / maxValue.toDouble()) * 100).toInt()
                textView.text = percentage.toString()
                val width = (seekBar.width - seekBar.paddingLeft - seekBar.paddingRight)
                val textPos = (seekBar.paddingLeft + (width - textView.width) * (percentage) / 100)
                textView.setX(textPos.toFloat())
                onProgressUpdate?.invoke(seek, percentage, fromUser)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                textView.visibility = View.VISIBLE
                onProgressStart?.invoke(seek)
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                textView.visibility = View.INVISIBLE
                if (seek.progress > thresholdValue) {
                    seek.setProgress(thresholdValue)
                }
                onProgressStop?.invoke(seek)
            }
        })
    }

}