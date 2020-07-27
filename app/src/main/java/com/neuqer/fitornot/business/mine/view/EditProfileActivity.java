package com.neuqer.fitornot.business.mine.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.BasePresenterActivity;
import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.model.request.SetUserInfoModel;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;
import com.neuqer.fitornot.business.mine.presenter.EditProfilePresenter;
import com.neuqer.fitornot.business.mine.presenter.MineFragmentPresenter;
import com.neuqer.fitornot.util.LogUtil;
import com.neuqer.fitornot.util.QnHelper.QnHelper;
import com.neuqer.fitornot.util.ToastUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundLinearLayout;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.nereo.multi_image_selector.MultiImageSelector;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author: Kingtous
 * Since: 2019-10-07
 * Email: me@kingtous.cn
 */

public class EditProfileActivity extends BasePresenterActivity<MineFragmentContract.Presenter> implements MineFragmentContract.View, View.OnClickListener,EasyPermissions.PermissionCallbacks {


    @BindView(R.id.edit_edit_nickname)
    EditText editEditNickname;
    @BindView(R.id.edit_edit_sex)
    EditText editEditSex;
    @BindView(R.id.edit_edit_age)
    EditText editEditAge;
    @BindView(R.id.edit_edit_height)
    EditText editEditHeight;
    @BindView(R.id.edit_edit_weight)
    EditText editEditWeight;
    @BindView(R.id.edit_edit_detail)
    EditText editEditDetail;
    @BindView(R.id.llayout_submit)
    QMUIRoundLinearLayout llayoutSubmit;
    @BindView(R.id.img_edit_avatar)
    QMUIRadiusImageView imgEditAvatar;

    QnHelper qnHelper;
    UserInfoModel tmpModel;
    SetUserInfoModel stmpModel;
    // CODE
    public static final int IMG_PICKER_CODE = 100;
    public static final int SYNC = 1;
    public static final String SYNC_STATUS = "SYNC_STATUS";
    public static final int SYNC_SUCCESS = 0;
    public static final int SYNC_FAILED = 2;
    public static final int SYNC_NONE = 3;
    // PERMISSION
    public static final int IMG_PICKER_PERMISSION_CODE = 101;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void initVariable() {
        stmpModel = new SetUserInfoModel();
        stmpModel.setData(new SetUserInfoModel.DataBean());
        initTopBar("编辑资料");
        setResult(SYNC_NONE);
        QMUIStatusBarHelper.translucent(this);
        try {
            qnHelper = new QnHelper();
        } catch (Exception e) {
            ToastUtil.showToast("网络异常");
            finish();
        }
    }

    public void processData(UserInfoModel model){
        tmpModel = model;
        stmpModel = new SetUserInfoModel();
        SetUserInfoModel.DataBean dataBean = new SetUserInfoModel.DataBean();
        dataBean.setAvatar_url(model.getAvatar_url());
        try{ dataBean.setAge(Integer.parseInt(model.getAge())); } catch (NumberFormatException e){};
        try{dataBean.setHeight(Integer.parseInt(model.getHeight()));} catch (NumberFormatException e){};
        dataBean.setNickname(model.getNickname());
        try{dataBean.setWeight(Integer.parseInt(model.getWeight()));}catch (NumberFormatException e){};
        dataBean.setSignature(model.getSignature());
        stmpModel.setData(dataBean);
        if (model.getAvatar_url() != null){
            Glide.with(this).load(model.getAvatar_url()).into(imgEditAvatar);
        }
        editEditAge.setText(model.getAge());
        editEditDetail.setText(model.getSignature());
        editEditHeight.setText(model.getHeight());
        editEditNickname.setText(model.getNickname());
        editEditWeight.setText(model.getWeight());
    }

    @Override
    protected void initView() {
        imgEditAvatar.setOnClickListener(this);
        llayoutSubmit.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        mPresenter.getUserInfo();
    }

    @Override
    protected MineFragmentPresenter initPresenter() {
        return new EditProfilePresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_edit_avatar:
                openAlbum();
                break;
            case R.id.llayout_submit:
                try {
                    stmpModel.getData().setAge(Integer.parseInt(editEditAge.getText().toString()));
                    stmpModel.getData().setSignature(editEditDetail.getText().toString());
                    stmpModel.getData().setWeight(Integer.parseInt(editEditWeight.getText().toString()));
                    stmpModel.getData().setHeight(Integer.parseInt(editEditHeight.getText().toString()));
                    stmpModel.getData().setNickname(editEditNickname.getText().toString());
                    mPresenter.setUserInfo(stmpModel);
                }catch (NumberFormatException e){
                ToastUtil.showToast(getString(R.string.txt_warn_number_format));
            }
                break;
        }
    }

    private void openAlbum(){
        if (checkPermission()){
            //  弃用
//            MultiImageSelector.create()
//                    .showCamera(false)
//                    .count(1)
//                    .multi()
//                    .start(this,IMG_PICKER_CODE);
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent,IMG_PICKER_CODE);
        }
    }

    private boolean checkPermission(){
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this,permissions)) {
            return true;
        }
        else {
            EasyPermissions.requestPermissions(this,getString(R.string.txt_permission_tip_edit_avatar),IMG_PICKER_PERMISSION_CODE,permissions);
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_PICKER_CODE){
            if (data != null && resultCode == ImagePicker.RESULT_CODE_ITEMS){
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                // 取第一个
                qnHelper.upLoadImageWithPath(images.get(0).path, new IDataCallBack.Callback<String>() {
                            @Override
                            public void onFailedLoaded(String error) {
                                ToastUtil.showToast(getString(R.string.txt_avatar_upload_failed));
                            }

                            @Override
                            public void onDataLoaded(String s) {
                                if (stmpModel!=null){
                                    stmpModel.getData().setAvatar_url(s);
                                    Glide.with(EditProfileActivity.this).load(s).into(imgEditAvatar);
                                    ToastUtil.showToast(getString(R.string.txt_upload_success));
                                }
                            }
                });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == IMG_PICKER_PERMISSION_CODE){
            openAlbum();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
