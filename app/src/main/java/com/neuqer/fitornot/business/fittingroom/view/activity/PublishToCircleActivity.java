package com.neuqer.fitornot.business.fittingroom.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.NoBarActivity;
import com.neuqer.fitornot.business.fittingroom.model.request.CreateMomentReqModle;
import com.neuqer.fitornot.common.ApiException;
import com.neuqer.fitornot.network.NetWorkFactory;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.LogUtil;
import com.neuqer.fitornot.util.StatusBarUtils;
import com.neuqer.fitornot.util.ToastUtil;
import com.neuqer.fitornot.util.Utility;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PublishToCircleActivity extends NoBarActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_publish)
    ImageView imgPublish;
    @BindView(R.id.flayout_selected_lable)
    TagFlowLayout flayoutSelectedLable;
    @BindView(R.id.flayout_lable_location)
    TagFlowLayout flayoutLableLocation;
    @BindView(R.id.flayout_lable_style)
    TagFlowLayout flayoutLableStyle;
    @BindView(R.id.flayout_lable_season)
    TagFlowLayout flayoutLableSeason;
    @BindView(R.id.edit_content)
    EditText editContent;
    private List<Integer> mLocation;
    private List<Integer> mStyle;
    private List<Integer> mSeason;
    private List<String> mSelected;
    private int id;
    private LayoutInflater mInflater;

    public static void actionStart(Context mContext, ArrayList<Integer> mLocation, ArrayList<Integer> mStyle,
                                   ArrayList<Integer> mSeason, int id) {
        Intent mIntent = new Intent(mContext, PublishToCircleActivity.class);
        mIntent.putExtra("location", mLocation);
        mIntent.putExtra("style", mStyle);
        mIntent.putExtra("season", mSeason);
        mIntent.putExtra("id", id);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarMode(this, false, R.color.fitting_toolbar_blue);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_to_circle;
    }

    @Override
    protected void initVariable() {
        mLocation = getIntent().getIntegerArrayListExtra("location");
        mStyle = getIntent().getIntegerArrayListExtra("style");
        mSeason = getIntent().getIntegerArrayListExtra("season");
        id = getIntent().getIntExtra("id", 0);
        mInflater = LayoutInflater.from(this);
    }

    @Override
    protected void initView() {
        mSelected = new ArrayList<>();

        flayoutLableLocation.setAdapter(new TagAdapter<String>(SaveCollocationActivity.mLocationLable) {
            @Override
            public View getView(FlowLayout parent, int position, String mS) {
                TextView mView = (TextView) mInflater.inflate(R.layout.lable_txt, flayoutLableLocation, false);
                mView.setText(mS);
                return mView;
            }

        });

        flayoutLableStyle.setAdapter(new TagAdapter<String>(SaveCollocationActivity.mStyleLable) {
            @Override
            public View getView(FlowLayout parent, int position, String mS) {
                TextView mView = (TextView) mInflater.inflate(R.layout.lable_txt, flayoutLableStyle, false);
                mView.setText(mS);
                return mView;
            }

        });

        flayoutLableSeason.setAdapter(new TagAdapter<String>(SaveCollocationActivity.mSeasonLable) {
            @Override
            public View getView(FlowLayout parent, int position, String mS) {
                TextView mView = (TextView) mInflater.inflate(R.layout.lable_txt, flayoutLableSeason, false);
                mView.setText(mS);
                return mView;
            }
        });

        for (int i : mLocation) {
            mSelected.add(SaveCollocationActivity.mLocationLable[i]);
        }
        for (int i : mStyle) {
            mSelected.add(SaveCollocationActivity.mStyleLable[i]);
        }
        for (int i : mSeason) {
            mSelected.add(SaveCollocationActivity.mSeasonLable[i]);
        }

        flayoutSelectedLable.setAdapter(new TagAdapter<String>(mSelected) {
            @Override
            public View getView(FlowLayout parent, int position, String mS) {
                TextView mView = (TextView) mInflater.inflate(R.layout.lable_txt, flayoutSelectedLable, false);
                mView.setText(mS);
                return mView;
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.img_back, R.id.img_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_publish:
                showProgressDialog();
                CreateMomentReqModle mModle = new CreateMomentReqModle();
                mModle.setContent(editContent.getText().toString());
                mModle.setSuit_id(id);
                NetWorkFactory.getApiServiceWithHeaders()
                        .createMoment(mModle)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ApiResponse<Void>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtil.showToast("发布圈子失败");
                                LogUtil.e("发布圈子", "失败");
                                disMissProgressDialog();
                            }

                            @Override
                            public void onNext(ApiResponse<Void> mVoidApiResponse) {
                                LogUtil.e("发布圈子", "成功");
                                disMissProgressDialog();
                                ToastUtil.showToastPic(R.drawable.ic_publish_to_circle_success, PublishToCircleActivity.this);

                                Utility.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 3000);
                            }
                        });
                break;
        }
    }
}
