package com.dhy.logswitch;

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
