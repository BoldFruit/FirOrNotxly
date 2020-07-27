package com.neuqer.fitornot.business.clothes.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import com.neuqer.fitornot.business.clothes.modle.request.GetClothesByWordReqModle;
import com.neuqer.fitornot.business.clothes.modle.request.SetClothesReqModle;
import com.neuqer.fitornot.business.clothes.modle.request.UpdataClothesReqModle;
import com.neuqer.fitornot.business.clothes.modle.response.GetClothesRspModle;
import com.neuqer.fitornot.business.clothes.view.ClothesFragment;
import com.neuqer.fitornot.business.clothes.view.adapter.BitmapAdapter;
import com.neuqer.fitornot.business.fittingroom.view.activity.SaveCollocationActivity;
import com.neuqer.fitornot.network.NetWorkFactory;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.Loader.DialogUtil;
import com.neuqer.fitornot.util.Loader.ImageLoader;
import com.neuqer.fitornot.util.LogUtil;
import com.neuqer.fitornot.util.QnHelper.QnHelper;
import com.neuqer.fitornot.util.StatusBarUtils;
import com.neuqer.fitornot.util.ToastUtil;
import com.neuqer.fitornot.util.Utility;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
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

public class ClassfyClothesActivity extends BaseActivity {

    public   boolean isClickToHere;
    @BindView(R.id.img_return_fitting)
    ImageView imgReturnFitting;
    @BindView(R.id.img_show_clothes)
    ImageView imgShowClothes;
    @BindView(R.id.spin_class)
    Spinner spinClass;
    @BindView(R.id.edit_brand)
    EditText editBrand;
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
    @BindView(R.id.img_save_clothes)
    ImageView imgSaveClothes;
    @BindView(R.id.img_delete_clothes)
    ImageView imgDeleteClothes;
    private byte[] mPhoto;
    private ImageLoader mLoader;

    public static String[] mLocationLable = new String[]{"职场", "海边", "运动", "旅行", "聚会", "居家", "街头", "约会", "校园", "郊外"};
    public static String[] mStyleLable = new String[]{"休闲", "简约", "混搭", "中性", "甜美", "日系", "深系", "韩系", "清新田园", "复古", "中国风",
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
    private GetClothesRspModle.DataEntity mData;
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
        return R.layout.activity_classfy_clothes;
    }

