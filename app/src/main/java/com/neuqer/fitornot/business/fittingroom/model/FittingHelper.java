package com.neuqer.fitornot.business.fittingroom.model;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.base.mvp.contract.BaseContract;
import com.neuqer.fitornot.business.fittingroom.model.request.SetClothesModel;
import com.neuqer.fitornot.common.ApiException;
import com.neuqer.fitornot.network.NetWorkFactory;
import com.neuqer.fitornot.business.fittingroom.model.response.GetClothesRspModel;
import com.neuqer.fitornot.network.response.ApiResponse;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author DuLong
 * @since 2019/8/22
 * email 798382030@qq.com
 */

public class FittingHelper implements BaseContract.Model {

    /**
     * 储存从相册中选取的照片信息
     *
     * @param clothes
     * @param callback 回调
     */
    public static void saveClothes(SetClothesModel clothes, final IDataCallBack.Callback<ApiResponse> callback) {
        NetWorkFactory.getApiServiceWithHeaders()
                .setClothes(clothes)
                .observeOn(AndroidSchedulers.mainThread()) //主线程处理结果
                .subscribeOn(Schedulers.newThread())  //新线程网络请求
                .subscribe(new Subscriber<ApiResponse<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                        if ( e.getMessage()==null)
                            callback.onFailedLoaded(e.getMessage());
                        else
                            callback.onFailedLoaded(((ApiException)e).getMsg());
                    }

                    @Override
                    public void onNext(ApiResponse<Void> voidApiResponse) {
                        //请求成功
                        callback.onDataLoaded(voidApiResponse);
                    }
                });
    }

    public static void getClothes(final IDataCallBack.Callback<GetClothesRspModel> callback) {
        NetWorkFactory.getApiService()
                .getClothes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<GetClothesRspModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if ( e.getMessage()==null)
                            callback.onFailedLoaded(e.getMessage());
                        else
                            callback.onFailedLoaded(((ApiException)e).getMsg());
                    }

                    @Override
                    public void onNext(ApiResponse<GetClothesRspModel> listApiResponse) {
                        callback.onDataLoaded(listApiResponse.getData());
                    }
                });
    }

}
