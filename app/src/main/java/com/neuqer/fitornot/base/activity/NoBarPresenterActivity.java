package com.neuqer.fitornot.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.neuqer.fitornot.base.mvp.contract.BaseContract;


/**
 * @author YangCihang
 * @since 17/10/9.
 * email yangcihang@hrsoft.net
 */

public abstract class NoBarPresenterActivity<Presenter extends BaseContract.Presenter> extends NoBarActivity implements BaseContract.View<Presenter>{
    protected Presenter mPresenter;


    //在创建的时候初始化Presenter
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化Presenter
     * @return
     */
    protected abstract Presenter initPresenter();


    @Override
    public void setPresenter(Presenter presenter) {
        this.mPresenter = presenter;
    }
}

