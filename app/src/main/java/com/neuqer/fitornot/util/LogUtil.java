package com.neuqer.fitornot.util;

import android.util.Log;

/**
 * @author DuLong
 * @since 2019/8/21
 * email 798382030@qq.com
 */

public class LogUtil {
    public static final int VERROSE = 1;

    public static final int DEBUG = 2;

    public static final int INFO = 3;

    public static final int WRAN = 4;

    public static final int ERROR = 5;

    public static final int NOTHING = 6;

    public static int level = VERROSE;

    public static void v(String tag, String msg) {
        if (level <= VERROSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WRAN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);
        }
    }


}
