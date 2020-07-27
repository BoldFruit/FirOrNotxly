package com.neuqer.fitornot.business.circle.presenter;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.circle.contract.CircleContract;
import com.neuqer.fitornot.business.circle.model.request.CommentRequestModel;
import com.neuqer.fitornot.business.circle.model.response.CommentsModel;
import com.neuqer.fitornot.business.circle.view.FitDetailActivity;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.ToastUtil;

import java.util.List;

public class FitDetailActivityPresenter extends CircleFragmentPresenter {
    /**
     * P层构造方法;
     * 创建P层时就进行双向绑定
     *
     * @param mView V层的引用
     */
    public FitDetailActivityPresenter(CircleContract.View mView) {
        super(mView);
    }

    public void getComment(int id){
        helper.getCommentByMoment(new IDataCallBack.Callback<ApiResponse<List<CommentsModel>>>() {
            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast("评论获取失败");
            }

            @Override
            public void onDataLoaded(ApiResponse<List<CommentsModel>> listApiResponse) {
                ((FitDetailActivity)mView).processData(listApiResponse.getData());
            }
        },id);
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
                ((FitDetailActivity)mView).reloadComments();
            }
        },requestModel);
    }
}
