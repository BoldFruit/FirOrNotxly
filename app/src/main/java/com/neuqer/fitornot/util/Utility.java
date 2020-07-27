package com.neuqer.fitornot.util;

import android.os.Looper;
import android.os.Handler;


/**
 * @author DuLong
 * @since 2019/7/29
 * email 798382030@qq.com
 */

public final class Utility {
    private static Handler uiHandle;

    private static void getUIHandle() {
        synchronized (Utility.class) {
            if (uiHandle == null) {
                uiHandle = new Handler(Looper.getMainLooper());
            }
        }
    }

    /**
     * 在UI线程中运行
     */
    public static void runOnUiThread(Runnable runnable) {
        runOnUiThread(runnable, 0);
    }

    /**
     * 在UI线程中运行
     */
    public static void runOnUiThread(Runnable runnable, long delayMillis) {
        getUIHandle();
        uiHandle.postDelayed(runnable, delayMillis);
    }

    /**
     * 在新线程中运行
     */
    public static void runOnNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}
