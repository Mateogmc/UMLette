package com.moist.umlette.utils

import android.view.MotionEvent
import com.moist.umlette.data.DiagramModel
import com.moist.umlette.data.SelectionManager

class StylusInteractionManager(
    private val model: DiagramModel,
    private val selection: SelectionManager,
    private val raycast: Raycast,
    private val camera: CameraManager,
    private val mover: MoveManager
) {

    fun onStylusTouch(event: MotionEvent): Boolean {
        val p = camera.screenToDiagram(event.x, event.y)

        when (event.actionMasked) {

            MotionEvent.ACTION_DOWN -> {
                val hit = raycast.point(p.x, p.y)

                if (hit != null) {
                    val buttonPressed = event.buttonState and MotionEvent.BUTTON_STYLUS_PRIMARY != 0
                    if (buttonPressed) selection.toggle(hit.id)
                    else selection.selectOnly(hit.id)
                } else {
                    selection.clear()
                }
            }
        }

        // Movimiento del componente
        return mover.onStylusTouch(event)
    }
}
