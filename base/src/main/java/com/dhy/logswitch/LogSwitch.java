package com.dhy.logswitch;

import androidx.annotation.Nullable;

import java.util.Map;

public class LogSwitch {
    /***
     * null when release
     * */
    static Map<String, Boolean> switches;

    public static boolean isLogOpen(@Nullable Object owner) {
        if (switches != null && owner != null) {
            String key = owner.getClass().getName();
            Boolean open = switches.get(key);
            if (open == null) {
                open = false;
                switches.put(key, false);
            }
            return open;
        } else return false;
    }
}
