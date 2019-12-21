package com.dhy.logswitch.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dhy.logswitch.LogSwitch
import com.dhy.logswitch.isLogOpen
import com.didichuxing.doraemonkit.DoraemonKit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
