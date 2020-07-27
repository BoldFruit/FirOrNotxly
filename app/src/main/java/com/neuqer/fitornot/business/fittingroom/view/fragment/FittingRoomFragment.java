package com.neuqer.fitornot.business.fittingroom.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.fragment.BaseFragment;
import com.neuqer.fitornot.business.fittingroom.view.activity.CollocationActivity;

import butterknife.BindView;

/**
 * @author DuLong
 * @since 2019/9/28
 * email 798382030@qq.com
 */

public class FittingRoomFragment extends BaseFragment {

    @BindView(R.id.img_add_collocation)
    ImageView imgAddCollocation;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fitting_room_firstpage;
    }

    @Override
    protected void initVariable() {
    }

    @Override
    protected void initView() {
        imgAddCollocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CollocationActivity.class));
            }
        });
    }

    @Override
    protected void loadData() {

    }

}
