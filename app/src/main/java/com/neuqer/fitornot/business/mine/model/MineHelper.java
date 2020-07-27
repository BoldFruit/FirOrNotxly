package com.neuqer.fitornot.business.mine.model;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.circle.model.response.MomentsModel;
import com.neuqer.fitornot.business.mine.contract.MineFragmentContract;
import com.neuqer.fitornot.business.mine.model.request.SetUserInfoModel;
import com.neuqer.fitornot.business.mine.model.response.FollowedModel;
import com.neuqer.fitornot.business.mine.model.response.FollowingModel;
import com.neuqer.fitornot.business.mine.model.response.UserInfoModel;
import com.neuqer.fitornot.common.ApiException;
import com.neuqer.fitornot.network.NetWorkFactory;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.ToastUtil;

import java.nio.channels.NetworkChannel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.NewThreadScheduler;
import rx.schedulers.Schedulers;

/**
 * Author: Kingtous
 * Since: 2019-09-26
 * Email: me@kingtous.cn
 */

public class MineHelper implements MineFragmentContract.Model {

    @Override
    public void getUserInfo(IDataCallBack.Callback<ApiResponse<UserInfoModel>> callBack) {
        NetWorkFactory.getApiService()
                .getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<UserInfoModel>>() {
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
                    public void onNext(ApiResponse<UserInfoModel> userInfoModelApiResponse) {
                        callBack.onDataLoaded(userInfoModelApiResponse);
                    }
                });
    }

    @Override
    public void setUserInfo(SetUserInfoModel setUserInfoModel, IDataCallBack.Callback<ApiResponse<Void>> callBack) {
        NetWorkFactory.getApiServiceWithHeaders()
                .setUserInfo(setUserInfoModel)
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
    public void getAllFollowed(IDataCallBack.Callback<ApiResponse<FollowedModel>> callBack) {
        NetWorkFactory.getApiService()
                .getAllFollowed()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<FollowedModel>>() {

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
                    public void onNext(ApiResponse<FollowedModel> voidApiResponse) {
                        callBack.onDataLoaded(voidApiResponse);
                    }
                });
    }

    @Override
    public void getAllFollowing(IDataCallBack.Callback<ApiResponse<FollowingModel>> callBack) {
        NetWorkFactory.getApiService()
                .getAllFollowing()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<FollowingModel>>() {

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
                    public void onNext(ApiResponse<FollowingModel> voidApiResponse) {
                        callBack.onDataLoaded(voidApiResponse);
                    }
                });
    }

    @Override
    public void getOnesMoments(IDataCallBack.Callback<ApiResponse<MomentsModel>> callBack, int id,int page) {
        NetWorkFactory.getApiService()
                .getOnesMoment(id,page)
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
                    public void onNext(ApiResponse<MomentsModel> momentsModelApiResponse) {
                        callBack.onDataLoaded(momentsModelApiResponse);
                    }
                });
    }

    @Override
    public void getAllLikedMoments(IDataCallBack.Callback<ApiResponse<MomentsModel>> callBack, int id) {
        NetWorkFactory.getApiService()
                .getAllLikedMoments(id)
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
                    public void onNext(ApiResponse<MomentsModel> momentsModelApiResponse) {
                        callBack.onDataLoaded(momentsModelApiResponse);
                    }
                });
    }

}
