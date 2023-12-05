package com.dhy.logswitch

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs


internal class ShakeDetector {
    private var sensorManager: SensorManager? = null
    private lateinit var onShakeCallback: () -> Unit
    private var UPDATE_INTERVAL_MS = 0
    private var startCount = 0
    private lateinit var sensor: Sensor

    private fun init(context: Context, onShake: () -> Unit) {
        if (sensorManager == null) {
            val app = context.applicationContext as Application
            sensorManager = app.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            UPDATE_INTERVAL_MS = app.getString(R.string.UPDATE_INTERVAL_MS).toInt()
        }
        onShakeCallback = onShake
    }

    private var registed = false
    fun register(context: Context, onShake: () -> Unit) {
        if (!registed) {
            init(context, onShake)
            sensorManager!!.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            registed = true
        }
    }

    fun unregister() {
        sensorManager?.unregisterListener(sensorEventListener)
        registed = false
    }

    private var lastDate = 0L
    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val currentUpdateTime = System.currentTimeMillis()
            val timeInterval: Long = currentUpdateTime - lastDate
            if (timeInterval < UPDATE_INTERVAL_MS) return
            if (event.isShake()) onShakeCallback()
            lastDate = currentUpdateTime
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    private fun SensorEvent.isShake(limit: Int = 17): Boolean {
        // https://www.cnblogs.com/tyjsjl/p/3695808.html
        // x、y、z三轴的加速度数值, 单位是m/s^2
        return abs(values[0]) > limit || abs(values[1]) > limit || abs(values[2]) > limit
    }
}