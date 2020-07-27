package com.neuqer.fitornot.business.fittingroom.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.NoBarActivity;
import com.neuqer.fitornot.business.fittingroom.view.adapter.BackGroundRecyclerViewAdapter;
import com.neuqer.fitornot.util.StatusBarUtils;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeBackGroundActivity extends NoBarActivity implements TabLayout.OnTabSelectedListener {


    public static final int ALL = 0;
    public static final int PUR_COLOR = 1;
    public static final int PARTY = 2;
    public static final int TRANVEL = 3;
    public static final int SPORTS = 4;
    public static final int WORK = 5;
    public static final int HOME = 6;
    @BindView(R.id.img_return_fitting)
    ImageView imgReturnFitting;
    @BindView(R.id.tlayout_background_tab)
    TabLayout tlayoutBackgroundTab;
    @BindView(R.id.recyclerview_backphoto)
    RecyclerView recyclerviewBackphoto;
    private List<String[]> mCurData;
    private Map<String, List<String[]>> mMap;
    private String[] mPurColor = {"http://pyimbkzvk.bkt.clouddn.com/image/background/%E7%BA%AF%E8%89%B21.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E7%BA%AF%E8%89%B22.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E7%BA%AF%E8%89%B23.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E7%BA%AF%E8%89%B24.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E7%BA%AF%E8%89%B25.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E7%BA%AF%E8%89%B26.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E7%BA%AF%E8%89%B27.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/20191007195833"};
    private String[] mTogether = {"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%81%9A%E4%BC%9A1.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%81%9A%E4%BC%9A2.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%81%9A%E4%BC%9A3.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%81%9A%E4%BC%9A4.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%81%9A%E4%BC%9A5.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%81%9A%E4%BC%9A6.png"
    };
    private String[] mTravel = {"http://pyimbkzvk.bkt.clouddn.com/image/background/%E6%97%85%E8%A1%8C1.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E6%97%85%E8%A1%8C2.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E6%97%85%E8%A1%8C3.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E6%97%85%E8%A1%8C4.png"
            };
    private String[] mSports = {"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%BF%90%E5%8A%A81.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%BF%90%E5%8A%A82.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%BF%90%E5%8A%A83.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%BF%90%E5%8A%A84.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%BF%90%E5%8A%A85.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/eab1b5552052dd0986addc7b58891836.jpg"};
    private String[] mPosition = {"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%81%8C%E5%9C%BA1.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%81%8C%E5%9C%BA2.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%81%8C%E5%9C%BA3.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E8%81%8C%E5%9C%BA4.png"
            };
    private String[] mHome = {"http://pyimbkzvk.bkt.clouddn.com/image/background/%E5%B1%85%E5%AE%B61.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E5%B1%85%E5%AE%B62.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E5%B1%85%E5%AE%B63.png"
            ,"http://pyimbkzvk.bkt.clouddn.com/image/background/%E5%B1%85%E5%AE%B64.png"
            };



    private BackGroundRecyclerViewAdapter mBackGroundRecyclerViewAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_back_ground;
    }

    @Override
    protected void initVariable() {
        mCurData = new ArrayList<>();
        List<String[]> mPurColorData = new ArrayList<>();
        List<String[]> mPositonData = new ArrayList<>();
        List<String[]> mHomeData = new ArrayList<>();
        List<String[]> mSportsData = new ArrayList<>();
        List<String[]> mTravelData = new ArrayList<>();
        List<String[]> mTogetherData = new ArrayList<>();
        mMap = new HashMap<>();
//       vpagerContainer.setAdapter(new BackGroundFragmentViewPagerAdapter(getSupportFragmentManager()));
        tlayoutBackgroundTab.addTab(tlayoutBackgroundTab.newTab().setText("全部"));
        tlayoutBackgroundTab.addTab(tlayoutBackgroundTab.newTab().setText("纯色"));
        tlayoutBackgroundTab.addTab(tlayoutBackgroundTab.newTab().setText("聚会"));
        tlayoutBackgroundTab.addTab(tlayoutBackgroundTab.newTab().setText("旅行"));
        tlayoutBackgroundTab.addTab(tlayoutBackgroundTab.newTab().setText("运动"));
        tlayoutBackgroundTab.addTab(tlayoutBackgroundTab.newTab().setText("职场"));
        tlayoutBackgroundTab.addTab(tlayoutBackgroundTab.newTab().setText("居家"));
        //关联viewPager
        tlayoutBackgroundTab.addOnTabSelectedListener(this);
//        tlayoutBackgroundTab.setupWithViewPager(vpagerContainer);
        List<String[]> mData = new ArrayList<>();
        List<String[]> mAllData = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String[] data = new String[2];
            data[0] = mPurColor[2 * i];
            data[1] = mPurColor[2 * i + 1];
            mData.add(data);
        }
        mAllData.addAll(mData);
        mPurColorData.addAll(mData);
        mMap.put("purColor", mPurColorData);
        mData.clear();
        for (int i = 0; i < 3; i++) {
            String[] data = new String[2];
            data[0] = mTogether[2 * i];
            data[1] = mTogether[2 * i + 1];
            mData.add(data);
        }
        mTogetherData.addAll(mData);
        mMap.put("together", mTogetherData);
        mAllData.addAll(mData);
        mData.clear();
        for (int i = 0; i < 2; i++) {
            String[] data = new String[2];
            data[0] = mTravel[2 * i];
            data[1] = mTravel[2 * i + 1];
            mData.add(data);
        }
        mTravelData.addAll(mData);
        mMap.put("travel", mTravelData);
        mAllData.addAll(mData);
        mData.clear();
        for (int i = 0; i < 3; i++) {
            String[] data = new String[2];
            data[0] = mSports[2 * i];
            data[1] = mSports[2 * i + 1];
            mData.add(data);
        }
        mSportsData.addAll(mData);
        mMap.put("sports", mSportsData);
        mAllData.addAll(mData);
        mData.clear();
        for (int i = 0; i < 2; i++) {
            String[] data = new String[2];
            data[0] = mPosition[2 * i];
            data[1] = mPosition[2 * i + 1];
            mData.add(data);
        }
        mPositonData.addAll(mData);
        mMap.put("position", mPositonData);
        mAllData.addAll(mData);
        mData.clear();
        for (int i = 0; i < 2; i++) {
            String[] data = new String[2];
            data[0] = mHome[2 * i];
            data[1] = mHome[2 * i + 1];
            mData.add(data);
        }
        mHomeData.addAll(mData);
        mMap.put("home", mHomeData);
        mAllData.addAll(mData);
        mData.clear();
        mMap.put("all", mAllData);

        mCurData = new ArrayList<>();
        mCurData.addAll(mMap.get("all"));
        //初始化recyclerview
        mBackGroundRecyclerViewAdapter = new BackGroundRecyclerViewAdapter(this, mCurData);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerviewBackphoto.setLayoutManager(mLayoutManager);
        recyclerviewBackphoto.setAdapter(mBackGroundRecyclerViewAdapter);

    }

    @Override
    protected void initView() {
        imgReturnFitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarMode(this, true, R.color.fitting_toolbar_blue);
    }


    @Override
    public void onTabSelected(TabLayout.Tab mTab) {
        switch (mTab.getText().toString()) {
            case "全部":
                mCurData.clear();
                mCurData.addAll(mMap.get("all"));
                mBackGroundRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case "纯色":
                mCurData.clear();
                mCurData.addAll(mMap.get("purColor"));
                mBackGroundRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case "聚会":
                mCurData.clear();
                mCurData.addAll(mMap.get("together"));
                mBackGroundRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case "旅行":
                mCurData.clear();
                mCurData.addAll(mMap.get("travel"));
                mBackGroundRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case "运动":
                mCurData.clear();
                mCurData.addAll(mMap.get("sports"));
                mBackGroundRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case "职场":
                mCurData.clear();
                mCurData.addAll(mMap.get("position"));
                mBackGroundRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case "居家":
                mCurData.clear();
                mCurData.addAll(mMap.get("home"));
                mBackGroundRecyclerViewAdapter.notifyDataSetChanged();
                break;
            default:
                break;

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab mTab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab mTab) {

    }


}
