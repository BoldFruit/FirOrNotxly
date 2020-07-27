package com.neuqer.fitornot.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.neuqer.fitornot.base.mvp.contract.BaseContract;


/**
 * @author YangCihang
 * @since 17/10/9.
 * email yangcihang@hrsoft.net
 */

public abstract class ToolbarPresenterActivity<Presenter extends BaseContract.Presenter> extends ToolbarActivity implements BaseContract.View<Presenter> {
    protected Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //创建时就绑定P层
        initPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * V层创建 与之绑定的P层对象
     *
     * @return 与之绑定的P层对象
     */
    public abstract Presenter initPresenter();

    @Override
    public void setPresenter(Presenter presenter) {
        this.mPresenter = presenter;
    }
}
