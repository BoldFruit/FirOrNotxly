package com.neuqer.fitornot.business.clothes.view;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.gabrielbb.cutout.CutOut;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.adapter.BaseViewHolder;
import com.neuqer.fitornot.base.fragment.BaseFragment;
import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.clothes.modle.request.GetClothesByWordReqModle;
import com.neuqer.fitornot.business.clothes.modle.response.GetClothesRspModle;
import com.neuqer.fitornot.business.clothes.modle.response.GetSuitByWordRspModle;
import com.neuqer.fitornot.business.clothes.view.activity.AdjustColorActivity;
import com.neuqer.fitornot.business.clothes.view.activity.ClassfyClothesActivity;
import com.neuqer.fitornot.business.clothes.view.activity.ClassfyCollocationActivity;
import com.neuqer.fitornot.business.clothes.view.adapter.BitmapAdapter;
import com.neuqer.fitornot.business.fittingroom.model.FittingRoomHelper;
import com.neuqer.fitornot.business.fittingroom.view.activity.SaveCollocationActivity;
import com.neuqer.fitornot.network.NetWorkFactory;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.Loader.ImageLoader;
import com.neuqer.fitornot.util.LogUtil;
import com.neuqer.fitornot.util.ToastUtil;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.neuqer.fitornot.util.Loader.ImageLoader.compressImage;
import static com.neuqer.fitornot.util.Loader.ImageLoader.getBitmapDegree;
import static com.neuqer.fitornot.util.Loader.ImageLoader.getBitmapFormUri;
import static com.neuqer.fitornot.util.Loader.ImageLoader.getFileFromMediaUri;
import static com.neuqer.fitornot.util.Loader.ImageLoader.getSmallBitmap;
import static com.neuqer.fitornot.util.Loader.ImageLoader.getSmallBitmapByBitmap;
import static com.neuqer.fitornot.util.Loader.ImageLoader.rotateBitmapByDegree;

/**
 * @author DuLong
 * @since 2019/8/18
 * email 798382030@qq.com
 */

public class ClothesFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, View.OnClickListener{
    public final static int CLICK_SUIT = 11;
    public final static int CLICK_SINGLE = 10;
    public final static int ADJUST_COLOR = 12;
    public final static int CLASSFY_CLOTHES = 13;
    public final static int SAVE_SUIT_BY_TAKE_PHOTO = 14;
    private File mInstantFile;
    @BindView(R.id.tlayout_header)
    TabLayout tlayoutHeader;
    @BindView(R.id.tlayout_single_clothes)
    TabLayout tlayoutSingleClothes;
    @BindView(R.id.tlayout_collocation)
    TabLayout tlayoutCollocation;
    @BindView(R.id.recyclerview_content)
    RecyclerView recyclerviewContent;
    @BindView(R.id.float_button)
    ImageView floatButton;
    int num;
    private QMUITipDialog mDialog;
    private boolean isLocateInSingle;
    List<GetClothesRspModle.DataEntity> mClothes;
    List<GetClothesRspModle.DataEntity> mTrousers;
    List<GetClothesRspModle.DataEntity> mSkirts;
    List<GetClothesRspModle.DataEntity> mShows;
    List<GetClothesRspModle.DataEntity> mDecorators;
    List<GetClothesRspModle.DataEntity> mBags;
    List<GetClothesRspModle.DataEntity> mSingleData;

    List<GetSuitByWordRspModle.DataEntity> mNormal;
    List<GetSuitByWordRspModle.DataEntity> mFormal;
    List<GetSuitByWordRspModle.DataEntity> mSuitData;

