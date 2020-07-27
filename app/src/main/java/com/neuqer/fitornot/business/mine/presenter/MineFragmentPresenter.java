package com.neuqer.fitornot.business.mine.presenter;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.base.mvp.presenter.BasePresenter;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.model.MineHelper;
import com.neuqer.fitornot.business.mine.model.request.SetUserInfoModel;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;
import com.neuqer.fitornot.business.mine.view.EditProfileActivity;
import com.neuqer.fitornot.business.mine.view.MineFragment;
import com.neuqer.fitornot.business.mine.view.MyLikedActivity;
import com.neuqer.fitornot.business.mine.view.MyPublishedActivity;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.ToastUtil;

/**
 * @author DuLong
 * @since 2019/9/2
 * email 798382030@qq.com
 */

public class MineFragmentPresenter extends BasePresenter<MineFragmentContract.View> implements MineFragmentContract.Presenter {

    /**
     * P层构造方法;
     * 创建P层时就进行双向绑定
     *
     * @param mView V层的引用
     */

    protected MineHelper helper;

    public MineFragmentPresenter(MineFragmentContract.View mView) {
        super(mView);
        helper=new MineHelper();
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
    }

    @Override
    public void getUserInfoInMineFragment() {
        helper.getUserInfo(new IDataCallBack.Callback<ApiResponse<UserInfoModel>>() {
            @Override
            public void onFailedLoaded(String error) {

            }

            @Override
            public void onDataLoaded(ApiResponse<UserInfoModel> userInfoModelApiResponse) {
                ((MineFragment)mView).processData(userInfoModelApiResponse.getData());
            }
        });
    }

    @Override
    public void setUserInfo(SetUserInfoModel setUserInfoModel) {
        helper.setUserInfo(setUserInfoModel, new IDataCallBack.Callback<ApiResponse<Void>>() {
            @Override
            public void onFailedLoaded(String error) {

            }

            @Override
            public void onDataLoaded(ApiResponse<Void> voidApiResponse) {
                // 修改成功
            }
        });
    }

    @Override
    public void getAllFollowed() {

    }

    @Override
    public void getAllFollowing() {

    }

    @Override
    public void getAllLikedMoments(int id) {
        helper.getAllLikedMoments(new IDataCallBack.Callback<ApiResponse<MomentsModel>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<MomentsModel> momentsModelApiResponse) {
                ((MyLikedActivity)mView).processData(momentsModelApiResponse.getData());
            }
        },id);
    }

    @Override
    public void getOnesMoments(int id,int page) {
        helper.getOnesMoments(new IDataCallBack.Callback<ApiResponse<MomentsModel>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<MomentsModel> momentsModelApiResponse) {
                ((MyPublishedActivity)mView).processData(momentsModelApiResponse.getData());
            }
        },id,page);
    }
}
