package com.neuqer.fitornot.business.fittingroom.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.NoBarPresenterActivity;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.business.clothes.modle.response.GetClothesRspModle;
import com.neuqer.fitornot.business.clothes.view.adapter.BitmapAdapter;
import com.neuqer.fitornot.business.fittingroom.contract.CollocationActivityContract;
import com.neuqer.fitornot.business.fittingroom.presenter.CollocationActivityPresenter;
import com.neuqer.fitornot.business.fittingroom.view.adapter.FittingRoomClothesAdapter;
import com.neuqer.fitornot.util.DensityUtil;
import com.neuqer.fitornot.util.Loader.ImageLoader;
import com.neuqer.fitornot.util.Loader.MyUtils;
import com.neuqer.fitornot.util.LogUtil;
import com.neuqer.fitornot.util.StatusBarUtils;
import com.neuqer.fitornot.util.ToastUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.yinglan.scrolllayout.ScrollLayout;
import com.yinglan.scrolllayout.content.ContentRecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzw.doodle.DoodleBitmap;
import cn.hzw.doodle.DoodleOnTouchGestureListener;
import cn.hzw.doodle.DoodleTouchDetector;
import cn.hzw.doodle.DoodleView;
import cn.hzw.doodle.IDoodleListener;
import cn.hzw.doodle.core.IDoodle;
import cn.hzw.doodle.core.IDoodleSelectableItem;
import cn.hzw.doodle.core.IDoodleTouchDetector;

