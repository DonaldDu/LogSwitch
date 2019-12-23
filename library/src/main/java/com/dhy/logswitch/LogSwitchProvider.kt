package com.dhy.logswitch

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ProviderInfo
import android.os.Bundle
import android.support.v4.content.FileProvider
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.kit.Category

internal class LogSwitchProvider : FileProvider() {
    override fun attachInfo(context: Context, info: ProviderInfo) {
        super.attachInfo(context, info)
        val app = context as Application

        val logSwitchKit = LogSwitchKit().apply { onAppInit(context) }
        LogSwitch.logSwitchKit = logSwitchKit
        DoraemonKit.install(app)
        DoraemonKit.getKitList(Category.TOOLS).add(logSwitchKit)
        DoraemonKit.hide()

        val installShakeDetector = context.getString(R.string.installShakeDetector) == "1"
        if (installShakeDetector) {
            val onShake = { DoraemonKit.show() }
            app.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks2 {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    ShakeDetector.onCreated(activity.application, onShake)
                }

                override fun onActivityResumed(activity: Activity) {
                    super.onActivityResumed(activity)
                    ShakeDetector.onResume()
                }

                override fun onActivityPaused(activity: Activity) {
                    super.onActivityPaused(activity)
                    ShakeDetector.onPause()
                }
            })
        }
    }
}