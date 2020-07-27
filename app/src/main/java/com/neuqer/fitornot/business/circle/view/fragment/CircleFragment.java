package com.neuqer.fitornot.business.circle.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.fragment.BasePresenterFragment;
import com.neuqer.fitornot.business.circle.contract.CircleContract;
import com.neuqer.fitornot.business.circle.presenter.CircleFragmentPresenter;
import com.neuqer.fitornot.business.circle.view.adapter.NonScrollViewPager;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.yuyakaido.android.cardstackview.CardStackView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Kingtous
 * @since 2019/9/26
 * email me@kingtous.cn
 */

public class CircleFragment extends BasePresenterFragment<CircleContract.Presenter> implements CircleContract.View {

    @BindView(R.id.tlayout_label)
    QMUITabSegment tlayoutLabel;
    @BindView(R.id.vp_card)
    NonScrollViewPager vpCard;


    Fragment[] fragments; // 0号：全部，1号：关注
    QMUITabSegment.Tab[] tabs;
    @BindView(R.id.pull_to_refresh_head)
    QMUIPullRefreshLayout pullToRefreshHead;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initVariable() {
        // 构建tab
        QMUITabSegment.Tab tab1 = new QMUITabSegment.Tab("推荐");
        QMUITabSegment.Tab tab2 = new QMUITabSegment.Tab("关注");
        tab1.setContentWidth(50);
        tab2.setContentWidth(50);
        tab1.setTextColor(getResources().getColor(R.color.white), getResources().getColor(R.color.white));
        tab2.setTextColor(getResources().getColor(R.color.white), getResources().getColor(R.color.white));
        tabs = new QMUITabSegment.Tab[]{tab1, tab2};
        for (QMUITabSegment.Tab tab : tabs) {
            tlayoutLabel.addTab(tab);
        }

    }

    @Override
    protected void initView() {
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
        pullToRefreshHead.finishRefresh();
        fragments = new Fragment[]{new ALLCardFragment(), new FollowCardFragment()};
        vpCard.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position].getText();
            }
        });
        tlayoutLabel.setForegroundGravity(Gravity.CENTER);
        tlayoutLabel.setDefaultNormalColor(getResources().getColor(R.color.white));
        tlayoutLabel.setDefaultSelectedColor(getResources().getColor(R.color.white));
        tlayoutLabel.setBackgroundColor(getResources().getColor(R.color.app_main));
        tlayoutLabel.setupWithViewPager(vpCard);
    }

    @Override
    protected CircleContract.Presenter initPresenter() {
        return new CircleFragmentPresenter(this);
    }
}
