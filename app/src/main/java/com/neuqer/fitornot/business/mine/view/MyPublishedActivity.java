package com.neuqer.fitornot.business.mine.view;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
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
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Author: Kingtous
 * Since: 2019-09-25
 * Email: me@kingtous.cn
 */
public class MyPublishedActivity extends BasePresenterActivity<MineFragmentContract.Presenter> implements XRecyclerView.LoadingListener,MineFragmentContract.View, BaseRecyclerViewAdapter.OnItemClicked<MomentsModel.DataBean>{

    @BindView(R.id.rv_published)
    XRecyclerView rvPublished;
    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;
    protected MyPublishedAdapter adapter;
    protected MineFragmentContract.Presenter mPresenter;

    public static String IDCODE = "ID";
    protected int MYID;
    @BindView(R.id.pull_to_refresh_head)
    QMUIPullRefreshLayout pullToRefreshHead;

    protected int currentPage = 1;
    protected ArrayList<MomentsModel.DataBean> modelData=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_my_published;
    }

    @Override
    protected void initVariable() {
        MYID = getIntent().getIntExtra(IDCODE, -1);
        // 状态栏沉浸
        QMUIStatusBarHelper.translucent(this);
        // grid 布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvPublished.setLayoutManager(gridLayoutManager);
        QMUIStatusBarHelper.translucent(this);
        pullToRefreshHead.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                currentPage = 1;
                adapter.clear();
                loadData();
            }
        });

        adapter = new MyPublishedAdapter(this, modelData, R.layout.item_my_published);
        adapter.setOnItemClickedListener(this);
        rvPublished.setPullRefreshEnabled(false);
        rvPublished.setLoadingListener(this);
        rvPublished.setAdapter(adapter);
    }

    public void processData(MomentsModel models) {
        if (models.getData().size() != 0) {
            rvPublished.setVisibility(View.VISIBLE);
            for (MomentsModel.DataBean bean:models.getData()){
                modelData.add(bean);
            }
            adapter.notifyDataSetChanged();
        } else {
            // 到头了或者没有
            if (modelData.size() == 0){
                rvPublished.setVisibility(View.GONE);
            }
        }
        rvPublished.refreshComplete();
        pullToRefreshHead.finishRefresh();
    }

    @Override
    protected void initView() {
        initTopBar("我的发布");
    }

    @Override
    protected void loadData() {
        // 等待接口，先测试
        mPresenter.getOnesMoments(MYID,currentPage);
    }


    @Override
    protected MineFragmentContract.Presenter initPresenter() {
        return new MineFragmentPresenter(this);
    }

    @Override
    public void setPresenter(MineFragmentContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void onItemClicked(View view, MomentsModel.DataBean model, int position) {
        // 跳转到详情页面
        Intent intent = new Intent(this, FitDetailActivity.class);
        intent.putExtra(FitDetailActivity.DETAIL_CODE, new Gson().toJson(model));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        currentPage = currentPage + 1;
        loadData();
    }
}
