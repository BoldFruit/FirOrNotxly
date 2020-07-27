package com.neuqer.fitornot.util.Loader;

/**
 * @author DuLong
 * @since 2019/8/25
 * email 798382030@qq.com
 */

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.mvp.IDataCallBack;

/**
 * 创建自定义的AlertDialog
 */
public class DialogUtil {


    /**
     * 获得自定义的Dialog
     *
     * @param context 当前上下文
     * @param mTitle 题目
     * @param mDetail 第一行语句
     * @param mSecondDetail 第二行语句
     * @param mThirdDetail 第三行语句
     * @param mEnter 选择按钮的label
     * @param callback 回调
     */
    public static void showDialog (Context context, String mTitle, String mDetail, String mSecondDetail, String mThirdDetail, String mEnter ,final IDataCallBack.SuccessCallback<Void> callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View view_custom = LayoutInflater.from(context).inflate(R.layout.dialog_tips, null);
        TextView title = view_custom.findViewById(R.id.txt_dialog_title);
        TextView detail = view_custom.findViewById(R.id.txt_dialog_detail);
        TextView secondDetail = view_custom.findViewById(R.id.txt_dialog_second_detail);
        TextView thirdDetail = view_custom.findViewById(R.id.txt_dialog_third_detail);
        Button btn_return = view_custom.findViewById(R.id.btn_dialog_return);
        Button btn_enter = view_custom.findViewById(R.id.btn_dialog_enter);
        //设置相应的属性
        btn_enter.setText(mEnter);
        title.setText(mTitle);
        detail.setText(mDetail);
        secondDetail.setText(mSecondDetail);
        thirdDetail.setText(mThirdDetail);

        builder.setView(view_custom);
        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onDataLoaded(null);
                dialog.dismiss();
            }
        });
    }
}
