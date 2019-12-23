package com.dhy.logswitch.demo

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
//            DoraemonKit.install(this, listOf(LogSwitch.logSwitchKit as IKit?))
        }
    }
}