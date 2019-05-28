package com.example.forehead.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

object RotationSensorListener: SensorEventListener {

    private lateinit var rotationSensor : Sensor
    private val RADS_TO_DEGREES = -57
    var currentRoll = 0f
    var listener : RotationSensorObserver? = null

    fun setSensor(rotSensor: Sensor){
        rotationSensor = rotSensor
    }

    fun registerListener(newListener : RotationSensorObserver){
        listener = newListener
    }

     fun  getOrientation() : Orientation{
        return when{
            currentRoll < Orientation.SCREEN_UP.roll -> RotationSensorListener.Orientation.SCREEN_UP
            currentRoll > Orientation.SCREEN_DOWN.roll -> RotationSensorListener.Orientation.SCREEN_DOWN
            else -> Orientation.PLAYABLE
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor == rotationSensor){
            updateRotation(event.values)
        }
    }

    private fun updateRotation(values: FloatArray?) {
        val rotationMatrix = FloatArray(9)
        SensorManager.getRotationMatrixFromVector(rotationMatrix, values)
        val orientationAngles = FloatArray(3)
        SensorManager.getOrientation(rotationMatrix, orientationAngles)
        currentRoll = Math.abs(orientationAngles[2] * RADS_TO_DEGREES)   // roll = angle of rotation about the y axis, the ordrr of values in orientation angles is: z, x y
        Log.d("ROLL", currentRoll.toString())
        listener?.onRotationChanged(getOrientation())
    }

    interface RotationSensorObserver{
        fun onRotationChanged(orientation: Orientation)
    }

    enum class Orientation(val roll: Int){
        PLAYABLE(60), SCREEN_UP(20),SCREEN_DOWN(150)
    }

}