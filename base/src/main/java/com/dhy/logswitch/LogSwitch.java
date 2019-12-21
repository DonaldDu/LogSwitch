package com.dhy.logswitch;

import com.dhy.logswitchbase.BuildConfig;

import java.util.Map;

public class LogSwitch {
    /***
     * null when release
     * */
    static Map<String, Boolean> switches;
    /***
     * null when release
     * */
    public static Object logSwitchKit;

    public static boolean isLogOpen(Object owner) {
        if (BuildConfig.DEBUG && owner != null && switches != null) {
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
