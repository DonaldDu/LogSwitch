package com.dhy.logswitch.demo

import android.content.Context
import androidx.multidex.MultiDexApplication
import androidx.startup.AppInitializer
import androidx.startup.initialized
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.kit.AbstractKit

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            DoraemonKit.install(this, appInitializedkits)
            DoraemonKit.hide()
        }
    }

    private val Context.appInitializedkits: MutableList<AbstractKit>
        get() {
            val initializer = AppInitializer.getInstance(this)
            return initializer.initialized.values
                .filterIsInstance<AbstractKit>()
                .toMutableList()
        }
}