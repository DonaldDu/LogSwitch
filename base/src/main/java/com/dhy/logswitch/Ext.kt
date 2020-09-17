package com.dhy.logswitch

val Any?.isLogOpen: Boolean
    get() {
        return LogSwitch.isLogOpen(this)
    }