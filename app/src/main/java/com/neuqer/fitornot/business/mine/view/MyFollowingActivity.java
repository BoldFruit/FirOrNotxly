package com.neuqer.fitornot.business.mine.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.BasePresenterActivity;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.model.response.FollowingModel;
import com.neuqer.fitornot.business.mine.presenter.FollowActivityPresenter;
import com.neuqer.fitornot.business.mine.view.adapter.MyFollowingAdapter;
import com.neuqer.fitornot.util.ToastUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: Kingtous
 * Since: 2019-10-01
 * Email: me@kingtous.cn
 */
public class MyFollowingActivity extends BasePresenterActivity<MineFragmentContract.Presenter> implements MineFragmentContract.View, BaseRecyclerViewAdapter.OnItemClicked, MyFollowingAdapter.SetButtonClickListener {
    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;
    @BindView(R.id.rv_fit_request)
    RecyclerView rvFollow;
    MyFollowingAdapter mAdapter;

    QMUITipDialog.CustomBuilder tCBuilder;
    @BindView(R.id.pull_to_refresh_head)
    QMUIPullRefreshLayout pullToRefreshHead;

    @Override
    protected MineFragmentContract.Presenter initPresenter() {
        return new FollowActivityPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_fit_request;
    }

    @Override
    protected void initVariable() {
        tCBuilder = new QMUITipDialog.CustomBuilder(this);
        QMUIStatusBarHelper.translucent(this);
        rvFollow.setLayoutManager(new LinearLayoutManager(this));
    }

    public void processData(FollowingModel models) {
        if (models.getData().size() == 0) {
            rvFollow.setVisibility(View.GONE);
        } else {
            if (mAdapter != null) {
                mAdapter.clear();
            }
            rvFollow.setVisibility(View.VISIBLE);
            mAdapter = new MyFollowingAdapter(this, models.getData(), R.layout.item_follow);
            mAdapter.setButtonClickListener(this);
            rvFollow.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        pullToRefreshHead.finishRefresh();
    }

    @Override
    protected void initView() {
        initTopBar("关注");
        pullToRefreshHead.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                loadData();
            }
        });

    }

    @Override
    protected void loadData() {
        mPresenter.getAllFollowing();
    }

    @Override
    public void onItemClicked(View view, Object o, int position) {

    }


    @Override
    public void onButtonClicked(FollowingModel.DataBean dataBean) {
        QMUIBottomSheet sheet = new QMUIBottomSheet(this);
        View pop_view = LayoutInflater.from(this).inflate(R.layout.item_input_detail, null);
        sheet.setContentView(pop_view);
        sheet.show();
        QMUIRoundButton btn_publish = pop_view.findViewById(R.id.btn_pulish_request);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToastPic(R.drawable.img_publish_success, MyFollowingActivity.this);
                sheet.dismiss();
            }
        });
    }

}
