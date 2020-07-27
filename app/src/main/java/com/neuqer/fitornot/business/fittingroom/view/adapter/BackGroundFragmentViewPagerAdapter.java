package com.neuqer.fitornot.business.fittingroom.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.neuqer.fitornot.business.fittingroom.view.fragment.BackGroundFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DuLong
 * @since 2019/10/2
 * email 798382030@qq.com
 */

public class BackGroundFragmentViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitle = new String[] {"全部", "纯色", "聚会", "旅行", "运动", "职场", "居家"};
    private List<BackGroundFragment> mFragments = new ArrayList<>();

    public BackGroundFragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i < 7; i++) {
            mFragments.add(new BackGroundFragment());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