public class CollocationActivity extends NoBarPresenterActivity<CollocationActivityContract.Presenter> implements
        CollocationActivityContract.View, View.OnClickListener, TabLayout.OnTabSelectedListener {


    @BindView(R.id.flayout_container)
    FrameLayout flayoutContainer;
    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.img_pull_up)
    ImageView imgPullUp;
    @BindView(R.id.img_yes)
    ImageView imgYes;
    @BindView(R.id.rlayout_header)
    RelativeLayout rlayoutHeader;
    @BindView(R.id.tlayout_clothes_type)
    TabLayout tlayoutClothesType;
    @BindView(R.id.recyclerview_content)
    ContentRecyclerView recyclerviewContent;
    @BindView(R.id.scroll_down_layout)
    ScrollLayout scrollDownLayout;
    @BindView(R.id.rbtn_restart_collocate)
    RadioButton rbtnRestartCollocate;
    @BindView(R.id.rbtn_save_collocate)
    RadioButton rbtnSaveCollocate;
    @BindView(R.id.rbtn_share_to_friend)
    RadioButton rbtnShareToFriend;
    @BindView(R.id.rgroup_operate)
    RadioGroup rgroupOperate;
    @BindView(R.id.img_change_background)
    ImageView imgChangeBackground;
    @BindView(R.id.img_delete_item)
    ImageView imgDeleteItem;
    @BindView(R.id.img_up_item)
    ImageView imgUpItem;
    @BindView(R.id.img_down_item)
    ImageView imgDownItem;
    @BindView(R.id.llayout_item_opetator)
    LinearLayout llayoutItemOpetator;
    @BindView(R.id.root)
    RelativeLayout root;
    private ImageView mReturnImg;
    private ImageView mShareToCircle;
    private ImageView mSavePhoto;
    private ImageLoader mLoader;

    private BottomSheetDialog mDialog;

    private DisplayMetrics mMetrics;
    private IDoodle mDoodle;
    private DoodleView mDoodleView;
    private DoodleOnTouchGestureListener mDoodleTouchListener;
    private Bitmap mBitmap;
    private FittingRoomClothesAdapter mAdapter;
    private int mScreenHeight;
    private List<GetClothesRspModle.DataEntity> mClothesData;
    private List<GetClothesRspModle.DataEntity> mTrousersData;
    private List<GetClothesRspModle.DataEntity> mSkirtsData;
    private List<GetClothesRspModle.DataEntity> mShoesData;
    private List<GetClothesRspModle.DataEntity> mDecoratorsData;
    private List<GetClothesRspModle.DataEntity> mBagsData;
    private List<GetClothesRspModle.DataEntity> mData;
    //搭配好的图片
    private Bitmap mCollocation;
    private Bitmap mItemPic;
    private int num;

    //决定保存后要做的事
    private int type = 0;
    //记录已经在画板上的搭配
    private Map<DoodleBitmap, Integer> mClothesReversItem;
    private Map<Integer, DoodleBitmap> mClothesItem;
    public static final int CLOTHES = 0;
    public static final int TROUSERS = 1;
    public static final int SKIRTS = 2;
    public static final int SHOES = 3;
    public static final int DECORATORS = 4;
    public static final int BAGS = 5;
    public static final int CHANGE_BACKGROUND = 9;
    public static final int CREATE_ITEM = 6;
    public static final int SAVE_COLLOCATION = 7;
    public static final int SHARE_TO_CIRCLE = 8;
    public static final int Save_Pic = 10;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CREATE_ITEM:
                    //创建imgeItem到展示处
                    IDoodleSelectableItem mIDoodleItem = new DoodleBitmap(mDoodle, mItemPic, mDoodle.getUnitSize() *
                            80, mDoodleView.getBitmap().getWidth() / 2, mDoodleView.getBitmap().getHeight() / 2);
                    mDoodle.addItem(mIDoodleItem);
                    //记录画板上的衣物
                    mClothesReversItem.put((DoodleBitmap) mIDoodleItem, msg.arg1);
                    mClothesItem.put(msg.arg1, (DoodleBitmap) mIDoodleItem);
                    mDoodleTouchListener.setSelectedItem(mIDoodleItem);
                    mDoodle.refresh();
                    break;

            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //获取背景图片后
            case CHANGE_BACKGROUND:
                if (resultCode == Activity.RESULT_OK) {
                    assert data != null;
                    String mdata = data.getStringExtra("background");
                    if (mdata != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //刷新背景
                                Bitmap mBitmap = mLoader.loadBitmap(mdata, 200, 200);
                                //测试中
                                mDoodleView.mBitmap = Bitmap.createScaledBitmap(mBitmap, mMetrics.widthPixels, mScreenHeight, true);
                                mDoodleView.refreshWithBackground();
                            }
                        }).start();
                    }
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_pull_up:
                if (scrollDownLayout.getCurrentStatus() == ScrollLayout.Status.OPENED) {
                    scrollDownLayout.setToExit();
                } else if (scrollDownLayout.getCurrentStatus() == ScrollLayout.Status.CLOSED) {
                    scrollDownLayout.setToOpen();
                } else {
                    scrollDownLayout.setToOpen();
                }
                break;
            case R.id.img_yes:
                hideAll();
                showOperation();
                break;
            case R.id.img_dialog_share_to_circle:
                type = SHARE_TO_CIRCLE;
                mDoodle.save();
                break;
            case R.id.img_share_cancel:
                mDialog.dismiss();
                showOperation();
                break;
            case R.id.img_dialog_save_photo:
                //存储图片
                type = Save_Pic;
                mDoodleView.save();
                break;
            case R.id.img_change_background:
                startActivityForResult(new Intent(CollocationActivity.this, ChangeBackGroundActivity.class),
                        CHANGE_BACKGROUND);
                break;
            case R.id.img_return:
                finish();
                break;
            case R.id.rbtn_restart_collocate:
                hideOperation();
                break;
            case R.id.rbtn_save_collocate:
                type = SAVE_COLLOCATION;
                mDoodle.save();
                break;
            case R.id.rbtn_share_to_friend:
                mDialog.show();
            default:
                break;
        }
    }

    @Override
    protected CollocationActivityContract.Presenter initPresenter() {
        return new CollocationActivityPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collocation;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initVariable() {
        mLoader = ImageLoader.build(this);
        mData = new ArrayList<>();
        num = 0;
        mClothesItem = new HashMap<>();
        mClothesReversItem = new HashMap<>();
        //设置展示处的高度
        ViewGroup.LayoutParams mParams;
        mMetrics = MyUtils.getScreenMetrics(this);
        //不加statusbar
        mScreenHeight = DensityUtil.getScreenHeight(this);
        mParams = flayoutContainer.getLayoutParams();
        mParams.height = mScreenHeight - DensityUtil.dp2px(this, 36);
        flayoutContainer.setLayoutParams(mParams);

        //创建指定大小的bitmap
        Bitmap mResource = BitmapFactory.decodeResource(getResources(), R.drawable.bg_white);
        mBitmap = Bitmap.createScaledBitmap(mResource, mMetrics.widthPixels, mScreenHeight - DensityUtil.dp2px(this, 36), false);
        //初始化Doodle
        mDoodle = mDoodleView = new DoodleView(this, mBitmap, new IDoodleListener() {
            @Override
            /**
             * 存储图片，当调用save时调用
             */
            public void onSaved(IDoodle doodle, Bitmap doodleBitmap, Runnable callback) {
                mCollocation = doodleBitmap;
                if (type == SAVE_COLLOCATION) {
                    ArrayList<Integer> mIndex = new ArrayList<>();
                    mIndex.addAll(mClothesItem.keySet());
                    BitmapAdapter.bitmapBytes = decodeBitmapToByteArray(mCollocation);
                    SaveCollocationActivity.actionStart(CollocationActivity.this, mIndex, false);
                } else if (type == SHARE_TO_CIRCLE) {
                    mDialog.dismiss();
                    new QMUIDialog.MessageDialogBuilder(CollocationActivity.this)
                            .setTitle("提醒")
                            .setMessage("必须保存搭配后才能分享")
                            .addAction("取消", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .addAction("保存搭配", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                    ArrayList<Integer> mIndex = new ArrayList<>();
                                    mIndex.addAll(mClothesItem.keySet());
                                    BitmapAdapter.bitmapBytes = decodeBitmapToByteArray(mCollocation);
                                    SaveCollocationActivity.actionStart(CollocationActivity.this, mIndex, true);
                                }
                            }).show();
                } else if (type == Save_Pic) {
                    mDialog.dismiss();
                    if (ContextCompat.checkSelfPermission(CollocationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CollocationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Save_Pic);
                    } else {
                        savePic(mCollocation);
                    }
                }
            }
            /**
             * 做涂鸦的提前设置，如设置画笔等
             *
             * @param doodle
             */
            @Override
            public void onReady(IDoodle doodle) {

            }
        });


        //创建Doodle的监听器
        mDoodleTouchListener = new DoodleOnTouchGestureListener(mDoodleView, new DoodleOnTouchGestureListener
                .ISelectionListener() {

            /**
             * 当item被选中，或从选中状态变为未选中时调用
             *
             * @param doodle 关联的画板
             * @param selectableItem
             * @param selected 是否选中，false表示从选中变成不选中
             */
            @Override
            public void onSelectedItem(IDoodle doodle, IDoodleSelectableItem selectableItem, boolean selected) {
                //什么都没选中的时候
                if (mDoodleTouchListener.getSelectedItem() == null) {
                    if (llayoutItemOpetator.getVisibility() == View.VISIBLE) {
                        llayoutItemOpetator.setVisibility(View.GONE);
                    }
                } else {
                    llayoutItemOpetator.setVisibility(View.VISIBLE);
                }
            }

            /**
             * 当创建item时调用
             *
             * @param doodle
             * @param x
             * @param y
             */
            @Override
            public void onCreateSelectableItem(IDoodle doodle, float x, float y) {

            }
        });
        //配置Doodle的属性
        IDoodleTouchDetector mDetector = new DoodleTouchDetector(getApplicationContext(), mDoodleTouchListener);
        mDoodleView.setDefaultTouchDetector(mDetector);
        //设置图片初始大小
        mDoodle.setSize(200 * mDoodle.getUnitSize());
        //设置为编辑模式
        mDoodleView.setEditMode(true);
        //设置允许item超出背景
        mDoodleView.setIsDrawableOutside(true);
        //加入到布局中
        flayoutContainer.addView(mDoodleView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //初始化衣物
        mClothesData = new ArrayList<>();
        mTrousersData = new ArrayList<>();
        mShoesData = new ArrayList<>();
        mSkirtsData = new ArrayList<>();
        mDecoratorsData = new ArrayList<>();
        mBagsData = new ArrayList<>();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            savePic(mCollocation);
        } else {
            ToastUtil.showToast("授权失败");
        }
    }

    private void savePic(Bitmap mBitmap) {
        ImageLoader mLoader = ImageLoader.build(CollocationActivity.this);
        mLoader.saveBmp2Gallery(mCollocation, "搭不搭" + mLoader.generateFileName());
        mDialog.dismiss();
        showOperation();
        ToastUtil.showToastPic(R.drawable.ic_has_saved_to_local, this);
    }

    @Override
    protected void initView() {
        //初始化tablayout
        tlayoutClothesType.addTab(tlayoutClothesType.newTab().setText("上衣"));
        tlayoutClothesType.addTab(tlayoutClothesType.newTab().setText("裤子"));
        tlayoutClothesType.addTab(tlayoutClothesType.newTab().setText("裙装"));
        tlayoutClothesType.addTab(tlayoutClothesType.newTab().setText("鞋袜"));
        tlayoutClothesType.addTab(tlayoutClothesType.newTab().setText("配饰"));
        tlayoutClothesType.addTab(tlayoutClothesType.newTab().setText("包包"));

        //设置监听
        tlayoutClothesType.addOnTabSelectedListener(this);
        //默认首先选中第一个tab
        tlayoutClothesType.getTabAt(0).select();

        //关闭状态时最上方预留高度,滚动到minoffset的高度
        scrollDownLayout.setMinOffset(0);
        //打开状态时内容显示区域的高低
        scrollDownLayout.setMaxOffset(mScreenHeight / 3);
        //退出状态显示的高度
        scrollDownLayout.setExitOffset(DensityUtil.dp2px(this, 36));
        //是否支持下滑退出，支持会有下滑到最底部时的回调
        scrollDownLayout.setIsSupportExit(true);
        //是否支持横向滚动
        scrollDownLayout.setAllowHorizontalScroll(false);
        //初始化布局，退出没有动画
        scrollDownLayout.setToOpen();
        scrollDownLayout.getBackground().setAlpha(0);

        flayoutContainer.setOnClickListener(this);
        imgYes.setOnClickListener(this);
        imgReturn.setOnClickListener(this);
        imgPullUp.setOnClickListener(this);
        rbtnSaveCollocate.setOnClickListener(this);
        rbtnRestartCollocate.setOnClickListener(this);
        rbtnShareToFriend.setOnClickListener(this);
        imgChangeBackground.setOnClickListener(this);

        //初始化Dialog
        View mView = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_share, root, false);
        //使Dialog的高度为其屏幕的三分之一
        ViewGroup.LayoutParams mParams = mView.getLayoutParams();
        mParams.height = mScreenHeight / 3;
        mReturnImg = mView.findViewById(R.id.img_share_cancel);
        mReturnImg.setOnClickListener(this);
        mShareToCircle = mView.findViewById(R.id.img_dialog_share_to_circle);
        mShareToCircle.setOnClickListener(this);
        mSavePhoto = mView.findViewById(R.id.img_dialog_save_photo);
        mSavePhoto.setOnClickListener(this);
        mDialog = new BottomSheetDialog(this);
        mDialog.setContentView(mView);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDialog.create();
        }
    }

    @Override
    protected void loadData() {
        showProgressDialog();
        mPresenter.onReqGetClothesByWord(CLOTHES);
        mPresenter.onReqGetClothesByWord(TROUSERS);
        mPresenter.onReqGetClothesByWord(SKIRTS);
        mPresenter.onReqGetClothesByWord(SHOES);
        mPresenter.onReqGetClothesByWord(DECORATORS);
        mPresenter.onReqGetClothesByWord(BAGS);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置白色状态栏
        StatusBarUtils.setStatusBarMode(this, true, R.color.white);
    }


    @Override
    public void onChangeData(List<GetClothesRspModle.DataEntity> mDataEntities, int Type) {
        switch (Type) {
            case CLOTHES:
                mClothesData = mDataEntities;
                break;
            case TROUSERS:
                mTrousersData = mDataEntities;
                break;
            case SHOES:
                mShoesData = mDataEntities;
                break;
            case SKIRTS:
                mSkirtsData = mDataEntities;
                break;
            case DECORATORS:
                mDecoratorsData = mDataEntities;
                break;
            case BAGS:
                mBagsData = mDataEntities;
                break;
            default:
                break;
        }
        num++;
        if (num == 6) {
            initRecyclerView();
        }
    }
    private void initRecyclerView() {
        mData.addAll(mClothesData);
        mAdapter = new FittingRoomClothesAdapter(this, mData, R.layout.item_recyclerview_fitting_clothes);
        mAdapter.setOnItemClickedListener(new BaseRecyclerViewAdapter.OnItemClicked<GetClothesRspModle.DataEntity>() {
            @Override
            public void onItemClicked(View view, GetClothesRspModle.DataEntity mDataEntity, int position) {
                //画板上不包含这个东西时才添加
                if (!mClothesItem.containsKey(mDataEntity.getId())) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message mMessage = new Message();
                            mMessage.what = CREATE_ITEM;
                            mMessage.arg1 = mDataEntity.getId();
                            mItemPic = ImageLoader.build(CollocationActivity.this).loadBitmap(mDataEntity.getPic_url(),
                                    0, 0);
                            mHandler.sendMessage(mMessage);
                        }
                    }).start();
                }
                //不会触发onCreateItem,但会触发onSelectedItem.
                //创建图片在中央位置

            }
        });
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager
                .VERTICAL);
        recyclerviewContent.setLayoutManager(mLayoutManager);
        recyclerviewContent.setAdapter(mAdapter);
        disMissProgressDialog();
    }

    @Override
    public void dismissDialog() {
        disMissProgressDialog();
    }

    /**
     * 显示操作选项
     */
    private void showOperation() {
        scrollDownLayout.setVisibility(View.GONE);
        rgroupOperate.setVisibility(View.VISIBLE);
        imgChangeBackground.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏操作选项
     */
    private void hideOperation() {
        scrollDownLayout.setToOpen();
        scrollDownLayout.setVisibility(View.VISIBLE);
        rgroupOperate.setVisibility(View.GONE);
        imgChangeBackground.setVisibility(View.GONE);
    }

    /**
     * 隐藏除doodle外的所有东西
     */
    private void hideAll() {
        scrollDownLayout.setVisibility(View.GONE);
        rgroupOperate.setVisibility(View.GONE);
        imgChangeBackground.setVisibility(View.GONE);
        llayoutItemOpetator.setVisibility(View.GONE);
    }

    /**
     * 展示通常的界面
     */
    private void showEditWindow() {
        scrollDownLayout.setVisibility(View.VISIBLE);
        scrollDownLayout.setToOpen();
    }

    /**
     * 将图片转化为字节数组
     *
     * @param mBitmap
     */
    private byte[] decodeBitmapToByteArray(Bitmap mBitmap) {
        ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, mOutputStream);
        return mOutputStream.toByteArray();
    }

    /**
     * 对Tablayout的监听
     *
     * @param mTab
     */
    @Override
    public void onTabSelected(TabLayout.Tab mTab) {
        switch (mTab.getPosition()) {
            case CLOTHES:
                mAdapter.clear();
                mAdapter.addAll(mClothesData);
                break;
            case TROUSERS:
                mAdapter.clear();
                mAdapter.addAll(mTrousersData);
                break;
            case SKIRTS:
                mAdapter.clear();
                mAdapter.addAll(mSkirtsData);
                break;
            case SHOES:
                mAdapter.clear();
                mAdapter.addAll(mShoesData);
                break;
            case DECORATORS:
                mAdapter.clear();
                mAdapter.addAll(mDecoratorsData);
                break;
            case BAGS:
                mAdapter.clear();
                mAdapter.addAll(mBagsData);
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

    @OnClick({R.id.img_delete_item, R.id.img_up_item, R.id.img_down_item})
    public void onViewClicked(View view) {
        DoodleBitmap mItem = (DoodleBitmap) mDoodleTouchListener.getSelectedItem();
        switch (view.getId()) {
            case R.id.img_delete_item:
                //去掉某件衣服
                if (mItem != null) {
                    mClothesItem.remove(mClothesReversItem.get(mItem));
                    mDoodle.removeItem(mItem);
                }
                break;
            case R.id.img_up_item:
                //将衣物上移一层
                if (mItem != null) {
                    mDoodleView.upItem(mItem);
                }
                break;
            case R.id.img_down_item:
                //将衣物下移一层
                if (mItem != null) {
                    mDoodleView.downItem(mItem);
                }
                break;
            default:
                break;
        }
    }
}
