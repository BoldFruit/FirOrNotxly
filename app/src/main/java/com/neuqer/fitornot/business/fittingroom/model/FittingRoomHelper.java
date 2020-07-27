package com.neuqer.fitornot.business.fittingroom.model;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.business.clothes.modle.request.GetClothesByWordReqModle;
import com.neuqer.fitornot.business.clothes.modle.request.SetClothesReqModle;
import com.neuqer.fitornot.business.clothes.modle.response.GetClothesRspModle;
import com.neuqer.fitornot.business.fittingroom.model.response.GetClothesRspModel;
import com.neuqer.fitornot.network.NetWorkFactory;
import com.neuqer.fitornot.network.response.ApiResponse;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author DuLong
 * @since 2019/9/30
 * email 798382030@qq.com
 */

public class FittingRoomHelper {

    /**
     * 存储用户上传的衣物
     *
     * @param mData
     * @param mCallback
     */
    public static void saveClothes(SetClothesReqModle mData, IDataCallBack.Callback<ApiResponse<Void>> mCallback) {
        NetWorkFactory.getApiServiceWithHeaders()
                .setNewClothes(mData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mCallback.onFailedLoaded(e.toString());
                    }

                    @Override
                    public void onNext(ApiResponse<Void> mVoidApiResponse) {
                        mCallback.onDataLoaded(mVoidApiResponse);
                    }
                });
    }

    /**
     * 获得用户衣橱中所有的衣物
     *
     * @param mCallback
     */
    public static void getClothes(final IDataCallBack.Callback<ApiResponse<GetClothesRspModle>> mCallback) {
        NetWorkFactory.getApiService()
                .getAllClothes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<GetClothesRspModle>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ApiResponse<GetClothesRspModle> mGetClothesRspModelApiResponse) {
                        mCallback.onDataLoaded(mGetClothesRspModelApiResponse);
                    }
                });
    }

    /**
     * 通过单词查找某一种类的衣物
     *
     * @param mData
     * @param mCallback
     */
    public static void getClothesByWord(GetClothesByWordReqModle mData, final IDataCallBack.Callback<ApiResponse<GetClothesRspModle>> mCallback) {
        NetWorkFactory.getApiServiceWithHeaders()
                .getClothesByWord(mData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<GetClothesRspModle>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mCallback.onFailedLoaded(e.toString());
                    }

                    @Override
                    public void onNext(ApiResponse<GetClothesRspModle> mGetClothesRspModleApiResponse) {
                        mCallback.onDataLoaded(mGetClothesRspModleApiResponse);
                    }
                });
    }

}
