package com.dhy.logswitch.demo

import android.app.Application
import com.dhy.logswitch.LogSwitch
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.kit.IKit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            DoraemonKit.install(this, listOf(LogSwitch.logSwitchKit as IKit?))
        }
    }
}