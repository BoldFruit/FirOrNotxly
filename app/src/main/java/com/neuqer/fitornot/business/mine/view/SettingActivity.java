package com.neuqer.fitornot.business.mine.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.BasePresenterActivity;
import com.neuqer.fitornot.base.activity.NoBarActivity;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.presenter.MineFragmentPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BasePresenterActivity<MineFragmentContract.Presenter> implements MineFragmentContract.View {

    @BindView(R.id.swh_status)
    Switch swhStatus;
    @BindView(R.id.llayout_edit_figure_data)
    LinearLayout llayoutEditFigureData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initVariable() {


    }

    @Override
    protected void initView() {
//        int roundPx = DensityUtil.dp2px(this, 16);
//        int widthPx = DensityUtil.dp2px(this, 32);
//        Drawable[] drawable = swhStatus.getCompoundDrawables();
//        drawable[0].setBounds(0, 0, roundPx, roundPx);
//        drawable[1].setBounds(0, 0, widthPx, roundPx);
//        swhStatus.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);
        llayoutEditFigureData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void loadData() {

    }


    @Override
    protected MineFragmentContract.Presenter initPresenter() {
        return new MineFragmentPresenter(this);
    }
}
