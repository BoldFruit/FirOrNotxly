package com.neuqer.fitornot;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.neuqer.fitornot.util.Loader.GlideImageLoader;
import com.neuqer.fitornot.util.Loader.ImageLoader;
import com.neuqer.fitornot.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * @author DuLong
 * @Since 19/7/29
 */

public class App extends Application {
    private static List<Activity> activityList = new ArrayList<>();
    private static Context instance ;    //实例对象

    @Override
    public void onCreate() {
        super.onCreate();
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityList.remove(activity);
            }
        });
        instance = getApplicationContext();

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(true);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setSaveRectangle(true);
        imagePicker.setSelectLimit(1);
        imagePicker.setFocusWidth(800);
        imagePicker.setFocusHeight(800);
        imagePicker.setOutPutX(300);
        imagePicker.setOutPutY(300);
    }


    /**
     * 外部获取实例对象
     *
     * @return app
     */
    public static Context getInstance() {
        if(instance == null)
            Log.d(TAG, "未初始化 ");
        return instance;
    }

    /**
     * 移除Activity
     *
     * @param activity act
     */

    public static void removeActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * 清除所有的Activity
     */
    public static void removeAllActivity() {
        for (Activity activity : activityList) {
            if (activity != null && !activity.isFinishing()) {
                SharedPreferenceUtil.remove("userId");
                SharedPreferenceUtil.remove("isLogined");
                SharedPreferenceUtil.remove("token");
                activity.finish();
            }
        }
    }

}
