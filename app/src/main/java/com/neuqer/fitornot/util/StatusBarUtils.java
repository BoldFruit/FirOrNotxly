package com.neuqer.fitornot.util;

/**
 * @author DuLong
 * @since 2019/7/30
 * email 798382030@qq.com
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import static android.content.ContentValues.TAG;

/**
 * 状态栏相关工具类
 *
 */
public class StatusBarUtils {


    /**
     * 设置状态栏和导航栏的颜色
     *
     * @param activity 当前所在活动
     * @param statusBarColor 状态栏颜色
     * @param navigationBarColor 导航栏颜色
     */
    public static void setWindowStatusBarColor(Activity activity, int statusBarColor,  int navigationBarColor) {
        //Android5.0后可用
        try {
            if (Build.VERSION.SDK_INT  >=  Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //状态栏
                window.setStatusBarColor(activity.getColor(statusBarColor));

                //底部导航栏
                window.setNavigationBarColor(activity.getColor(navigationBarColor));
                //让view不根据系统窗口来调整自己的布局
                ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    ViewCompat.setFitsSystemWindows(mChildView, false);
                    ViewCompat.requestApplyInsets(mChildView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //只设置状态栏颜色
    public static void setWindowStatusBarColor(Activity activity, int statusBarColor) {
        try {
            if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //状态栏
                window.setStatusBarColor(activity.getColor(statusBarColor));
                //让view不根据系统窗口来调整自己的布局
                ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    ViewCompat.setFitsSystemWindows(mChildView, false);
                    ViewCompat.requestApplyInsets(mChildView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //实现沉浸式状态栏（不设置导航栏）
    public static void setImmersiveStatusBar(Window window) {
        //5.0版本以上的设置方法
        if (Build.VERSION.SDK_INT  >=  Build.VERSION_CODES.LOLLIPOP){
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION );
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.setStatusBarColor(Color.TRANSPARENT);


        } else if (Build.VERSION.SDK_INT  >=  Build.VERSION_CODES.KITKAT) {
            //设置状态栏颜色为透明色
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            Log.d(TAG, "该系统不支持沉浸式状态栏 ");
        }
    }



    //设置全透明，和半透明的5.0
    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (hideStatusBarBackground) {
                //如果为全透明模式，取消设置Window半透明的Flag
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //设置状态栏为透明
                window.setStatusBarColor(Color.TRANSPARENT);
                //设置window的状态栏不可见
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                //如果为半透明模式，添加设置Window半透明的Flag
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //设置系统状态栏处于可见状态
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
            //view不根据系统窗口来调整自己的布局
            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
                ViewCompat.requestApplyInsets(mChildView);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            //设置Window为透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
            View mContentChild = mContentView.getChildAt(0);

            if (mContentChild != null) {
                //fitsSystemWindow 为 false, 不预留系统栏位置.
                ViewCompat.setFitsSystemWindows(mContentChild, false);
            }
        } else {
            Log.d(TAG, "该版本不支持改变状态栏");
        }
    }

    /**
     * 设置黑色字体
     *
     * @param activity 当前活动
     * @param bDark 是否设置黑色字体
     * @param statusBarColor 状态栏颜色
     */
    public static void setStatusBarMode(Activity activity, boolean bDark, int statusBarColor) {

        //6.0以上

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            View decorView = activity.getWindow().getDecorView();

            if (decorView != null) {

                int vis = decorView.getSystemUiVisibility();

                if (bDark) {

                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

                } else {

                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

                }

                decorView.setSystemUiVisibility(vis);
                setWindowStatusBarColor(activity, statusBarColor);

            }

        }

    }

}
