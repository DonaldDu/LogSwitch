package com.dhy.logswitch

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.didichuxing.doraemonkit.kit.AbstractKit


internal class LogSwitchKit : AbstractKit() {
    companion object {
        val kit = LogSwitchKit()
        internal val switches: MutableMap<String, Boolean> = mutableMapOf()
        private const val KEY_NAME = "LogSwitch"
        internal fun save(context: Context) {
            val datas = switches.filter { it.value }.map { it.key }.toSet()
            val pref = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)
            pref.edit().apply {
                putStringSet(KEY_NAME, datas)
                apply()
            }
        }
    }

    fun init(context: Context): LogSwitchKit {
        val app = context.applicationContext as Application
        installShakeDetector(app)
        return this
    }

    private var installShake = false
    private fun installShakeDetector(app: Application) {
        if (installShake) return
        installShake = true
        val shakeDetector = ShakeDetector()
        app.registerActivityLifecycleCallbacks(ActivityLifecycleOfShakeDetector(shakeDetector))

        val screenOffReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                shakeDetector.unregister()
            }
        }

        app.registerReceiver(screenOffReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
    }

    override fun onClick(context: Context?) {
        val i = Intent(context, LogSwitchActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context!!.startActivity(i)
    }

    override fun onAppInit(context: Context?) {
        val pref = context!!.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)
        pref.getStringSet(KEY_NAME, emptySet())?.forEach {
            switches[it] = true
        }
    }

    override val icon: Int = R.drawable.log_switch_kit
    override val name: Int = R.string.log_switch_kit
}