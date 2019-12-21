package com.dhy.logswitch

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.content.pm.ProviderInfo
import android.os.Bundle
import android.support.v4.content.FileProvider
import com.dhy.shakedetector.ShakeDetector
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.kit.IKit

internal class LogSwitchProvider : FileProvider() {
    override fun attachInfo(context: Context, info: ProviderInfo) {
        super.attachInfo(context, info)
        val app = context as Application
        LogSwitch.logSwitchKit = LogSwitchKit()

        val install = context.getString(R.string.auto_install_doraemon_kit) == "1"
        if (install) {
            DoraemonKit.install(app, listOf(LogSwitch.logSwitchKit as IKit))
            DoraemonKit.hide()
        }

        val installShakeDetector = context.getString(R.string.installShakeDetector) == "1"
        if (installShakeDetector) {
            DoraemonKit.hide()
            app.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks2 {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    if (activity is LifecycleOwner) {
                        ShakeDetector.onShake(activity) {
                            DoraemonKit.show()
                        }
                    }
                }
            })
        }
    }
}