package com.dhy.logswitch

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import kotlin.math.sqrt


class ShakeDetector {
    companion object {
        private var shakeDetector: ShakeDetector? = null

        fun onCreated(app: Application, onShake: () -> Unit) {
            if (shakeDetector == null) shakeDetector = ShakeDetector()
            shakeDetector!!.onCreated(app, onShake)
        }

        fun onResume() {
            shakeDetector?.onResume()
        }

        fun onPause() {
            shakeDetector?.onPause()
        }
    }

    private lateinit var sensorManager: SensorManager
    private lateinit var onShakeCallback: () -> Unit
    private var UPDATE_INTERVAL_MS = 0
    private var startCount = 0
    private lateinit var sensor: Sensor
    private lateinit var handler: Handler

    fun onCreated(app: Application, onShake: () -> Unit) {
        if (!::sensorManager.isInitialized) {
            sensorManager = app.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            UPDATE_INTERVAL_MS = app.getString(R.string.UPDATE_INTERVAL_MS).toInt()
            handler = Handler()
        }
        onShakeCallback = onShake
    }

    private var registed = false
    private fun onResume() {
        handler.removeCallbacksAndMessages(null)
        startCount++
        if (!registed) {
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            registed = true
        }
    }

    private fun onPause() {
        startCount--
        if (startCount == 0) {
            handler.postDelayed({
                sensorManager.unregisterListener(sensorEventListener)
                registed = false
            }, 1500)
        }
    }

    private var lastDate = 0L
    private var x: Float = 0f
    private var y: Float = 0f
    private var z: Float = 0f
    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            x -= event.values[0]
            y -= event.values[1]
            z -= event.values[2]

            val delta = sqrt((x * x + y * y + z * z).toDouble())
            if (delta > 20) {
                if (System.currentTimeMillis() - lastDate > UPDATE_INTERVAL_MS) {
                    lastDate = System.currentTimeMillis()
                    onShakeCallback()
                }
            }

            x = event.values[0]
            y = event.values[1]
            z = event.values[2]
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }
}