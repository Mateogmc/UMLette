package com.moist.umlette.views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.moist.umlette.data.DiagramModel
import com.moist.umlette.data.SelectionManager
import com.moist.umlette.interfaces.IDiagramComponent
import com.moist.umlette.utils.*

class DiagramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val model = DiagramModel()
    private val selection = SelectionManager()
    private val panZoom = CameraManager(context)
    private val hitTest = Raycast(model)
    private val mover = MoveManager(model, selection, hitTest, panZoom)
    private val stylus = StylusInteractionManager(model, selection, hitTest, panZoom, mover)
    private val finger = FingerInteractionManager(panZoom)
    private val renderer = DiagramRenderer(model, selection, panZoom)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val tool = event.getToolType(event.actionIndex)

        val handled = when (tool) {
            MotionEvent.TOOL_TYPE_FINGER -> finger.onFingerTouch(event)
            MotionEvent.TOOL_TYPE_STYLUS -> stylus.onStylusTouch(event)
            else -> false
        }

        if (handled) invalidate()
        return handled
    }

    override fun onDraw(canvas: Canvas) {
        renderer.draw(canvas)
    }

    fun addComponent(component: IDiagramComponent) {
        model.components.add(component)
        invalidate()
    }
}
