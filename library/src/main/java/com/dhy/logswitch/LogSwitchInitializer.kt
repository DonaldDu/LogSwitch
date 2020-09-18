package com.dhy.logswitch

import android.content.Context
import androidx.startup.Initializer
import com.didichuxing.doraemonkit.kit.AbstractKit

class LogSwitchInitializer : Initializer<AbstractKit> {
    override fun create(context: Context): AbstractKit {
        return LogSwitchKit.kit.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}