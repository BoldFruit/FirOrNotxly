package com.neuqer.fitornot.business.clothes.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.BaseActivity;
import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.clothes.modle.response.GetSuitByWordRspModle;
import com.neuqer.fitornot.business.clothes.view.ClothesFragment;
import com.neuqer.fitornot.business.clothes.view.adapter.BitmapAdapter;
import com.neuqer.fitornot.business.fittingroom.model.request.CreateMomentReqModle;
import com.neuqer.fitornot.business.fittingroom.model.request.SaveSuitReqModel;
import com.neuqer.fitornot.network.NetWorkFactory;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.LogUtil;
import com.neuqer.fitornot.util.QnHelper.QnHelper;
import com.neuqer.fitornot.util.StatusBarUtils;
import com.neuqer.fitornot.util.ToastUtil;
import com.neuqer.fitornot.util.Utility;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundLinearLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ClassfyCollocationActivity extends BaseActivity {


    public boolean isClickToHere;
    @BindView(R.id.img_return_fitting)
    ImageView imgReturnFitting;
    @BindView(R.id.img_delete_clothes)
    ImageView imgDeleteClothes;
    @BindView(R.id.img_show_clothes)
    ImageView imgShowClothes;
    @BindView(R.id.edit_brand)
    EditText editBrand;
    @BindView(R.id.spin_class)
    Spinner spinClass;
    @BindView(R.id.flayout_lable_content)
    TagFlowLayout flayoutLableContent;
    @BindView(R.id.flayout_lable_location)
    TagFlowLayout flayoutLableLocation;
    @BindView(R.id.flayout_lable_style)
    TagFlowLayout flayoutLableStyle;
    @BindView(R.id.flayout_lable_season)
    TagFlowLayout flayoutLableSeason;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.llayout_submit)
    QMUIRoundLinearLayout llayoutSubmit;
    @BindView(R.id.llayout_save)
    QMUIRoundLinearLayout llayoutSave;
    private byte[] mPhoto;

    public static String[] mLocationLable = new String[]{"职场", "海边", "运动", "旅行", "聚会", "居家", "街头", "约会", "校园", "郊外"};
    public static String[] mStyleLable = new String[]{"休闲", "简约", "混搭", "中性", "甜美", "日系", "深系", "韩系", "清新田园", "复古",
            "中国风",
            "民族风", "嘻哈", "哥特", "自定义标签"};
    public static String[] mSeasonLable = new String[]{"春季", "夏季", "秋季", "冬季"};
    private String[] mSelectedLable = new String[]{};
    private List<String> mLocation;
    private List<String> mStyle;
    private List<String> mSeason;
    private List<String> mSelected;
    //搭配中包含的衣物
    private ArrayList<Integer> mClothes;
    private QMUIDialog.EditTextDialogBuilder mBuilder;
    private MyAdapter mSelectedAdapter;
    private LayoutInflater mLayoutInflater;
    private GetSuitByWordRspModle.DataEntity mData;

    private class MyAdapter extends TagAdapter<String> {


        public MyAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, String mS) {
            TextView mView = (TextView) mLayoutInflater.inflate(R.layout.lable_txt, flayoutLableContent, false);
            mView.setText(mS);
            return mView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarMode(this, false, R.color.fitting_toolbar_blue);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classfy_collocation;
    }

    @Override
    protected void initVariable() {
        isClickToHere = getIntent().getBooleanExtra("isClickToHere", false);
        if (!isClickToHere) {
            mPhoto = BitmapAdapter.bitmapBytes;
            imgShowClothes.setImageBitmap(BitmapFactory.decodeByteArray(mPhoto, 0, mPhoto.length));
        }

        mBuilder = new QMUIDialog.EditTextDialogBuilder(this);
        mClothes = new ArrayList<>();
        mLocation = new ArrayList<>();
        mStyle = new ArrayList<>();
        mSeason = new ArrayList<>();
        mSelected = new ArrayList<>();
        mLocation.addAll(Arrays.asList(mLocationLable));
        mStyle.addAll(Arrays.asList(mStyleLable));
        mSeason.addAll(Arrays.asList(mSeasonLable));
        mSelected.addAll(Arrays.asList(mSelectedLable));
        mLayoutInflater = LayoutInflater.from(this);
    }

    @Override
    protected void initView() {
        mSelectedAdapter = new MyAdapter(mSelected);
        flayoutLableContent.setAdapter(mSelectedAdapter);
        //为标签设置适配器，以及监听事件
        flayoutLableLocation.setAdapter(new TagAdapter<String>(mLocation) {
            @Override
            public View getView(FlowLayout parent, int position, String mO) {
                TextView mView = (TextView) mLayoutInflater.inflate(R.layout.lable_txt, flayoutLableLocation, false);
                mView.setText(mO);
                return mView;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                mSelected.add(mLocation.get(position));
                mSelectedAdapter.notifyDataChanged();
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                mSelected.remove(mLocation.get(position));
                mSelectedAdapter.notifyDataChanged();
            }
        });

        flayoutLableSeason.setAdapter(new TagAdapter<String>(mSeason) {
            @Override
            public View getView(FlowLayout parent, int position, String mO) {
                TextView mView = (TextView) mLayoutInflater.inflate(R.layout.lable_txt, flayoutLableSeason, false);
                mView.setText(mO);
                return mView;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                mSelected.add(mSeason.get(position));
                mSelectedAdapter.notifyDataChanged();
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                mSelected.remove(mSeason.get(position));
                mSelectedAdapter.notifyDataChanged();
            }
        });

        flayoutLableStyle.setAdapter(new TagAdapter<String>(mStyle) {
            @Override
            public View getView(FlowLayout parent, int position, String mO) {
                if (position != 14) {
                    TextView mView = (TextView) mLayoutInflater.inflate(R.layout.lable_txt, flayoutLableStyle, false);
                    mView.setText(mO);
                    return mView;
                } else {
                    return (LinearLayout) mLayoutInflater.inflate(R.layout.lable_custom_lable, flayoutLableStyle,
                            false);
                }
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                if (position != 14) {
                    mSelected.add(mStyle.get(position));
                    mSelectedAdapter.notifyDataChanged();
                }
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                if (position != 14) {
                    mSelected.remove(mStyle.get(position));
                    mSelectedAdapter.notifyDataChanged();
                }
            }
        });

        //点击自定义标签时弹出dialog
        flayoutLableStyle.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (position == 14) {
                    //显示输入框
                    mBuilder.setTitle("标签")
                            .setPlaceholder("在此输入您自定义的标签")
                            .setInputType(InputType.TYPE_CLASS_TEXT)
                            .addAction("取消", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    CharSequence text = mBuilder.getEditText().getText();
                                    if (text != null && text.length() > 0) {
                                        mSelected.add(text.toString());
                                        mSelectedAdapter.notifyDataChanged();
                                    } else {
                                        ToastUtil.showToast("请输入正确的标签");
                                    }
                                    dialog.dismiss();
                                }
                            }).show();
                }
                return true;
            }
        });


        if (isClickToHere) {
            llayoutSubmit.setVisibility(View.VISIBLE);
            mData = (GetSuitByWordRspModle.DataEntity) getIntent().getSerializableExtra("data");
            Glide.with(this)
                    .load(mData.getClothes())
                    .transition(GenericTransitionOptions.<Drawable>withNoTransition())
                    .into(imgShowClothes);
            List<GetSuitByWordRspModle.DataEntity.TagsEntity> tags = mData.getTags();
            for (GetSuitByWordRspModle.DataEntity.TagsEntity tag : tags) {
                mSelected.add(tag.getTag_name());
            }
            mSelectedAdapter.notifyDataChanged();
            editContent.setText(mData.getRemarks());
            switch (mData.getCategory()) {
                case "商务":
                    spinClass.setSelection(0);
                    break;
                case "休闲":
                    spinClass.setSelection(1);
                    break;
                default:
                    break;
            }
            editBrand.setText(mData.getTitle());
            imgDeleteClothes.setVisibility(View.VISIBLE);
            llayoutSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressDialog();
                    CreateMomentReqModle mModle = new CreateMomentReqModle();
                    mModle.setContent(editContent.getText().toString());
                    mModle.setSuit_id(mData.getId());
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
                                    LogUtil.e("发布圈子", "失败");
                                    ToastUtil.showToast("发布圈子失败");
                                    disMissProgressDialog();
                                }

                                @Override
                                public void onNext(ApiResponse<Void> mVoidApiResponse) {
                                    LogUtil.e("发布圈子", "成功");
                                    disMissProgressDialog();
                                    ToastUtil.showToastPic(R.drawable.ic_publish_to_circle_success,
                                            ClassfyCollocationActivity.this);

                                    Utility.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 3000);
                                }
                            });

                }
            });
            imgDeleteClothes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetWorkFactory.getApiService()
                            .deleteSuit(mData.getId())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(new Subscriber<ApiResponse<Void>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtil.showToast("删除失败");
                                }

                                @Override
                                public void onNext(ApiResponse<Void> mVoidApiResponse) {
                                    ToastUtil.showToast("删除搭配成功");
                                    Utility.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setResult(RESULT_OK);
                                            finish();
                                        }
                                    }, 2000);
                                }
                            });
                }
            });
        }
    }

    public static void actionStart(Context mContext) {
        Intent mIntent = new Intent(mContext, ClassfyCollocationActivity.class);
        ((Activity) mContext).startActivityForResult(mIntent, ClothesFragment.CLASSFY_CLOTHES);
    }

    public static void actionStart(Context mContext, GetSuitByWordRspModle.DataEntity mDataEntity) {
        Intent mIntent = new Intent(mContext, ClassfyCollocationActivity.class);
        mIntent.putExtra("data", mDataEntity);
        mIntent.putExtra("isClickToHere", true);
        ((Activity) (mContext)).startActivityForResult(mIntent, ClothesFragment.CLICK_SINGLE);
    }

    @OnClick({R.id.img_return_fitting, R.id.llayout_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return_fitting:
                setResult(0);
                finish();
                break;
            case R.id.llayout_save:
                if (isClickToHere) {
                    ToastUtil.showToastPic(R.drawable.ic_save_to_collocation_success, ClassfyCollocationActivity.this);
                    Utility.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);
                } else {
                    try {
                        saveClothes();
                    } catch (Exception mE) {
                        mE.printStackTrace();
                    }
                    showProgressDialog();
                }
                break;
        }
    }

    private void saveClothes() throws Exception {
        try {
            QnHelper mHelper = new QnHelper();
            mHelper.upLoadImageWithByteArray(mPhoto, new IDataCallBack.Callback<String>() {
                //保存图片失败
                @Override
                public void onFailedLoaded(String error) {
                    LogUtil.e("七牛", "保存图片失败");
                    disMissProgressDialog();
                }

                @Override
                public void onDataLoaded(String mS) {
                    LogUtil.e("七牛", "保存图片成功");
                    SaveSuitReqModel mSaveSuitReqModel = new SaveSuitReqModel();
                    SaveSuitReqModel.SuitEntity mSuitEntity = new SaveSuitReqModel.SuitEntity();
                    //以下为封装数据
                    mSuitEntity.setCategory((String) spinClass.getSelectedItem());
                    mSuitEntity.setClothes(mS);
                    mSuitEntity.setBackground(null);
                    mSuitEntity.setClothes_ids(mClothes);
                    mSuitEntity.setRemarks(editContent.getText().toString());
                    mSuitEntity.setTitle(editBrand.getText().toString());
                    List<SaveSuitReqModel.SuitEntity.TagsEntity> tags = new ArrayList<>();
                    Set<Integer> mLocation = flayoutLableLocation.getSelectedList();
                    Set<Integer> mStyle = flayoutLableStyle.getSelectedList();
                    Set<Integer> mSeason = flayoutLableSeason.getSelectedList();
                    ArrayList<Integer> location = new ArrayList<>();
                    ArrayList<Integer> style = new ArrayList<>();
                    ArrayList<Integer> season = new ArrayList<>();

                    for (int i : mLocation) {
                        SaveSuitReqModel.SuitEntity.TagsEntity tag = new SaveSuitReqModel.SuitEntity.TagsEntity();
                        tag.setTag_type("场景");
                        tag.setTag_name(mLocationLable[i]);
                        tags.add(tag);
                        location.add(i);
                    }
                    for (int i : mSeason) {
                        SaveSuitReqModel.SuitEntity.TagsEntity tag = new SaveSuitReqModel.SuitEntity.TagsEntity();
                        tag.setTag_type("季节");
                        tag.setTag_name(mSeasonLable[i]);
                        tags.add(tag);
                        season.add(i);
                    }
                    for (int i : mStyle) {
                        SaveSuitReqModel.SuitEntity.TagsEntity tag = new SaveSuitReqModel.SuitEntity.TagsEntity();
                        if (!mStyleLable[i].equals("自定义标签")) {
                            tag.setTag_type("风格");
                            tag.setTag_name(mStyleLable[i]);
                            tags.add(tag);
                            style.add(i);
                        }
                    }
                    mSuitEntity.setTags(tags);
                    mSaveSuitReqModel.setSuit(mSuitEntity);

                    //网络请求
                    NetWorkFactory.getApiServiceWithHeaders()
                            .setNewSuit(mSaveSuitReqModel)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(new Subscriber<ApiResponse<Void>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtil.showToast("保存搭配失败");
                                    LogUtil.e("保存搭配失败", e.toString());
                                    disMissProgressDialog();
                                }

                                @Override
                                public void onNext(ApiResponse<Void> mVoidApiResponse) {
                                    LogUtil.e("保存搭配", "成功");
                                    disMissProgressDialog();
                                    ToastUtil.showToastPic(R.drawable.ic_save_to_collocation_success,
                                            ClassfyCollocationActivity.this);
                                    Utility.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setResult(RESULT_OK);
                                            finish();
                                        }
                                    });
                                }
                            });
                }
            });
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }

    @Override
    protected void loadData() {

    }
}
