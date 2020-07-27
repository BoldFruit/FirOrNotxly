package com.neuqer.fitornot.business.fittingroom.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.NoBarActivity;
import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.clothes.modle.response.GetClothesRspModle;
import com.neuqer.fitornot.business.clothes.modle.response.GetSuitByWordRspModle;
import com.neuqer.fitornot.business.clothes.view.ClothesFragment;
import com.neuqer.fitornot.business.clothes.view.adapter.BitmapAdapter;
import com.neuqer.fitornot.business.fittingroom.model.request.SaveSuitReqModel;
import com.neuqer.fitornot.business.fittingroom.model.response.GetSuitRspModle;
import com.neuqer.fitornot.network.NetWorkFactory;
import com.neuqer.fitornot.network.RequestBean.SetSuitModel;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.Loader.MyUtils;
import com.neuqer.fitornot.util.LogUtil;
import com.neuqer.fitornot.util.QnHelper.QnHelper;
import com.neuqer.fitornot.util.QnHelper.QnUploadHelper;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.forward.androids.utils.StatusBarUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SaveCollocationActivity extends NoBarActivity {


    @BindView(R.id.img_return_collocation)
    ImageView imgReturnCollocation;
    @BindView(R.id.txt_collocation_name)
    TextView txtCollocationName;
    @BindView(R.id.edit_collocation_name)
    EditText editCollocationName;
    @BindView(R.id.txt_collocation_classfy)
    TextView txtCollocationClassfy;
    @BindView(R.id.spin_category)
    Spinner spinCategory;
    @BindView(R.id.txt_add_lables)
    TextView txtAddLables;
    @BindView(R.id.flayout_lable_content)
    TagFlowLayout flayoutLableContent;
    @BindView(R.id.glayout_location)
    TagFlowLayout glayoutLocation;
    @BindView(R.id.glayout_style)
    TagFlowLayout glayoutStyle;
    @BindView(R.id.glayout_season)
    TagFlowLayout glayoutSeason;
    @BindView(R.id.edit_remarks)
    EditText editRemarks;
    @BindView(R.id.img_save_collocation)
    ImageView imgSaveCollocation;
    @BindView(R.id.card_view_collocation)
    CardView mCardView;
    @BindView(R.id.img_show)
    ImageView mImageShow;
    @BindView(R.id.txt_collocation_detail)
    TextView mDetailText;
    @BindView(R.id.img_delete_suit)
    ImageView mDeleteClothes;
    //搭配的图片
    private byte[] mBitmap;
    //搭配中包含的衣物
    private ArrayList<Integer> mClothes;

    public static String[] mLocationLable = new String[]{"职场", "海边", "运动", "旅行", "聚会", "居家", "街头", "约会", "校园", "郊外"};
    public static String[] mStyleLable = new String[]{"休闲", "简约", "混搭", "中性", "甜美", "日系", "深系", "韩系", "清新田园", "复古", "中国风",
            "民族风", "嘻哈", "哥特", "自定义标签"};
    public static String[] mSeasonLable = new String[]{"春季", "夏季", "秋季", "冬季"};
    private String[] mSelectedLable = new String[]{};
    private List<String> mLocation;
    private List<String> mStyle;
    private List<String> mSeason;
    private List<String> mSelected;
    private int mSelectNumber = 0;
    private QMUIDialog.EditTextDialogBuilder mBuilder;

    private MyAdapter mSelectedAdapter;
    private LayoutInflater mLayoutInflater;
    private String mCategory;
    private boolean isPublish;
    private  boolean isSaveByTakePhoto = false;
    private int mode;
    public static final int NORMAL = 0;
    public static final int TAKE_PHOTO = 1;
    public static final int CLICK_SUIT = 2;
    private GetSuitByWordRspModle.DataEntity mDataEntity;


    public static void actionStart(Context mContext, ArrayList<Integer> clothes, boolean isPublish) {
        Intent mIntent = new Intent(mContext, SaveCollocationActivity.class);
        mIntent.putExtra("clothes", clothes);
        mIntent.putExtra("isPublish", isPublish);
        mIntent.putExtra("mode", 0);
        mContext.startActivity(mIntent);
    }

    public static void saveByTakePhoto(Context mContext) {
        Intent mIntent = new Intent(mContext, SaveCollocationActivity.class);
        mIntent.putExtra("isSaveByTakePhoto", true);
        mIntent.putExtra("mode", 1);
        ((Activity)mContext).startActivityForResult(mIntent, ClothesFragment.SAVE_SUIT_BY_TAKE_PHOTO);
    }

    public static void editSuit(Context mContext, GetSuitByWordRspModle.DataEntity data) {
        Intent mIntent = new Intent(mContext, SaveCollocationActivity.class);
        mIntent.putExtra("data", data);
        mIntent.putExtra("mode", 2);
        ((Activity) mContext).startActivityForResult(mIntent, ClothesFragment.CLICK_SUIT);
    }




    @Override
    protected int getLayoutId() {
        return R.layout.activity_save_collocation;
    }

    @Override
    protected void initVariable() {
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
        mode = getIntent().getIntExtra("mode", 0);
        mLayoutInflater = LayoutInflater.from(this);

        switch (mode) {
            case TAKE_PHOTO:
                mBitmap = BitmapAdapter.bitmapBytes;
                mCardView.setVisibility(View.VISIBLE);
                mImageShow.setImageBitmap(BitmapFactory.decodeByteArray(mBitmap, 0, mBitmap.length));
                mDetailText.setVisibility(View.INVISIBLE);
                break;
            case CLICK_SUIT:
                mCardView.setVisibility(View.VISIBLE);
                mDetailText.setVisibility(View.INVISIBLE);
                mDeleteClothes.setVisibility(View.VISIBLE);
                mDataEntity = (GetSuitByWordRspModle.DataEntity) getIntent().getSerializableExtra("data");
            case NORMAL:
                mBitmap = BitmapAdapter.bitmapBytes;
                mClothes = getIntent().getIntegerArrayListExtra("clothes");
                isPublish = getIntent().getBooleanExtra("isPublish", false);
        }

    }

    @Override
    protected void initView() {
        mSelectedAdapter = new MyAdapter(mSelected);
        flayoutLableContent.setAdapter(mSelectedAdapter);
        //为标签设置适配器，以及监听事件
        glayoutLocation.setAdapter(new TagAdapter<String>(mLocation) {
            @Override
            public View getView(FlowLayout parent, int position, String mO) {
                TextView mView = (TextView) mLayoutInflater.inflate(R.layout.lable_txt, glayoutLocation, false);
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



        glayoutSeason.setAdapter(new TagAdapter<String>(mSeason) {
            @Override
            public View getView(FlowLayout parent, int position, String mO) {
                TextView mView = (TextView) mLayoutInflater.inflate(R.layout.lable_txt, glayoutSeason, false);
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

        glayoutStyle.setAdapter(new TagAdapter<String>(mStyle) {
            @Override
            public View getView(FlowLayout parent, int position, String mO) {
                if (position != 14) {
                    TextView mView = (TextView) mLayoutInflater.inflate(R.layout.lable_txt, glayoutStyle, false);
                    mView.setText(mO);
                    return mView;
                } else {
                    return (LinearLayout) mLayoutInflater.inflate(R.layout.lable_custom_lable, glayoutStyle, false);
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
        glayoutStyle.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
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
        if (mode == CLICK_SUIT) {
            if (mDataEntity.getClothes() != null) {
                Glide.with(this)
                        .load(mDataEntity.getClothes())
                        .transition(GenericTransitionOptions.<android.graphics.drawable.Drawable>withNoTransition())
                        .into(mImageShow);
                editCollocationName.setText(mDataEntity.getTitle());
                editRemarks.setText(mDataEntity.getRemarks());
                for (GetSuitByWordRspModle.DataEntity.TagsEntity mTagsEntity : mDataEntity.getTags()) {
                    mSelected.add(mTagsEntity.getTag_name());
                }
                mSelectedAdapter.notifyDataChanged();
                if (mDataEntity.getCategory().equals("商务")) {
                    spinCategory.setSelection(0);
                } else {
                    spinCategory.setSelection(1);
                }
            }
            mDeleteClothes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetWorkFactory.getApiService()
                            .deleteSuit(mDataEntity.getId())
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
                                }});
                }
            });
        }
    }

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
    protected void loadData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarMode(this, false, R.color.fitting_toolbar_blue);
    }

    @OnClick({R.id.img_return_collocation, R.id.img_save_collocation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return_collocation:
                finish();
                break;
            case R.id.img_save_collocation:
                if (mode == CLICK_SUIT) {
                    ToastUtil.showToast("保存成功");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    setSuit();
                    showProgressDialog();
                }
                break;
        }
    }

    /**
     * 保存搭配
     */
    private void setSuit() {
        try {
            QnHelper mHelper = new QnHelper();
            mHelper.upLoadImageWithByteArray(mBitmap, new IDataCallBack.Callback<String>() {
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
                    mSuitEntity.setCategory((String) spinCategory.getSelectedItem());
                    mSuitEntity.setClothes(mS);
                    mSuitEntity.setBackground(null);
                    mSuitEntity.setClothes_ids(mClothes);
                    mSuitEntity.setRemarks(editRemarks.getText().toString());
                    mSuitEntity.setTitle(editCollocationName.getText().toString());
                    List<SaveSuitReqModel.SuitEntity.TagsEntity> tags = new ArrayList<>();
                    Set<Integer> mLocation = glayoutLocation.getSelectedList();
                    Set<Integer> mStyle = glayoutStyle.getSelectedList();
                    Set<Integer> mSeason = glayoutSeason.getSelectedList();
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
                                    LogUtil.e("保存搭配失败", e.toString());
                                    ToastUtil.showToast("保存搭配失败");
                                    disMissProgressDialog();
                                }

                                @Override
                                public void onNext(ApiResponse<Void> mVoidApiResponse) {
                                    LogUtil.e("保存搭配", "成功");
                                    BitmapAdapter.isChangeData = true;
                                    disMissProgressDialog();
                                    ToastUtil.showToastPic(R.drawable.ic_save_to_collocation_success, SaveCollocationActivity.this);
                                    if (isPublish) {
                                        NetWorkFactory.getApiService()
                                                .getNewSuit()
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.newThread())
                                                .subscribe(new Subscriber<ApiResponse<GetSuitRspModle>>() {
                                                    @Override
                                                    public void onCompleted() {

                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        LogUtil.e("获取搭配", "失败" + e.toString());
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onNext(ApiResponse<GetSuitRspModle> mGetClothesRspModleApiResponse) {
                                                        LogUtil.e("获取搭配", "成功");
                                                        int id = 0;
                                                        //获得第一个id
                                                        for (GetSuitRspModle.DataEntity data : mGetClothesRspModleApiResponse.getData().getData()){
                                                            id = data.getId();
                                                            break;
                                                        }
                                                        //跳转到发布界面
                                                        PublishToCircleActivity.actionStart(SaveCollocationActivity.this, location, style, season, id);
                                                        finish();
                                                    }
                                                });
                                    } else {
                                        if (mode == TAKE_PHOTO) {
                                            setResult(RESULT_OK);
                                            finish();
                                        } else {
                                            finish();
                                        }
                                    }
                                }
                            });
                }
            });
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }

}
