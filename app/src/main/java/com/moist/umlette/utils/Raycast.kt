package com.moist.umlette.utils

import com.moist.umlette.data.DiagramModel
import com.moist.umlette.interfaces.IDiagramComponent

class Raycast(private val model: DiagramModel) {

    fun point(x: Float, y: Float): IDiagramComponent? {
        return model.components.lastOrNull { it.containsPoint(x, y) }
    }
}
