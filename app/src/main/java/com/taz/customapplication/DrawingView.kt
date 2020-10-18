package com.taz.customapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class DrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    //drawing SerializablePath
    private lateinit var drawPath: SerializablePath

    //drawing and canvas paint
    private lateinit var drawPaint: Paint  //drawing and canvas paint
    private lateinit var canvasPaint: Paint

    //initial color
    private val paintColor = -0x9a0000

    //canvas
    private lateinit var drawCanvas: Canvas

    //canvas bitmap
    private lateinit var canvasBitmap: Bitmap

    private var color:Int = Color.BLUE
    private var strokeWidth:Float = 20f

    var paths = mutableListOf<SerializablePath>()
    private val removedPaths = mutableListOf<SerializablePath>()

    init {
        setupDrawing()
    }

    fun setupDrawing() {
        drawPath = SerializablePath()
        drawPaint = Paint()
        drawPaint.setColor(Color.BLUE)
        drawPaint.setAntiAlias(true)
        drawPaint.setStrokeWidth(20f)
        drawPaint.setStyle(Paint.Style.STROKE)
        drawPaint.setStrokeJoin(Paint.Join.ROUND)
        drawPaint.setStrokeCap(Paint.Cap.ROUND)
        canvasPaint = Paint(Paint.DITHER_FLAG)
    }

    infix fun setColor(color: Int) {
        drawPaint.color = color; drawPath.color = color; this.color = color
    }

    infix fun setBrushSize(strokeWidth: Float) {
        drawPaint.strokeWidth = strokeWidth; drawPath.strokeWidth = strokeWidth
        this.strokeWidth = strokeWidth
    }

    fun clear() {
        removedPaths.addAll(paths.reversed())
        paths.clear()
        invalidate()
    }

    fun undo() {
        if (!paths.isEmpty()) {
            removedPaths.add(paths.last())
            paths.removeAt(paths.size - 1)
            invalidate()
        }
    }

    fun redo() {
        if (!removedPaths.isEmpty()) {
            paths.add(removedPaths.last())
            removedPaths.removeAt(removedPaths.size - 1)
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paths.forEach {
            drawPaint.color = it.color
            drawPaint.strokeWidth = it.strokeWidth
            canvas?.drawPath(it, drawPaint)
        }
        drawPaint.color = color
        drawPaint.strokeWidth = strokeWidth
//        canvas?.drawBitmap(canvasBitmap, 0f, 0f, canvasPaint)
        canvas?.drawPath(drawPath, drawPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event!!.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> drawPath.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> drawPath.lineTo(touchX, touchY)
            MotionEvent.ACTION_UP -> {
                paths.add(drawPath)
//                drawCanvas.drawPath(drawPath, drawPaint)
                drawPath = SerializablePath().apply {
                    color = this@DrawingView.color
                    strokeWidth = this@DrawingView.strokeWidth
                }

            }
            else -> return false
        }
        invalidate()
        return true
    }


}