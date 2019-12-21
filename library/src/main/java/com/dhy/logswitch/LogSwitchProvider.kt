package com.dhy.logswitch

import android.app.Application
import android.content.Context
import android.content.pm.ProviderInfo
import android.support.v4.content.FileProvider
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.kit.IKit

class LogSwitchProvider : FileProvider() {
    override fun attachInfo(context: Context, info: ProviderInfo) {
        super.attachInfo(context, info)
        println("LogSwitchProvider attachInfo")
        LogSwitch.logSwitchKit = LogSwitchKit()
        val install = context.getString(R.string.install_doraemon_kit) == "1"
        if (install) {
            DoraemonKit.install(context as Application, listOf(LogSwitch.logSwitchKit as IKit))
            DoraemonKit.hide()
        }
    }
}