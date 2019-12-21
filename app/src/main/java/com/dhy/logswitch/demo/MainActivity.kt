package com.dhy.logswitch.demo

import android.app.Activity
import android.os.Bundle
import com.dhy.logswitch.LogSwitch
import com.dhy.logswitch.isLogOpen
import com.didichuxing.doraemonkit.DoraemonKit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonCommit.setOnClickListener {
            DoraemonKit.show()
        }
    }

    override fun onResume() {
        super.onResume()

        if (LogSwitch.isLogOpen(this)) println("LogSwitchKit onResume")
        if (isLogOpen) println("LogSwitchKit onResume")
    }
}