    @Override
    protected void initVariable() {
        mLoader = ImageLoader.build(this);
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
                    return (LinearLayout) mLayoutInflater.inflate(R.layout.lable_custom_lable, flayoutLableStyle, false);
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
        //显示数据
        if (isClickToHere) {
             mData = (GetClothesRspModle.DataEntity)getIntent().getSerializableExtra("data");
            Glide.with(this)
                    .load(mData.getPic_url())
                    .transition(GenericTransitionOptions.<android.graphics.drawable.Drawable>withNoTransition())
                    .into(imgShowClothes);
            List<GetClothesRspModle.DataEntity.TagsEntity> tags = mData.getTags();
            for (GetClothesRspModle.DataEntity.TagsEntity tag : tags) {
                mSelected.add(tag.getTag_name());
            }
            mSelectedAdapter.notifyDataChanged();
            editBrand.setText(mData.getBrand());
            editContent.setText(mData.getRemarks());
            switch (mData.getCategory()) {
                case "上衣":
                    spinClass.setSelection(0);
                    break;
                case "裤子":
                    spinClass.setSelection(1);
                    break;
                case "裙装":
                    spinClass.setSelection(2);
                    break;
                case "鞋袜":
                    spinClass.setSelection(3);
                    break;
                case "配饰":
                    spinClass.setSelection(4);
                    break;
                case "包包":
                    spinClass.setSelection(5);
                    break;
                default:
                    break;
            }
            imgDeleteClothes.setVisibility(View.VISIBLE);
            imgDeleteClothes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetWorkFactory.getApiService()
                            .deleteClothes(mData.getId())
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<ApiResponse<Void>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    LogUtil.e("删除衣物", "失败");
                                }

                                @Override
                                public void onNext(ApiResponse<Void> mVoidApiResponse) {
                                    LogUtil.e("删除衣物", "成功");
                                    ToastUtil.showToast("删除衣物成功");
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

    @Override
    protected void loadData() {

    }

    public static void actionStart(Context mContext) {
        Intent mIntent = new Intent(mContext, ClassfyClothesActivity.class);
        ((Activity)mContext).startActivityForResult(mIntent, ClothesFragment.CLASSFY_CLOTHES);
    }

    public static void actionStart(Context mContext, GetClothesRspModle.DataEntity mDataEntity) {
        Intent mIntent = new Intent(mContext, ClassfyClothesActivity.class);
        mIntent.putExtra("data", mDataEntity);
        mIntent.putExtra("isClickToHere", true);
        ((Activity)(mContext)).startActivityForResult(mIntent, ClothesFragment.CLICK_SINGLE);
    }

    @OnClick({R.id.img_return_fitting, R.id.img_save_clothes})
    public void onViewClicked(View view)  {
        switch (view.getId()) {
            case R.id.img_return_fitting:
                finish();
                break;
            case R.id.img_save_clothes:
                if (isClickToHere) {
                    UpdataClothesReqModle mModle = new UpdataClothesReqModle();
                    UpdataClothesReqModle.ClothesEntity mClothesEntity = new UpdataClothesReqModle.ClothesEntity();
                    mClothesEntity.setBrand(editBrand.getText().toString());
                    mClothesEntity.setId(String.valueOf(mData.getId()));
                    mClothesEntity.setRemarks(editContent.getText().toString());
                    mModle.setClothes(mClothesEntity);
                    showProgressDialog();
                    NetWorkFactory.getApiServiceWithHeaders()
                            .updateClothes(mModle)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<ApiResponse<Void>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtil.showToast("更新服饰失败");
                                    LogUtil.e("更新服装", "失败");
                                    disMissProgressDialog();
                                }

                                @Override
                                public void onNext(ApiResponse<Void> mVoidApiResponse) {
                                    ToastUtil.showToast("更新服装成功");
                                    Utility.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setResult(RESULT_OK);
                                            finish();
                                        }
                                    }, 1000);
                                }
                            });
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
        QnHelper mQnHelper = new QnHelper();
        mQnHelper.upLoadImageWithByteArray(mPhoto, new IDataCallBack.Callback<String>() {
            @Override
            public void onFailedLoaded(String error) {
                LogUtil.e("七牛", "保存图片失败");
                disMissProgressDialog();
            }

            @Override
            public void onDataLoaded(String mS) {
                LogUtil.e("七牛", "保存图片成功");
                SetClothesReqModle mSetClothesReqModle = new SetClothesReqModle();
                SetClothesReqModle.ClothesEntity mClothesEntity = new SetClothesReqModle.ClothesEntity();
                if (editBrand.getText() != null) {
                    mClothesEntity.setBrand(editBrand.getText().toString());
                    mClothesEntity.setCategory((String)spinClass.getSelectedItem());
                    mClothesEntity.setPic_url(mS);
                    mClothesEntity.setRemarks(editContent.getText().toString());
                    List<SetClothesReqModle.ClothesEntity.TagsEntity> tags = new ArrayList<>();
                    Set<Integer> mLocation = flayoutLableLocation.getSelectedList();
                    Set<Integer> mStyle = flayoutLableStyle.getSelectedList();
                    Set<Integer> mSeason = flayoutLableSeason.getSelectedList();

                    for (int i : mLocation) {
                        SetClothesReqModle.ClothesEntity.TagsEntity tag = new SetClothesReqModle.ClothesEntity.TagsEntity();
                        tag.setTag_type("场景");
                        tag.setTag_name(mLocationLable[i]);
                        tags.add(tag);
                    }
                    for (int i : mSeason) {
                        SetClothesReqModle.ClothesEntity.TagsEntity tag = new SetClothesReqModle.ClothesEntity.TagsEntity();
                        tag.setTag_type("季节");
                        tag.setTag_name(mSeasonLable[i]);
                        tags.add(tag);
                    }
                    for (int i : mStyle) {
                        SetClothesReqModle.ClothesEntity.TagsEntity tag = new SetClothesReqModle.ClothesEntity.TagsEntity();
                        if (!mStyleLable[i].equals("自定义标签")) {
                            tag.setTag_type("风格");
                            tag.setTag_name(mStyleLable[i]);
                            tags.add(tag);
                        }
                    }
                    mClothesEntity.setTags(tags);
                    mSetClothesReqModle.setClothes(mClothesEntity);
                    NetWorkFactory.getApiServiceWithHeaders()
                            .setNewClothes(mSetClothesReqModle)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(new Subscriber<ApiResponse<Void>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtil.showToast("保存衣物失败");
                                    LogUtil.e("保存衣物", "失败" + e.toString());
                                }

                                @Override
                                public void onNext(ApiResponse<Void> mVoidApiResponse) {
                                    LogUtil.e("保存衣物", "成功");
                                    ToastUtil.showToastPic(R.drawable.ic_save_to_collocation_success, ClassfyClothesActivity.this);
                                    disMissProgressDialog();
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
            }
        });
    }
}
