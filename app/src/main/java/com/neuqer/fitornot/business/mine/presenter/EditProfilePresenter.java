package com.neuqer.fitornot.business.mine.presenter;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.model.request.SetUserInfoModel;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;
import com.neuqer.fitornot.business.mine.view.EditProfileActivity;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.ToastUtil;

public class EditProfilePresenter extends MineFragmentPresenter {
    public EditProfilePresenter(MineFragmentContract.View mView) {
        super(mView);
    }

    @Override
    public void getUserInfo() {
        helper.getUserInfo(new IDataCallBack.Callback<ApiResponse<UserInfoModel>>() {
            @Override
            public void onFailedLoaded(String error) {

            }

            @Override
            public void onDataLoaded(ApiResponse<UserInfoModel> userInfoModelApiResponse) {
                ((EditProfileActivity)mView).processData(userInfoModelApiResponse.getData());
            }
        });
    };

    @Override
    public void setUserInfo(SetUserInfoModel setUserInfoModel) {
        helper.setUserInfo(setUserInfoModel, new IDataCallBack.Callback<ApiResponse<Void>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast("网络错误");
            }

            @Override
            public void onDataLoaded(ApiResponse<Void> voidApiResponse) {
                ToastUtil.showToast(((EditProfileActivity)mView).getString(R.string.txt_upload_success));
                ((EditProfileActivity)mView).setResult(EditProfileActivity.SYNC_SUCCESS);
                ((EditProfileActivity)mView).finish();
            }
        });
    }
}
