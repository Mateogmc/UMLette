package com.moist.umlette.data

import com.moist.umlette.interfaces.IDiagramComponent

data class DiagramModel(
    val components: MutableList<IDiagramComponent> = mutableListOf()
)
