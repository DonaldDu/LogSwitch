package com.dhy.logswitch.demo

import androidx.multidex.MultiDexApplication
import com.dhy.logswitch.LogSwitch
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.kit.AbstractKit

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            DoraemonKit.install(this, listOf(LogSwitch.logSwitchKit as AbstractKit?))
        }
    }
}