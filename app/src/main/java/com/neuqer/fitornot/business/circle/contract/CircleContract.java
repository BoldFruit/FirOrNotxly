package com.neuqer.fitornot.business.circle.contract;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.base.mvp.contract.BaseContract;
import com.neuqer.fitornot.business.circle.model.request.CommentRequestModel;
import com.neuqer.fitornot.business.circle.model.response.CommentsModel;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.network.response.ApiResponse;

import java.util.List;

/**
 * Author: Kingtous
 * Since: 2019-09-26
 * Email: me@kingtous.cn
 */
public interface CircleContract {

    interface Presenter extends BaseContract.Presenter {
        void createLike(MomentsModel.DataBean dataBean);
        void deleteLike(MomentsModel.DataBean dataBean);
        void checkIfFollowed(MomentsModel.DataBean dataBean);
        void createFollow(MomentsModel.DataBean dataBean);
        void deleteFollow(MomentsModel.DataBean dataBean);
        void createComment(CommentRequestModel requestModel);

        void getAllFollowingMoments(int id);
    }

    interface View extends BaseContract.View<Presenter> {

    }

    interface Model extends BaseContract.Model {
        void getALLMoments(IDataCallBack.Callback<ApiResponse<MomentsModel>> callBack,int id);
        void createLike(IDataCallBack.Callback<ApiResponse<Void>> callBack,MomentsModel.DataBean dataBean);
        void checkIfFollowed(IDataCallBack.Callback<ApiResponse<Void>> callBack,MomentsModel.DataBean dataBean);
        void createFollow(IDataCallBack.Callback<ApiResponse<Void>> callBack,MomentsModel.DataBean dataBean);
        void deleteFollow(IDataCallBack.Callback<ApiResponse<Void>> callBack,MomentsModel.DataBean dataBean);
        void createComment(IDataCallBack.Callback<ApiResponse<Void>> callBack,CommentRequestModel requestModel);
        void getCommentByMoment(IDataCallBack.Callback<ApiResponse<List<CommentsModel>>> callBack, int id);

        void getAllFollowingMoments(IDataCallBack.Callback<ApiResponse<MomentsModel>> callBack,int id);
    }


}
