package com.neuqer.fitornot.business.circle.presenter;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.circle.contract.CircleContract;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.circle.view.fragment.FollowCardFragment;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.ToastUtil;

public class FollowingCircleFragmentPresenter extends CircleFragmentPresenter {
    /**
     * P层构造方法;
     * 创建P层时就进行双向绑定
     *
     * @param mView V层的引用
     */
    public FollowingCircleFragmentPresenter(CircleContract.View mView) {
        super(mView);
    }

    @Override
    public void getAllFollowingMoments(int id) {
        helper.getAllFollowingMoments(new IDataCallBack.Callback<ApiResponse<MomentsModel>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<MomentsModel> momentsModelApiResponse) {
                ((FollowCardFragment)mView).processData(momentsModelApiResponse.getData());
            }
        },id);
    }
}
