package com.dhy.logswitch

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.didichuxing.doraemonkit.DoraemonKit
import kotlinx.android.synthetic.main.activity_log_switch.*

internal class LogSwitchActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_switch)
        DoraemonKit.hide()

        val datas = LogSwitchKit.switches.map { LogSwitch(it.key, it.value) }.sortedBy { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, datas)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val data = datas[position]
            data.open = !data.open
            LogSwitchKit.switches[data.name] = data.open
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogSwitchKit.save(this)
    }

    private data class LogSwitch(val name: String, var open: Boolean) {
        override fun toString(): String {
            return "$name => " + (if (open) "ON" else "OFF")
        }
    }
}
