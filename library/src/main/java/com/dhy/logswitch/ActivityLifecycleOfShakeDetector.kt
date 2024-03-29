package com.dhy.logswitch

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import com.didichuxing.doraemonkit.DoraemonKit

internal class ActivityLifecycleOfShakeDetector(private val shakeDetector: ShakeDetector) : ActivityLifecycleCallbacks2 {
    private val onShake = { DoraemonKit.showToolPanel() }
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        hideDoraemonKit()
    }

    override fun onActivityResumed(activity: Activity) {
        shakeDetector.register(activity, onShake)
        hideDoraemonKit()
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity.isAppBackground()) shakeDetector.unregister()
        hideDoraemonKit()
    }

    private fun hideDoraemonKit() {
        if (DoraemonKit.isShow) DoraemonKit.hide()
    }

    private fun Context.isAppBackground(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses
        val process = appProcesses?.find { it.processName == packageName }
        return if (process != null) process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
        else false
    }
}