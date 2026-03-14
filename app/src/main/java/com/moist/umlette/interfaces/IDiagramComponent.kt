package com.moist.umlette.interfaces

import android.graphics.Canvas

interface IDiagramComponent {
    val id: Long

    var x: Float
    var y: Float
    var width: Float
    var height: Float

    fun draw(canvas: Canvas)

    fun containsPoint(px: Float, py: Float): Boolean {
        return px >= x && px <= x + width && py >= y && py <= y + height
    }

    fun moveBy(dx: Float, dy: Float) {
        x += dx
        y += dy
    }
}