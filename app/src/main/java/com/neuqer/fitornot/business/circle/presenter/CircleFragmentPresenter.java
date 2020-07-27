package com.neuqer.fitornot.business.circle.presenter;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.base.mvp.contract.BaseContract;
import com.neuqer.fitornot.base.mvp.presenter.BasePresenter;
import com.neuqer.fitornot.business.circle.contract.CircleContract;
import com.neuqer.fitornot.business.circle.model.CircleHelper;
import com.neuqer.fitornot.business.circle.model.request.CommentRequestModel;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.ToastUtil;

/**
 * Author: Kingtous
 * Since: 2019-09-26
 * Email: me@kingtous.cn
 */
public class CircleFragmentPresenter extends BasePresenter<CircleContract.View> implements CircleContract.Presenter {

    protected CircleHelper helper;

    /**
     * P层构造方法;
     * 创建P层时就进行双向绑定
     *
     * @param mView V层的引用
     */
    public CircleFragmentPresenter(CircleContract.View mView) {
        super(mView);
        helper = new CircleHelper();
    }


    @Override
    public void createLike(MomentsModel.DataBean dataBean) {
        helper.createLike(new IDataCallBack.Callback<ApiResponse<Void>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<Void> voidApiResponse) {
                ToastUtil.showToast("点赞成功");
            }
        },dataBean);
    }

    @Override
    public void deleteLike(MomentsModel.DataBean dataBean) {
        helper.deleteLike(new IDataCallBack.Callback<ApiResponse<Void>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<Void> voidApiResponse) {
                ToastUtil.showToast("取消成功");
            }
        },dataBean);
    }

    @Override
    public void checkIfFollowed(MomentsModel.DataBean dataBean) {
        helper.deleteLike(new IDataCallBack.Callback<ApiResponse<Void>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<Void> voidApiResponse) {

            }
        },dataBean);
    }

    @Override
    public void createFollow(MomentsModel.DataBean dataBean) {
        helper.createFollow(new IDataCallBack.Callback<ApiResponse<Void>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<Void> voidApiResponse) {
                ToastUtil.showToast("关注成功");
            }
        },dataBean);
    }

    @Override
    public void deleteFollow(MomentsModel.DataBean dataBean) {
        helper.deleteFollow(new IDataCallBack.Callback<ApiResponse<Void>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<Void> voidApiResponse) {
                ToastUtil.showToast("取消关注成功");
            }
        },dataBean);
    }

    @Override
    public void createComment(CommentRequestModel requestModel) {
        helper.createComment(new IDataCallBack.Callback<ApiResponse<Void>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<Void> voidApiResponse) {
                ToastUtil.showToast("评论成功");
            }
        },requestModel);
    }


    @Override
    public void getAllFollowingMoments(int id) {
        helper.getAllFollowingMoments(new IDataCallBack.Callback<ApiResponse<MomentsModel>>() {
            @Override
            public void onFailedLoaded(String error) {

            }

            @Override
            public void onDataLoaded(ApiResponse<MomentsModel> momentsModelApiResponse) {

            }
        },id);
    }


}
