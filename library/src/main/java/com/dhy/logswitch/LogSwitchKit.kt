package com.dhy.logswitch

import android.content.Context
import android.content.Intent
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.didichuxing.doraemonkit.kit.Category


internal class LogSwitchKit : AbstractKit() {
    companion object {
        internal val switches: MutableMap<String, Boolean> = mutableMapOf()

        init {
            LogSwitch.switches = switches
        }

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

    override val category: Int = Category.TOOLS
    override val icon: Int = R.drawable.log_switch_kit
    override val name: Int = R.string.log_switch_kit
}