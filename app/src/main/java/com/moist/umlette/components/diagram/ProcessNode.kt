package com.moist.umlette.components.diagram

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.moist.umlette.interfaces.IDiagramComponent

class ProcessNode(
    override val id: Long,
    override var x: Float,
    override var y: Float,
    override var width: Float = 200f,
    override var height: Float = 100f,
    var label: String = "Proceso"
) : IDiagramComponent {

    private val rectPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 4f
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 40f
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(x, y, x + width, y + height, rectPaint)
        canvas.drawRect(x, y, x + width, y + height, borderPaint)

        val textX = x + width / 2
        val textY = y + height / 2 - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(label, textX, textY, textPaint)
    }
}
