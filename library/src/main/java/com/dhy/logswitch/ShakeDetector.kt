package com.dhy.logswitch

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import kotlin.math.max
import kotlin.math.sqrt


internal class ShakeDetector {
    private lateinit var sensorManager: SensorManager
    private lateinit var onShakeCallback: () -> Unit
    private var UPDATE_INTERVAL_MS = 0
    private var startCount = 0
    private lateinit var sensor: Sensor

    fun init(app: Application, onShake: () -> Unit) {
        if (!::sensorManager.isInitialized) {
            sensorManager = app.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            UPDATE_INTERVAL_MS = app.getString(R.string.UPDATE_INTERVAL_MS).toInt()
        }
        onShakeCallback = onShake
    }

    private var registed = false
    fun register() {
        if (!registed) {
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            registed = true
        }
    }

    fun unregister() {
        sensorManager.unregisterListener(sensorEventListener)
        registed = false
    }

    private var lastDate = 0L
    private var x: Float = 0f
    private var y: Float = 0f
    private var z: Float = 0f
    private val SPEED_SHRESHOLD = 200
    var maxDelta = 0F
    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val currentUpdateTime = System.currentTimeMillis()
            val timeInterval: Long = currentUpdateTime - lastDate
            if (timeInterval < UPDATE_INTERVAL_MS) return
            lastDate = currentUpdateTime
            x -= event.values[0]
            y -= event.values[1]
            z -= event.values[2]

            val delta = sqrt(x * x + y * y + z * z) / timeInterval * 1000
            if (delta > SPEED_SHRESHOLD) onShakeCallback()
//            maxDelta = max(maxDelta, delta)
//            println("delta $delta  , maxDelta $maxDelta")
            x = event.values[0]
            y = event.values[1]
            z = event.values[2]
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }
}