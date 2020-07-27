package com.neuqer.fitornot.base.activity;


import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.neuqer.fitornot.common.LogicErrorException;
import com.neuqer.fitornot.util.QnHelper.QnHelper;
import com.neuqer.fitornot.util.Utility;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    /** 进度窗 */
    protected QMUITipDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 禁止APP横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        init();
    }

    /**
     * 页面初始化操作.
     */
    protected void init() {
        ButterKnife.bind(this);
        preInit();
        initVariable();
        initView();
        loadData();
    }

    /**
     * 执行init 方法之前的处理
     */
    protected void preInit() {
        // progressDialog 统一定义
        progressDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("加载数据中")
                .create();
    }

    /**
     * 获取LayoutId.
     *
     * @return LayoutId 布局文件Id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化变量.
     */
    protected abstract void initVariable();

    /**
     * 初始化View的状态，挂载各种监听事件.
     */
    protected abstract void initView();

    /**
     * 初始化加载页面数据.
     */
    protected abstract void loadData();


    /**
     * 代码逻辑错误，在理论上不可达的位置调用此方法
     */
    protected void logicError() {
        try {
            throw new LogicErrorException();
        } catch (LogicErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示progressDialog
     *
     */
    protected void showProgressDialog() {
        if (!isDestroyed() && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Utility.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
            }
        });
    }

    /**
     * 取消ProgressDialog
     */
    protected void disMissProgressDialog() {
        if (!isDestroyed() && progressDialog.isShowing()) {
            Utility.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            }, 300);
        }
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

}