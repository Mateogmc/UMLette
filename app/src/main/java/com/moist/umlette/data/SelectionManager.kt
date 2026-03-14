package com.moist.umlette.data

class SelectionManager {
    val selectedIds = mutableListOf<Long>()

    fun selectOnly(id: Long) {
        selectedIds.clear()
        selectedIds.add(id)
    }

    fun toggle(id: Long) {
        if (id in selectedIds) selectedIds.remove(id)
        else selectedIds.add(id)
    }

    fun clear() = selectedIds.clear()

    fun isSelected(id: Long) = id in selectedIds
}