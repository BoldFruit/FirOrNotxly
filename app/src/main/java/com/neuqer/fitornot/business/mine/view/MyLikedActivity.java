package com.neuqer.fitornot.business.mine.view;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.BasePresenterActivity;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.circle.view.FitDetailActivity;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.presenter.MineFragmentPresenter;
import com.neuqer.fitornot.business.mine.view.adapter.MyPublishedAdapter;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;

/**
 * Author: Kingtous
 * Since: 2019-09-25
 * Email: me@kingtous.cn
 */
public class MyLikedActivity extends MyPublishedActivity {


    @Override
    protected void initView() {
        initTopBar("我的喜欢");
    }

    @Override
    protected void loadData() {
        mPresenter.getAllLikedMoments(currentPage);
    }

}
