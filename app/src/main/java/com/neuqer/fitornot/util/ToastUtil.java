package com.neuqer.fitornot.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neuqer.fitornot.App;
import com.neuqer.fitornot.R;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * @author DuLong
 * @since 2019/7/29
 * email 798382030@qq.com
 */

public final class ToastUtil {

    private static final int duration = Toast.LENGTH_SHORT;

    public static void showToast(final String msg) {
        Utility.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getInstance(), msg, duration).show();
            }
        });
    }

    public static void showToast(@StringRes final int resId) {
        Utility.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = App.getInstance().getString(resId);
                Toast.makeText(App.getInstance(), msg, duration).show();
            }
        });
    }

    public static void showToastWithPic(final String res, final Activity activity) {
        Utility.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.view_toast, null, false);
                TextView textView = (TextView) view.findViewById(R.id.tv_toast);
                textView.setText(res);
                Toast toast = new Toast(App.getInstance());
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(duration);
                toast.setView(view);
                toast.show();

            }
        });
    }
    public static void showToastPic(final int imgRes,final Activity activity){
        View v = activity.getLayoutInflater().inflate(R.layout.toast_img,null);
        ((ImageView) v.findViewById(R.id.img_toast)).setImageDrawable(activity.getResources().getDrawable(imgRes));
        Toast toast = new Toast(App.getInstance());
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,200);
        toast.show();
    }

    public static void showQMUIToast(final int type,final String message){
        Handler handler=new Handler(Looper.getMainLooper());
        QMUITipDialog.Builder builder=new QMUITipDialog.Builder(App.getInstance());
        builder.setIconType(type);
        builder.setTipWord(message);
        QMUITipDialog dialog=builder.create();
        dialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },1500);
    }
}
