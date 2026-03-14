package com.moist.umlette.utils

import android.view.MotionEvent
import com.moist.umlette.data.DiagramModel
import com.moist.umlette.data.SelectionManager

class MoveManager(
    private val model: DiagramModel,
    private val selection: SelectionManager,
    private val hitTest: Raycast,
    private val panZoom: CameraManager
) {

    private var moving = false
    private var lastX = 0f
    private var lastY = 0f

    fun onStylusTouch(event: MotionEvent): Boolean {
        val p = panZoom.screenToDiagram(event.x, event.y)

        when (event.actionMasked) {

            MotionEvent.ACTION_DOWN -> {
                val hit = hitTest.point(p.x, p.y)

                if (hit != null && selection.isSelected(hit.id)) {
                    moving = true
                    lastX = p.x
                    lastY = p.y
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (moving) {
                    val dx = p.x - lastX
                    val dy = p.y - lastY

                    // Mover todos los seleccionados
                    model.components.forEach { comp ->
                        if (selection.isSelected(comp.id)) {
                            comp.x += dx
                            comp.y += dy
                        }
                    }

                    lastX = p.x
                    lastY = p.y
                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                moving = false
            }
        }

        return moving
    }
}
