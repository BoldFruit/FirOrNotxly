package com.neuqer.fitornot.business.circle.model;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.circle.contract.CircleContract;
import com.neuqer.fitornot.business.circle.model.request.CommentRequestModel;
import com.neuqer.fitornot.business.circle.model.response.CommentsModel;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.common.ApiException;
import com.neuqer.fitornot.network.NetWorkFactory;
import com.neuqer.fitornot.network.ResponseBean.CommentRspModel;
import com.neuqer.fitornot.network.response.ApiResponse;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CircleHelper implements CircleContract.Model {
    @Override
    public void getALLMoments(IDataCallBack.Callback<ApiResponse<MomentsModel>> callBack,int id) {
        NetWorkFactory.getApiService()
                .getMoments(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<MomentsModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage()!=null){
                            callBack.onFailedLoaded(e.getMessage());
                        }
                        else {
                            callBack.onFailedLoaded(((ApiException)e).getMsg());
                        }
                    }

                    @Override
                    public void onNext(ApiResponse<MomentsModel> userInfoModelApiResponse) {
                        callBack.onDataLoaded(userInfoModelApiResponse);
                    }
                });
    }

    @Override
    public void createLike(IDataCallBack.Callback<ApiResponse<Void>> callBack, MomentsModel.DataBean dataBean) {
        NetWorkFactory.getApiService()
                .createLike(dataBean.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage()!=null){
                            callBack.onFailedLoaded(e.getMessage());
                        }
                        else {
                            callBack.onFailedLoaded(((ApiException)e).getMsg());
                        }
                    }

                    @Override
                    public void onNext(ApiResponse<Void> voidApiResponse) {
                        callBack.onDataLoaded(voidApiResponse);
                    }
                });

    }

    @Override
    public void checkIfFollowed(IDataCallBack.Callback<ApiResponse<Void>> callBack, MomentsModel.DataBean dataBean) {
        NetWorkFactory.getApiService()
                .checkIfFollowed(dataBean.getWriter())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage()!=null){
                            callBack.onFailedLoaded(e.getMessage());
                        }
                        else {
                            callBack.onFailedLoaded(((ApiException)e).getMsg());
                        }
                    }

                    @Override
                    public void onNext(ApiResponse<Void> voidApiResponse) {
                        callBack.onDataLoaded(voidApiResponse);
                    }
                });
    }

    @Override
    public void createFollow(IDataCallBack.Callback<ApiResponse<Void>> callBack, MomentsModel.DataBean dataBean) {
        NetWorkFactory.getApiService()
                .createFollow(dataBean.getWriter())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage()!=null){
                            callBack.onFailedLoaded(e.getMessage());
                        }
                        else {
                            callBack.onFailedLoaded(((ApiException)e).getMsg());
                        }
                    }

                    @Override
                    public void onNext(ApiResponse<Void> voidApiResponse) {
                        callBack.onDataLoaded(voidApiResponse);
                    }
                });
    }

    @Override
    public void deleteFollow(IDataCallBack.Callback<ApiResponse<Void>> callBack, MomentsModel.DataBean dataBean) {
        NetWorkFactory.getApiService()
                .deleteFollow(dataBean.getWriter())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage()!=null){
                            callBack.onFailedLoaded(e.getMessage());
                        }
                        else {
                            callBack.onFailedLoaded(((ApiException)e).getMsg());
                        }
                    }

                    @Override
                    public void onNext(ApiResponse<Void> voidApiResponse) {
                        callBack.onDataLoaded(voidApiResponse);
                    }
                });
    }

    public void deleteLike(IDataCallBack.Callback<ApiResponse<Void>> callBack, MomentsModel.DataBean dataBean) {
        NetWorkFactory.getApiService()
                .deleteLike(dataBean.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage()!=null){
                            callBack.onFailedLoaded(e.getMessage());
                        }
                        else {
                            callBack.onFailedLoaded(((ApiException)e).getMsg());
                        }
                    }

                    @Override
                    public void onNext(ApiResponse<Void> voidApiResponse) {
                        callBack.onDataLoaded(voidApiResponse);
                    }
                });
    }

    public void createComment(IDataCallBack.Callback<ApiResponse<Void>> callBack, CommentRequestModel requestModel) {
        NetWorkFactory.getApiService()
                .createComment(requestModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage()!=null){
                            callBack.onFailedLoaded(e.getMessage());
                        }
                        else {
                            callBack.onFailedLoaded(((ApiException)e).getMsg());
                        }
                    }

                    @Override
                    public void onNext(ApiResponse<Void> voidApiResponse) {
                        callBack.onDataLoaded(voidApiResponse);
                    }
                });
    }

    @Override
    public void getCommentByMoment(IDataCallBack.Callback<ApiResponse<List<CommentsModel>>> callBack, int id) {
        NetWorkFactory.getApiService()
                .getCommentByMoment(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<List<CommentsModel>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage()!=null){
                            callBack.onFailedLoaded(e.getMessage());
                        }
                        else {
                            callBack.onFailedLoaded(((ApiException)e).getMsg());
                        }
                    }

                    @Override
                    public void onNext(ApiResponse<List<CommentsModel>> listApiResponse) {
                        callBack.onDataLoaded(listApiResponse);
                    }
                });
    }

    @Override
    public void getAllFollowingMoments(IDataCallBack.Callback<ApiResponse<MomentsModel>> callBack, int id) {
        NetWorkFactory.getApiService()
                .getAllFollowingMoments(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<MomentsModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage()!=null){
                            callBack.onFailedLoaded(e.getMessage());
                        }
                        else {
                            callBack.onFailedLoaded(((ApiException)e).getMsg());
                        }
                    }

                    @Override
                    public void onNext(ApiResponse<MomentsModel> userInfoModelApiResponse) {
                        callBack.onDataLoaded(userInfoModelApiResponse);
                    }
                });
    }


}
