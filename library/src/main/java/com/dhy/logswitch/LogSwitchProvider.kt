package com.dhy.logswitch

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.*
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import com.didichuxing.doraemonkit.DoraemonKit

internal class LogSwitchProvider : ContentProvider() {
    //region ContentProvider
    override fun onCreate() = true

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return -1
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return -1
    }

    //endregion
    override fun attachInfo(context: Context, info: ProviderInfo) {
        super.attachInfo(context, info)
        val app = context as Application

        val logSwitchKit = LogSwitchKit()
        LogSwitch.logSwitchKit = logSwitchKit
        DoraemonKit.install(app, listOf(logSwitchKit))
        DoraemonKit.hide()

        val installShakeDetector = context.getString(R.string.installShakeDetector) == "1"
        if (installShakeDetector) {
            val shakeDetector = ShakeDetector()
            app.registerActivityLifecycleCallbacks(ActivityLifecycleOfShakeDetector(shakeDetector))

            val screenOffReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    shakeDetector.unregister()
                }
            }
            context.registerReceiver(screenOffReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
        }
    }

    private class ActivityLifecycleOfShakeDetector(val shakeDetector: ShakeDetector) : ActivityLifecycleCallbacks2 {
        val onShake = { DoraemonKit.showToolPanel() }
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            shakeDetector.init(activity.application, onShake)
        }

        override fun onActivityResumed(activity: Activity) {
            shakeDetector.register()
        }

        override fun onActivityPaused(activity: Activity) {
            if (activity.isAppBackground()) shakeDetector.unregister()
        }

        private fun Context.isAppBackground(): Boolean {
            val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val appProcesses = activityManager.runningAppProcesses
            val process = appProcesses?.find { it.processName == packageName }
            return if (process != null) process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            else false
        }
    }
}