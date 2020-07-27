package com.neuqer.fitornot.business.account.model;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.base.mvp.contract.BaseContract;
import com.neuqer.fitornot.business.account.model.request.GetVCodeRequestModel;
import com.neuqer.fitornot.business.account.model.request.RegisterByVCodeRequestModel;
import com.neuqer.fitornot.business.account.model.response.RegisterByVCodeResponseModel;
import com.neuqer.fitornot.common.ApiException;
import com.neuqer.fitornot.network.NetWorkFactory;
import com.neuqer.fitornot.network.response.ApiResponse;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author DuLong
 * @since 2019/7/31
 * email 798382030@qq.com
 */

public class AcountHelper implements BaseContract.Model {

    /**
     * 请求发送验证码
     *
     * @param phoneNumber 电话号码
     */
    public static void sendMessage(String phoneNumber, IDataCallBack.Callback<Integer> mCallback) {
        GetVCodeRequestModel mGetVCodeRequestModel = new GetVCodeRequestModel();
        mGetVCodeRequestModel.setPhone(Long.parseLong(phoneNumber));
        NetWorkFactory.getApiServiceWithHeaders().getVCode(mGetVCodeRequestModel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApiResponse<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if ( e.getMessage()==null)
                            mCallback.onFailedLoaded(e.getMessage());
                        else
                            mCallback.onFailedLoaded(((ApiException)e).getMsg());

                    }

                    @Override
                    public void onNext(ApiResponse<Void> mVoidApiResponse) {
                        //请求成功后
                        mCallback.onDataLoaded(mVoidApiResponse.getErrorCode());
                    }
                });
    }

    /**
     * 认证短信登陆
     *
     * @param mCaptcha 验证码
     * @param mPhoneNumber 电话号码
     * @param mCallback 回调
     */
    public static void rigisterByVCode(String mCaptcha, String mPhoneNumber, IDataCallBack.Callback<ApiResponse<RegisterByVCodeResponseModel>> mCallback) {
        RegisterByVCodeRequestModel model = new RegisterByVCodeRequestModel();
        model.setPhone(Long.parseLong(mPhoneNumber));
        model.setVCode(mCaptcha);
        NetWorkFactory.getApiServiceWithHeaders().rigistorByVCode(model)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ApiResponse<RegisterByVCodeResponseModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if ( e.getMessage()==null)
                            mCallback.onFailedLoaded(e.getMessage());
                        else
                            mCallback.onFailedLoaded(((ApiException)e).getMsg());
                    }

                    @Override
                    public void onNext(ApiResponse<RegisterByVCodeResponseModel> mMessageResponseModelApiResponse) {
                        mCallback.onDataLoaded(mMessageResponseModelApiResponse);
                    }
                });
    }
}
