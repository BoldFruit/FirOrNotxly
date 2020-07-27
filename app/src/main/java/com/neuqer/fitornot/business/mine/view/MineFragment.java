package com.neuqer.fitornot.business.mine.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.neuqer.fitornot.App;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.fragment.BasePresenterFragment;
import com.neuqer.fitornot.business.account.view.LoginActivity;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;
import com.neuqer.fitornot.business.mine.presenter.MineFragmentPresenter;
import com.neuqer.fitornot.util.ToastUtil;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author Kingtous
 * @since 2019/9/23
 * email me@kingtous.cn
 */

public class MineFragment extends BasePresenterFragment<MineFragmentContract.Presenter> implements MineFragmentContract.View, View.OnClickListener {
    @BindView(R.id.img_avatar)
    QMUIRadiusImageView imgAvatar;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_follow_number)
    TextView txtFollowNumber;
    @BindView(R.id.txt_follower_number)
    TextView txtFollowerNumber;
    @BindView(R.id.llayout_my_circle_published)
    LinearLayout llayoutMyCirclePublished;
    @BindView(R.id.llayout_my_circle_liked)
    LinearLayout llayoutMyCircleLiked;
    @BindView(R.id.llayout_my_circle_fit_request)
    LinearLayout llayoutMyCircleFitRequest;
    @BindView(R.id.llayout_my_circle_my_request)
    LinearLayout llayoutMyCircleMyRequest;
    @BindView(R.id.llayout_common_edit_profile)
    LinearLayout llayoutCommonEditProfile;
    @BindView(R.id.llayout_common_common_question)
    LinearLayout llayoutCommonCommonQuestion;
    @BindView(R.id.llayout_common_feedback)
    LinearLayout llayoutCommonFeedback;
    @BindView(R.id.rlayout_mine_user)
    RelativeLayout rlayoutMineUser;
    @BindView(R.id.txt_signature)
    TextView txtSignature;
    @BindView(R.id.llayout_follow)
    LinearLayout llayoutFollow;
    @BindView(R.id.llayout_follower)
    LinearLayout llayoutFollower;
    @BindView(R.id.llayout_setting)
    LinearLayout llayoutSetting;
    @BindView(R.id.pull_to_refresh_head)
    QMUIPullRefreshLayout pullToRefreshHead;


    private TextView txtChangeAvatar;
    private TextView txtCancelChange;

    private BottomSheetDialog dialog;
    private Context mContext;
    private Handler mHandler;
    private UserInfoModel tmpModel;

    @Override
    protected MineFragmentContract.Presenter initPresenter() {
        return new MineFragmentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initVariable() {
        mHandler = new Handler();
        rlayoutMineUser.setBackground(getResources().getDrawable(R.drawable.img_default_avatar_background));
        mContext = getActivity();
        View view = View.inflate(mContext, R.layout.view_bottom_sheet_dialog, null);
        txtChangeAvatar = view.findViewById(R.id.txt_change_avatar);
        txtCancelChange = view.findViewById(R.id.txt_cancel_change);
        //初始化底部对话框
        dialog = new BottomSheetDialog(mContext);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.create();
    }

    @Override
    protected void initView() {
        imgAvatar.setOnClickListener(this);
        txtCancelChange.setOnClickListener(this);
        txtChangeAvatar.setOnClickListener(this);
        txtSignature.setOnClickListener(this);
        llayoutMyCirclePublished.setOnClickListener(this);
        llayoutCommonEditProfile.setOnClickListener(this);
        llayoutMyCircleFitRequest.setOnClickListener(this);
        llayoutMyCircleMyRequest.setOnClickListener(this);
        llayoutFollow.setOnClickListener(this);
        llayoutFollower.setOnClickListener(this);
        llayoutMyCircleLiked.setOnClickListener(this);
        llayoutCommonFeedback.setOnClickListener(this);
        llayoutCommonCommonQuestion.setOnClickListener(this);
        llayoutSetting.setOnClickListener(this);
        pullToRefreshHead.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                },1000);
                loadData();
            }
        });
    }


    public void processData(UserInfoModel model) {
        tmpModel = model;
        pullToRefreshHead.finishRefresh();
        txtFollowerNumber.setText(String.valueOf(model.getFollowers()));
        txtFollowNumber.setText(String.valueOf(model.getFollowing()));
        txtName.setText(model.getNickname());
        txtSignature.setText(model.getSignature());
        if (model.getAvatar_url() != null){
            Glide.with(mContext).load(model.getAvatar_url()).into(imgAvatar);
            // 设定头像，有头像使用下面的
            Glide.with(mContext).asBitmap().load(model.getAvatar_url()).transition(BitmapTransitionOptions.withCrossFade()).apply(RequestOptions.bitmapTransform(new BlurTransformation())).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Drawable drawable = new BitmapDrawable(resource);
                    // 图片变暗
                    int brightness = -80; //RGB偏移量，变暗为负数
                    ColorMatrix matrix = new ColorMatrix();
                    matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
                    ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(matrix);
                    drawable.setColorFilter(cmcf); //imageView为显示图片的View。

                    // 设置透明度 200/255
                    drawable.setAlpha(200);
                    rlayoutMineUser.setBackground(drawable);
                }
            });
        }
    }

    @Override
    protected void loadData() {
        mPresenter.getUserInfoInMineFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                dialog.show();
                break;
            case R.id.txt_cancel_change:
                dialog.dismiss();
                break;
            case R.id.txt_change_avatar:
                startActivityForResult(new Intent(mContext, EditProfileActivity.class),EditProfileActivity.SYNC);
                dialog.dismiss();
                break;
            case R.id.llayout_setting:
                QMUIDialog.MessageDialogBuilder builder = new QMUIDialog.MessageDialogBuilder(mContext);
                builder.setMessage("退出登录？");
                builder.addAction(R.color.app_main, "退出", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        App.removeAllActivity();
                        startActivity(new Intent(mContext,LoginActivity.class));
                    }
                });
                builder.addAction(R.color.app_main, "取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.llayout_my_circle_published:
                if (tmpModel != null){
                    Intent intent = new Intent(mContext, MyPublishedActivity.class);
                    intent.putExtra(MyPublishedActivity.IDCODE,tmpModel.getUser_id());
                    startActivity(intent);
                }
                break;
            case R.id.llayout_my_circle_liked:
                if (tmpModel != null){
                    Intent intent = new Intent(mContext, MyLikedActivity.class);
                    intent.putExtra(MyLikedActivity.IDCODE,tmpModel.getUser_id());
                    startActivity(intent);
                }
                break;
            case R.id.llayout_common_edit_profile:
                startActivityForResult(new Intent(mContext, EditProfileActivity.class),EditProfileActivity.SYNC);
                break;
            case R.id.llayout_my_circle_fit_request:
                startActivity(new Intent(mContext, FitRequestActivity.class));
                break;
            case R.id.llayout_my_circle_my_request:
                startActivity(new Intent(mContext, MyRequestActivity.class));
                break;
            case R.id.llayout_follow:
                startActivity(new Intent(mContext, MyFollowingActivity.class));
                break;
            case R.id.llayout_follower:
                startActivity(new Intent(mContext, MyFollowedActivity.class));
                break;
            case R.id.llayout_common_feedback:
                startActivity(new Intent(mContext, FeedbackActivity.class));
                break;
            case R.id.llayout_common_common_question:
                ToastUtil.showToast("敬请期待");
                break;
            default:
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EditProfileActivity.SYNC){
            if (resultCode == EditProfileActivity.SYNC_SUCCESS){
                loadData();
            }
        }
    }
}
