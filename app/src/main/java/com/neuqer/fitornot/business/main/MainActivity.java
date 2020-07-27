package com.neuqer.fitornot.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.gabrielbb.cutout.CutOut;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.NoBarActivity;
import com.neuqer.fitornot.business.circle.view.fragment.CircleFragment;
import com.neuqer.fitornot.business.clothes.view.ClothesFragment;
import com.neuqer.fitornot.business.fittingroom.view.fragment.FittingRoomFragment;
import com.neuqer.fitornot.business.mine.view.MineFragment;
import com.neuqer.fitornot.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends NoBarActivity implements RadioGroup.OnCheckedChangeListener {


    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    @BindView(R.id.rb_fitting)
    RadioButton rbFitting;
    @BindView(R.id.rb_clothes)
    RadioButton rbClothes;
    @BindView(R.id.rb_circle)
    RadioButton rbCircle;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.group_tab_menu)
    RadioGroup groupTabMenu;
    @BindView(R.id.flayout_content)
    FrameLayout flayoutContent;


    private FragmentManager mFragmentManager;
    //四个界面的Fragment
    private FittingRoomFragment mFittingFragment;
    private ClothesFragment mClothesFragment;
    private CircleFragment mCircleFragment;
    private MineFragment mMineFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {
        mFragmentManager = getSupportFragmentManager();
    }

    /**
     * 隐藏所有的Fragment
     *
     * @param mFragmentTransaction
     */
    private void hideAllFragment(FragmentTransaction mFragmentTransaction) {
        if (mFittingFragment != null) {
            mFragmentTransaction.hide(mFittingFragment);
        }
        if (mClothesFragment != null) {
            mFragmentTransaction.hide(mClothesFragment);
        }
        if (mCircleFragment != null) {
            mFragmentTransaction.hide(mCircleFragment);
        }
        if (mMineFragment != null) {
            mFragmentTransaction.hide(mMineFragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //解决子Fragment中无法收到Result的问题
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager_main
// + ":" + viewpagerMain.getCurrentItem());
//        fragment.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ClothesFragment.ADD_PIC || requestCode == ClothesFragment.TAKE_PHOTO || requestCode == CutOut.CUTOUT_ACTIVITY_REQUEST_CODE || requestCode == ClothesFragment.CLICK_SINGLE || requestCode == ClothesFragment.CLICK_SUIT || requestCode == ClothesFragment.ADJUST_COLOR ||requestCode == ClothesFragment.CLASSFY_CLOTHES) {
            Fragment mFragment = getSupportFragmentManager().findFragmentByTag("clothesFragment");
            mFragment.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    protected void initView() {
        groupTabMenu.setOnCheckedChangeListener(this);
        rbFitting.setChecked(true);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarMode(MainActivity.this, true, R.color.white);
        //默认初始在第一个界面
    }

    //当底部导航栏选项改变时
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        hideAllFragment(mTransaction);
        switch (checkedId) {
            case R.id.rb_fitting:
                if (mFittingFragment == null) {
                    mFittingFragment = new FittingRoomFragment();
                    mTransaction.add(R.id.flayout_content, mFittingFragment, "fittingFragment");
                } else {
                    mTransaction.show(mFittingFragment);
                }
                break;
            case R.id.rb_clothes:
                if (mClothesFragment == null) {
                    mClothesFragment = new ClothesFragment();
                    mTransaction.add(R.id.flayout_content, mClothesFragment, "clothesFragment");
                } else {
                    mTransaction.show(mClothesFragment);
                }
                break;
            case R.id.rb_circle:
                if (mCircleFragment == null) {
                    mCircleFragment = new CircleFragment();
                    mTransaction.add(R.id.flayout_content, mCircleFragment, "circleFragment");
                } else {
                    mTransaction.show(mCircleFragment);
                }
                break;
            case R.id.rb_mine:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    mTransaction.add(R.id.flayout_content, mMineFragment, "mineFragment");
                } else {
                    mTransaction.show(mMineFragment);
                }
                break;
            default:
                break;
        }
        mTransaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ClothesFragment mFragment = (ClothesFragment) getSupportFragmentManager().findFragmentByTag("clothesFragment");
        mFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
