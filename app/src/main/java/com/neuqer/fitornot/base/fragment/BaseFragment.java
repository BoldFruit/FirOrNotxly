package com.neuqer.fitornot.base.fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author YangCihang
 * @since 17/10/9.
 * email yangcihang@hrsoft.net
 */

public abstract class BaseFragment extends Fragment {

    /** 当前Fragment RootView */
    private View view;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    /**
     * 页面初始化操作.
     */
    protected void init() {
        initVariable();
        initView();
        loadData();
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
     * 获取当前Fragment的RootView
     *
     * @return RootView
     */
    protected View getRootView() {
        return view;
    }

    /**
     * 解绑ButterKnife
     */
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }
}
