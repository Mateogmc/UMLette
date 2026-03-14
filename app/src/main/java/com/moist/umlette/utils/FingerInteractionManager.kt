package com.moist.umlette.utils

import android.view.MotionEvent

class FingerInteractionManager(
    private val panZoom: CameraManager
) {
    fun onFingerTouch(event: MotionEvent): Boolean {
        return panZoom.onFingerTouch(event)
    }
}
