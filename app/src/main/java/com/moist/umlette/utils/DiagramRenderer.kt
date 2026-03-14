package com.moist.umlette.utils

import android.graphics.*
import com.moist.umlette.data.DiagramModel
import com.moist.umlette.data.SelectionManager

class DiagramRenderer(
    private val model: DiagramModel,
    private val selection: SelectionManager,
    private val panZoom: CameraManager
) {

    private val gridPaint = Paint().apply {
        color = Color.LTGRAY
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    fun draw(canvas: Canvas) {
        canvas.save()
        canvas.concat(panZoom.viewMatrix)

        drawGrid(canvas)
        drawComponents(canvas)

        canvas.restore()
    }

    private fun drawComponents(canvas: Canvas) {
        model.components.forEach { component ->
            component.draw(canvas)

            if (selection.isSelected(component.id)) {
                val selPaint = Paint().apply {
                    color = Color.BLUE
                    style = Paint.Style.STROKE
                    strokeWidth = 4f
                }
                canvas.drawRect(
                    component.x,
                    component.y,
                    component.x + component.width,
                    component.y + component.height,
                    selPaint
                )
            }
        }
    }

    private fun drawGrid(canvas: Canvas) {
        val values = FloatArray(9)
        panZoom.viewMatrix.getValues(values)
        val scale = values[Matrix.MSCALE_X]
        if (scale < 0.4f) return

        val spacing = 50f
        val radius = 3f

        panZoom.inverseMatrix.reset()
        panZoom.viewMatrix.invert(panZoom.inverseMatrix)

        val screenRect = RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat())
        panZoom.inverseMatrix.mapRect(screenRect)

        val startX = (screenRect.left / spacing).toInt() * spacing
        val endX = (screenRect.right / spacing).toInt() * spacing
        val startY = (screenRect.top / spacing).toInt() * spacing
        val endY = (screenRect.bottom / spacing).toInt() * spacing

        var x = startX
        while (x <= endX) {
            var y = startY
            while (y <= endY) {
                canvas.drawCircle(x, y, radius, gridPaint)
                y += spacing
            }
            x += spacing
        }
    }
}
