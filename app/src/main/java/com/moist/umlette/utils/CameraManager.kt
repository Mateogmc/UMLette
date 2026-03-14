package com.moist.umlette.utils
import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.view.MotionEvent
import android.view.ScaleGestureDetector

class CameraManager(context: Context) {

    val viewMatrix = Matrix()
    val inverseMatrix = Matrix()

    private var lastX = 0f
    private var lastY = 0f

    private val scaleDetector = ScaleGestureDetector(context,
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                viewMatrix.postScale(
                    detector.scaleFactor,
                    detector.scaleFactor,
                    detector.focusX,
                    detector.focusY
                )
                return true
            }
        }
    )

    fun onFingerTouch(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
            }

            MotionEvent.ACTION_POINTER_UP -> {
                if (event.pointerCount == 2) {
                    val remaining = if (event.actionIndex == 0) 1 else 0
                    lastX = event.getX(remaining)
                    lastY = event.getY(remaining)
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (!scaleDetector.isInProgress) {
                    val dx = event.x - lastX
                    val dy = event.y - lastY
                    viewMatrix.postTranslate(dx, dy)
                }
                lastX = event.x
                lastY = event.y
            }
        }

        return true
    }

    fun screenToDiagram(x: Float, y: Float): PointF {
        inverseMatrix.reset()
        viewMatrix.invert(inverseMatrix)
        val p = floatArrayOf(x, y)
        inverseMatrix.mapPoints(p)
        return PointF(p[0], p[1])
    }
}