    String[] mWord ;
    GetClothesByWordReqModle[] mModle;
    MyAdapter mMyAdapter;
    MySuitAdapter mMySuitAdapter;
    private Context mContext;
    public static final int ADD_PIC = 1;
    public static final int TAKE_PHOTO = 2;
    private ImageLoader mLoader;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_clothes;
    }

    @Override
    protected void initVariable() {
        mLoader = ImageLoader.build(getActivity());
        isLocateInSingle = true;
        mContext = getActivity();
        mSingleData = new ArrayList<>();
        mSuitData = new ArrayList<>();
        mDialog = new QMUITipDialog.Builder(getActivity())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("加载数据中")
                .create();
        mClothes = new ArrayList<>();
        mTrousers = new ArrayList<>();
        mShows = new ArrayList<>();
        mSkirts = new ArrayList<>();
        mDecorators = new ArrayList<>();
        mBags = new ArrayList<>();
        mNormal = new ArrayList<>();
        mFormal = new ArrayList<>();
        mModle = new GetClothesByWordReqModle[8];
        mWord = new String[] {"上衣", "裤子", "裙装", "鞋袜", "配饰", "包包", "休闲", "商务"};
        for (int i = 0; i < 8; i++) {
            GetClothesByWordReqModle modle = new GetClothesByWordReqModle();
            modle.setWord(mWord[i]);
            mModle[i] = modle;
        }
        num = 0;
    }

    @Override
    protected void initView() {
        tlayoutHeader.addTab(tlayoutHeader.newTab().setText("单品"));
        tlayoutHeader.addTab(tlayoutHeader.newTab().setText("搭配"));
        tlayoutHeader.addOnTabSelectedListener(this);

        tlayoutSingleClothes.addTab(tlayoutSingleClothes.newTab().setText("上衣"));
        tlayoutSingleClothes.addTab(tlayoutSingleClothes.newTab().setText("裤子"));
        tlayoutSingleClothes.addTab(tlayoutSingleClothes.newTab().setText("裙装"));
        tlayoutSingleClothes.addTab(tlayoutSingleClothes.newTab().setText("鞋袜"));
        tlayoutSingleClothes.addTab(tlayoutSingleClothes.newTab().setText("配饰"));
        tlayoutSingleClothes.addTab(tlayoutSingleClothes.newTab().setText("包包"));
        tlayoutSingleClothes.addOnTabSelectedListener(this);

        tlayoutCollocation.addTab(tlayoutCollocation.newTab().setText("商务"));
        tlayoutCollocation.addTab(tlayoutCollocation.newTab().setText("休闲"));
        tlayoutCollocation.addOnTabSelectedListener(this);

        mSingleData.addAll(mClothes);
        mSuitData.addAll(mFormal);
        //默认为衣物开始
        mMyAdapter = new MyAdapter(getContext(), mSingleData, R.layout.item_card_view);
        mMySuitAdapter = new MySuitAdapter(getContext(), mSuitData, R.layout.item_card_view);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerviewContent.setLayoutManager(mLayoutManager);
        recyclerviewContent.setAdapter(mMyAdapter);
    }

    @Override
    protected void loadData() {
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    private void initRecyclerView() {
        if (isLocateInSingle) {
            tlayoutSingleClothes.getTabAt(0).select();
            mMyAdapter.clear();
            mMyAdapter.addAll(mClothes);
        } else {
            tlayoutCollocation.getTabAt(0).select();
            mMySuitAdapter.clear();
            mMySuitAdapter.addAll(mFormal);
        }
        mDialog.dismiss();
    }

    //初次进入网络请求加载数据
    public void getData() {
        mDialog.show();
        for (int i = 0; i < 8; i++) {
            if (i < 6) {
                int finalI = i;
                FittingRoomHelper.getClothesByWord(mModle[i], new IDataCallBack.Callback<ApiResponse<GetClothesRspModle>>() {
                    @Override
                    public void onFailedLoaded(String error) {
                        if (++num == 8) {
                            initRecyclerView();
                            num = 0;
                        }
                        LogUtil.e("衣物数据加载失败", error);
                    }

                    @Override
                    public void onDataLoaded(ApiResponse<GetClothesRspModle> mGetClothesRspModleApiResponse) {
                        LogUtil.e("衣物数据", "加载成功");
                        switch (finalI) {
                            case 0:
                                mClothes = mGetClothesRspModleApiResponse.getData().getData();
                                break;
                            case 1:
                                mTrousers = mGetClothesRspModleApiResponse.getData().getData();
                                break;
                            case 2:
                                mSkirts = mGetClothesRspModleApiResponse.getData().getData();
                                break;
                            case 3:
                                mShows = mGetClothesRspModleApiResponse.getData().getData();
                                break;
                            case 4:
                                mDecorators = mGetClothesRspModleApiResponse.getData().getData();
                                break;
                            case 5:
                                mBags = mGetClothesRspModleApiResponse.getData().getData();
                                break;
                        }
                        if (++num == 8) {
                            initRecyclerView();
                            num = 0;
                        }
                    }
                });
            } else {
                int finalI1 = i;
                NetWorkFactory.getApiServiceWithHeaders()
                        .getSuitByWord(mModle[i])
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<ApiResponse<GetSuitByWordRspModle>>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.e("搭配加载", "成功");
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (++num == 8) {
                                    initRecyclerView();
                                    num = 0;

                                }
                                LogUtil.e("搭配加载", "失败" + e.toString());
                            }

                            @Override
                            public void onNext(ApiResponse<GetSuitByWordRspModle> mGetSuitByWordRspModleApiResponse) {
                                if (finalI1 == 6) {
                                    mNormal = mGetSuitByWordRspModleApiResponse.getData().getData();
                                } else if (finalI1 == 7) {
                                    mFormal = mGetSuitByWordRspModleApiResponse.getData().getData();
                                }
                                if (++num == 8) {
                                    initRecyclerView();
                                    num = 0;
                                }
                            }
                        });
            }
        }

    }


    @OnClick(R.id.float_button)
    public void onViewClicked() {
        showListPopup(floatButton, QMUIPopup.DIRECTION_TOP);
    }

    @Override
    public void onTabSelected(TabLayout.Tab mTab) {
        switch (mTab.getText().toString()) {
            case "上衣":
                mMyAdapter.clear();
                mMyAdapter.addAll(mClothes);
                break;
            case "裤子":
                mMyAdapter.clear();
                mMyAdapter.addAll(mTrousers);
                break;
            case "裙装":
                mMyAdapter.clear();
                mMyAdapter.addAll(mSkirts);
                break;
            case "鞋袜":
                mMyAdapter.clear();
                mMyAdapter.addAll(mShows);
                break;
            case "配饰":
                mMyAdapter.clear();
                mMyAdapter.addAll(mDecorators);
                break;
            case "包包":
                mMyAdapter.clear();
                mMyAdapter.addAll(mBags);
                break;
            case "单品":
                isLocateInSingle = true;
                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerviewContent.setLayoutManager(mLayoutManager);
                recyclerviewContent.setAdapter(mMyAdapter);
                tlayoutSingleClothes.setVisibility(View.VISIBLE);
                tlayoutCollocation.setVisibility(View.GONE);
                tlayoutSingleClothes.getTabAt(0).select();
                break;
            case "搭配":
                isLocateInSingle = false;
                StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerviewContent.setLayoutManager(mStaggeredGridLayoutManager);
                recyclerviewContent.setAdapter(mMySuitAdapter);
                tlayoutCollocation.setVisibility(View.VISIBLE);
                tlayoutSingleClothes.setVisibility(View.GONE);
                if (BitmapAdapter.isChangeData) {
                    getData();
                    BitmapAdapter.isChangeData = false;
                } else {
                    mMySuitAdapter.clear();
                    mMySuitAdapter.addAll(mFormal);
                }
                tlayoutCollocation.getTabAt(0).select();
                break;
            case "商务":
                mMySuitAdapter.clear();
                mMySuitAdapter.addAll(mFormal);
                break;
            case "休闲":
                mMySuitAdapter.clear();
                mMySuitAdapter.addAll(mNormal);
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

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends BaseRecyclerViewAdapter<GetClothesRspModle.DataEntity> {
        private int width;
        private int height;
        private ImageLoader mLoader;

        public MyAdapter(Context context, List<GetClothesRspModle.DataEntity> dataList, int itemLayoutId) {
            super(context, dataList, itemLayoutId);
            mLoader = ImageLoader.build(context);
        }

        @Override
        protected void bind(BaseViewHolder holder, GetClothesRspModle.DataEntity mDataEntity) {
            mLoader.bindBitmap(mDataEntity.getPic_url(), holder.findViewById(R.id.img_clthes_clothes), width, height);
            holder.getmItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClassfyClothesActivity.actionStart(mContext, mDataEntity);
                }
            });
        }

        @Override
        public BaseViewHolder onCreateViewHodler(ViewGroup parent, int viewType) {
            View mView = mInflater.inflate(mItemLayoutId, parent, false);
            int parentHight = parent.getHeight();
            ViewGroup.LayoutParams mParams = mView.getLayoutParams();
            mParams.height = parentHight / 5 * 2;
            height = mParams.height;
            width = parent.getWidth();
            return new BaseViewHolder(mContext, mView);
        }
    }

    private class MySuitAdapter extends BaseRecyclerViewAdapter<GetSuitByWordRspModle.DataEntity> {
        private int height;
        private int width;
        public MySuitAdapter(Context context, List<GetSuitByWordRspModle.DataEntity> dataList, int itemLayoutId) {
            super(context, dataList, itemLayoutId);
        }

        @Override
        protected void bind(BaseViewHolder holder, GetSuitByWordRspModle.DataEntity mDataEntity) {
            mLoader.bindBitmap(mDataEntity.getClothes(), holder.findViewById(R.id.img_clthes_clothes), width, height);
            holder.getmItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClassfyCollocationActivity.actionStart(getActivity(), mDataEntity);
                }
            });
        }

        @Override
        public BaseViewHolder onCreateViewHodler(ViewGroup parent, int viewType) {
            View mView = mInflater.inflate(mItemLayoutId, parent, false);
            int parentHight = parent.getHeight();
            ViewGroup.LayoutParams mParams = mView.getLayoutParams();
            mParams.height = parentHight / 5 * 2;
            height = mParams.height;
            width = parent.getWidth();
            return new BaseViewHolder(mContext, mView);

        }
    }

    private void showListPopup(View v, int preferredDirection) {
        String[] array = new String[]{
              "拍照", "从相册中选择"
        };
        ArrayAdapter adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array)));
        QMUIListPopup mListPopup = new QMUIListPopup(mContext, QMUIPopup.DIRECTION_NONE, adapter);
        mListPopup.create(QMUIDisplayHelper.dp2px(mContext, 250), QMUIDisplayHelper.dp2px(mContext, 200),
                (adapterView, view, i, l) -> {
                    if(i == 0) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
                        } else {
                            takePhoto();
                        }
                    } else {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ADD_PIC);
                        } else {
                            getPictures();
                        }
                    }
                    mListPopup.dismiss();
                });

        mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mListPopup.setPreferredDirection(preferredDirection);
        mListPopup.show(v);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ADD_PIC:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPictures();
                } else {
                    ToastUtil.showToast("授权失败");
                }
                break;
            case TAKE_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    ToastUtil.showToast("授权失败");
                }
            default:
                break;
        }
    }

    //从相册中获得多张图片
    public void getPictures() {
        MultiImageSelector.create()
                .showCamera(false)
                .count(1)
                .multi()
                .start(this, ADD_PIC);
    }

    //照相并存储在本地
    public void takePhoto() {
        //启动照相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从相册中添加图片的结果处理
            case ADD_PIC:
                if (data != null) {
                    //获得选择图片地址
                    List<String> picturesUri = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    Uri photoUri = Uri.parse("file://" + picturesUri.get(0));
                    LogUtil.e("uri", photoUri.toString());
                    Bitmap testBitmap = BitmapFactory.decodeFile(picturesUri.get(0));
                    LogUtil.d("压缩前图片大小", testBitmap.getByteCount()/1024/1024 + "M宽度为"+ testBitmap.getWidth() + "高度为"+ testBitmap.getHeight());
                    if (!isLocateInSingle) {
                        //case 1
                        Bitmap mData = getSmallBitmap(picturesUri.get(0));
                        LogUtil.d("压缩后1图片大小", mData.getByteCount()/1024/1024 + "M宽度为"+ mData.getWidth() + "高度为"+ mData.getHeight());
                        BitmapAdapter.bitmapBytes = ImageLoader.decodeBitmapToByteArray(mData);
                        mData.recycle();
                        ClassfyCollocationActivity.actionStart(mContext);
                    } else {
                        LogUtil.e("图片地址", photoUri.toString());
                        //开启抠图
                        CutOut.activity()
                                .bordered()
                                .src(photoUri)
                                .start(getActivity());
                    }
                } else {
                    ToastUtil.showToast("选择图片失败");
                }
                break;
            //通过照相获得图片
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    //获取图片
                    Bundle extras = data.getExtras();
                    Bitmap image = (Bitmap) extras.get("data");
                    LogUtil.d("图片大小前", image.getByteCount()/1024/1024 + "M宽度为"+ image.getWidth() + "高度为"+ image.getHeight());
                    Bitmap newImage = image.copy(Bitmap.Config.ARGB_8888, true);
                    image.recycle();
                    newImage = getSmallBitmapByBitmap(newImage);
                    LogUtil.d("图片大小后", newImage.getByteCount()/1024/1024 + "M宽度为"+ newImage.getWidth() + "高度为"+ newImage.getHeight());
                    if (!isLocateInSingle) {
                        BitmapAdapter.bitmapBytes = ImageLoader.decodeBitmapToByteArray(newImage);
                        newImage.recycle();
                        ClassfyCollocationActivity.actionStart(getActivity());
                    } else {
                        //先保存到本地
                        String pictureUri = ImageLoader.build(getActivity()).saveBitmap(newImage);
                        newImage.recycle();
                        LogUtil.e("图片地址", pictureUri);
                        Uri photoUri = Uri.parse("file://" + pictureUri);
                        CutOut.activity()
                                .bordered()
                                .src(photoUri)
                                .start(getActivity());
                    }
                }
                break;
            case CutOut.CUTOUT_ACTIVITY_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Uri imgeUri = CutOut.getUri(data);
                        LogUtil.e("抠图成功", imgeUri.toString());
                        //开始调颜色
                        Bitmap mBitmap = getSmallBitmap(imgeUri.getPath());
                        LogUtil.d("图片变小后", mBitmap.getByteCount()/1024/1024 + "M宽度为"+ mBitmap.getWidth() + "高度为"+ mBitmap.getHeight());
                        BitmapAdapter.bitmapBytes = ImageLoader.decodeBitmapToByteArray(mBitmap);
                        mBitmap.recycle();
                        AdjustColorActivity.actionStart(getActivity());
                        break;
                    case CutOut.CUTOUT_ACTIVITY_RESULT_ERROR_CODE:
                        Exception mException = CutOut.getError(data);
                        LogUtil.e("抠图失败", mException.toString());
                        ToastUtil.showToast("抠图失败了");
                        break;
                    default:
                        break;
                }
                break;
            case CLICK_SINGLE:
            case CLASSFY_CLOTHES:
            case SAVE_SUIT_BY_TAKE_PHOTO:
            case CLICK_SUIT:
                if (resultCode == getActivity().RESULT_OK) {
                    getData();
                }
                break;
            case ADJUST_COLOR:
                if (resultCode == getActivity().RESULT_OK) {
                    BitmapAdapter.bitmapBytes = data.getByteArrayExtra("bitmap");
                    ClassfyClothesActivity.actionStart(mContext);
                }
            default:
                break;

        }
    }
}
