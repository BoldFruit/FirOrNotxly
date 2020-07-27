package com.neuqer.fitornot.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.mvp.contract.BaseContract;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;


/**
 * @author YangCihang
 * @since 17/10/9.
 * email yangcihang@hrsoft.net
 */

public abstract class BasePresenterProcessViewActivity<Presenter extends BaseContract.Presenter> extends BaseProcessViewActivity
        implements BaseContract.View<Presenter> {
    protected Presenter mPresenter;//V层对P层的引用

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initPresenter();
        super.onCreate(savedInstanceState);
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

    /**
     * 设置TopBar ()
     *
     * @param title 标题名字
     */
    public void initTopBar(String title){
        QMUITopBarLayout topBar = findViewById(R.id.topbar);
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        topBar.setTitle(title).setTextColor(getResources().getColor(R.color.white));
        topBar.setTitleGravity(Gravity.CENTER);
    }

}
