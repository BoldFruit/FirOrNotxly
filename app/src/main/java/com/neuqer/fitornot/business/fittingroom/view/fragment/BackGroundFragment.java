package com.neuqer.fitornot.business.fittingroom.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.adapter.BaseViewHolder;
import com.neuqer.fitornot.base.fragment.BaseFragment;
import com.neuqer.fitornot.business.fittingroom.view.adapter.BackGroundRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DuLong
 * @since 2019/10/2
 * email 798382030@qq.com
 */

public class BackGroundFragment extends BaseFragment {

    private RecyclerView mRecyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_background;
    }

    @Override
    protected void initVariable() {
        List<String[]> mData = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            //模拟数据
            String[] data = new String[2];
            data[0] = "http://pyimbkzvk.bkt.clouddn.com/t015963ce092ca4872e.webp.jpg";
            data[1] = "http://pyimbkzvk.bkt.clouddn.com/t017021415ac1e5e051.webp.jpg";
            mData.add(data);
        }
        mRecyclerView = getRootView().findViewById(R.id.recyclerview_backphoto);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        BackGroundRecyclerViewAdapter mAdapter = new BackGroundRecyclerViewAdapter(getActivity(), mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void loadData() {

    }

}
