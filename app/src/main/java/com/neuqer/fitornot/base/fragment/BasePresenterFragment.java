package com.neuqer.fitornot.base.fragment;

import android.content.Context;

import com.neuqer.fitornot.base.mvp.contract.BaseContract;


/**
 * @author YangCihang
 * @since 17/10/9.
 * email yangcihang@hrsoft.net
 */

public abstract class BasePresenterFragment<Presenter extends BaseContract.Presenter> extends BaseFragment
        implements BaseContract.View<Presenter> {
    protected Presenter mPresenter;//V层对P层的引用

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //在界面绑定的时候就触发
        initPresenter();
    }

    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    /**
     * 设置对presenter的引用
     *
     * @param presenter P层引用
     */
    @Override
    public void setPresenter(Presenter presenter) {
        this.mPresenter = presenter;
    }

}
