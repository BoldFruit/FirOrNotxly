package com.neuqer.fitornot.business.mine.contract;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.base.mvp.contract.BaseContract;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.mine.model.request.SetUserInfoModel;
import com.neuqer.fitornot.business.mine.model.response.FollowedModel;
import com.neuqer.fitornot.business.mine.model.response.FollowingModel;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;
import com.neuqer.fitornot.network.response.ApiResponse;

/**
 * Author: Kingtous
 * Date: 2019/9/26
 * Email: me@kingtous.cn
 */

public interface MineFragmentContract {

    interface Presenter extends BaseContract.Presenter {
        void getUserInfo();
        void getUserInfoInMineFragment();
        void setUserInfo(SetUserInfoModel setUserInfoModel);
        void getAllFollowed();
        void getAllFollowing();
        void getAllLikedMoments(int id);
        void getOnesMoments(int id,int page);
    }

    interface View extends BaseContract.View<Presenter> {

    }

    interface Model extends BaseContract.Model{
        void getUserInfo(IDataCallBack.Callback<ApiResponse<UserInfoModel>> callBack);
        void setUserInfo(SetUserInfoModel setUserInfoModel, IDataCallBack.Callback<ApiResponse<Void>> callBack);
        void getAllFollowed(IDataCallBack.Callback<ApiResponse<FollowedModel>> callBack);
        void getAllFollowing(IDataCallBack.Callback<ApiResponse<FollowingModel>> callBack);
        void getOnesMoments(IDataCallBack.Callback<ApiResponse<MomentsModel>> callBack, int id,int page);
        void getAllLikedMoments(IDataCallBack.Callback<ApiResponse<MomentsModel>> callBack, int id);
    }


}
