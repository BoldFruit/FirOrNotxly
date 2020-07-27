package com.neuqer.fitornot.business.circle.presenter;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.base.mvp.presenter.BasePresenter;
import com.neuqer.fitornot.business.circle.contract.CircleContract;
import com.neuqer.fitornot.business.circle.model.CircleHelper;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.circle.view.fragment.ALLCardFragment;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.ToastUtil;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * Author: Kingtous
 * Since: 2019-09-26
 * Email: me@kingtous.cn
 */
public class ALLCircleFragmentPresenter extends CircleFragmentPresenter {

    CircleHelper helper;

    /**
     * P层构造方法;
     * 创建P层时就进行双向绑定
     *
     * @param mView V层的引用
     */
    public ALLCircleFragmentPresenter(CircleContract.View mView) {
        super(mView);
        helper = new CircleHelper();
    }

    public void getALLMoments(int id) {
        helper.getALLMoments(new IDataCallBack.Callback<ApiResponse<MomentsModel>>(){

            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast(error);
            }

            @Override
            public void onDataLoaded(ApiResponse<MomentsModel> momentsModelApiResponse) {
                ((ALLCardFragment)mView).processData(momentsModelApiResponse.getData());
            }
        },id);
    }

    public void getAllFollowingMoments(int id){
        helper.getAllFollowingMoments(new IDataCallBack.Callback<ApiResponse<MomentsModel>>() {
            @Override
            public void onFailedLoaded(String error) {

            }

            @Override
            public void onDataLoaded(ApiResponse<MomentsModel> momentsModelApiResponse) {

            }
        },id);
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
        helper.checkIfFollowed(new IDataCallBack.Callback<ApiResponse<Void>>() {
            @Override
            public void onFailedLoaded(String error) {

            }

            @Override
            public void onDataLoaded(ApiResponse<Void> voidApiResponse) {

            }
        },dataBean);
    }
}
