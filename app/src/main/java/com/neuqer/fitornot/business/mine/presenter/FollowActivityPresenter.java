package com.neuqer.fitornot.business.mine.presenter;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.model.response.FollowedModel;
import com.neuqer.fitornot.business.mine.model.response.FollowingModel;
import com.neuqer.fitornot.business.mine.view.MyFollowedActivity;
import com.neuqer.fitornot.business.mine.view.MyFollowingActivity;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.ToastUtil;

/**
 * @author DuLong
 * @since 2019/9/2
 * email 798382030@qq.com
 */

public class FollowActivityPresenter extends MineFragmentPresenter {

    public FollowActivityPresenter(MineFragmentContract.View mView) {
        super(mView);
    }

    @Override
    public void getAllFollowed() {
        helper.getAllFollowed(new IDataCallBack.Callback<ApiResponse<FollowedModel>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<FollowedModel> followedModelApiResponse) {
                ((MyFollowedActivity)mView).processData(followedModelApiResponse.getData());
            }
        });
    }

    @Override
    public void getAllFollowing() {
        helper.getAllFollowing(new IDataCallBack.Callback<ApiResponse<FollowingModel>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<FollowingModel> followingModelApiResponse) {
                ((MyFollowingActivity)mView).processData(followingModelApiResponse.getData());
            }
        });
    }
}
