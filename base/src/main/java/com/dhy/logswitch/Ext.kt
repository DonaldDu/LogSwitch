package com.dhy.logswitch

val Any?.isLogOpen: Boolean
    get() {
        return if (this != null) {
            LogSwitch.isLogOpen(this)
        } else false
    }