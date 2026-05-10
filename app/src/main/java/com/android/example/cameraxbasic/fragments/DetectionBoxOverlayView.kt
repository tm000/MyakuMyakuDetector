package com.android.example.cameraxbasic.fragments

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class DetectionBoxOverlayView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val boxes = mutableListOf<DetectionBox>()

    private var boxPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }

    private var textPaint = Paint().apply {
        color = Color.RED
        textSize = 40f
        style = Paint.Style.FILL
    }

    data class DetectionBox(
        val rect: RectF,
        val label: String,
        val score: Float
    )

    fun updateDetections(detections: List<DetectionBox>) {
        boxes.clear()
        boxes.addAll(detections)
        invalidate()
    }

    fun changeDetectionColor(newColor: Int) {
        boxPaint = Paint().apply {
            color = newColor
            style = Paint.Style.STROKE
            strokeWidth = 6f
        }

        textPaint = Paint().apply {
            color = newColor
            textSize = 40f
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        boxes.forEach { box ->
            canvas.drawRect(box.rect, boxPaint)

            val text = "${box.label} ${(box.score * 100).toInt()}%"
            canvas.drawText(text, box.rect.left, box.rect.top - 10, textPaint)
        }
    }
}