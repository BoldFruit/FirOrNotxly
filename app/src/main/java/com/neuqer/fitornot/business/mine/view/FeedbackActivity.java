package com.neuqer.fitornot.business.mine.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.BasePresenterActivity;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.presenter.MineFragmentPresenter;
import com.neuqer.fitornot.util.ToastUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: Kingtous
 * Since: 2019-09-26
 * Email: me@kingtous.cn
 */

public class FeedbackActivity extends BasePresenterActivity<MineFragmentContract.Presenter> implements MineFragmentContract.View, View.OnClickListener {


    @BindView(R.id.edit_detail)
    EditText editDetail;
    @BindView(R.id.llayout_submit)
    QMUIRoundLinearLayout llayoutSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initVariable() {
        initTopBar("建议反馈");
        QMUIStatusBarHelper.translucent(this);
    }

    @Override
    protected void initView() {
        llayoutSubmit.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected MineFragmentPresenter initPresenter() {
        return new MineFragmentPresenter(this);
    }

    @Override
    public void onClick(View view) {
        ToastUtil.showToast("提交成功");
        finish();
    }

}
