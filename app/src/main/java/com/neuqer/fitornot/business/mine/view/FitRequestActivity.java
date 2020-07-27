package com.neuqer.fitornot.business.mine.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.BasePresenterActivity;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;
import com.neuqer.fitornot.business.mine.presenter.MineFragmentPresenter;
import com.neuqer.fitornot.business.mine.view.adapter.FitRequestAdapter;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: Kingtous
 * Since: 2019-10-01
 * Email: me@kingtous.cn
 */
public class FitRequestActivity extends BasePresenterActivity<MineFragmentContract.Presenter> implements MineFragmentContract.View {

    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;
    @BindView(R.id.rv_fit_request)
    RecyclerView rvFitRequest;
    private FitRequestAdapter mAdapter;
    private MineFragmentContract.Presenter mPresenter;

    @Override
    protected MineFragmentContract.Presenter initPresenter() {
        return new MineFragmentPresenter(this);
    }

    @Override
    public void setPresenter(MineFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_fit_request;
    }

    @Override
    protected void initVariable() {
        // 标题栏
       initTopBar("搭配请求");
        QMUIStatusBarHelper.translucent(this);
        // 列表
        // 等接口，先测试
        ArrayList<UserInfoModel> test_model = new ArrayList<>();
        UserInfoModel model=new UserInfoModel();
        model.setNickname("你的小可爱");
        model.setSignature("求求大佬！");
        model.setAvatar_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569928129835&di=2157486b2fef4185f069e5bec856f3c6&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg");
        test_model.add(model);
        model=new UserInfoModel();
        model.setNickname("你的大可爱");
        model.setSignature("求求大佬！");
        model.setAvatar_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569928129835&di=2157486b2fef4185f069e5bec856f3c6&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg");
        test_model.add(model);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvFitRequest.setLayoutManager(linearLayoutManager);
        processData(test_model);
    }

    private void processData(List<UserInfoModel> models){
        if (mAdapter!=null){
            mAdapter.clear();
        }
        mAdapter=new FitRequestAdapter(this,models,R.layout.item_fit_request);
        rvFitRequest.setAdapter(mAdapter);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }
}
