package com.neuqer.fitornot.business.account.contract;

import com.neuqer.fitornot.base.mvp.contract.BaseContract;
import com.neuqer.fitornot.business.account.model.response.RegisterByVCodeResponseModel;
import com.neuqer.fitornot.network.response.ApiResponse;


/**
 * @author DuLong
 * @since 2019/7/31
 * email 798382030@qq.com
 */

public interface LoginContract {

    interface Presenter extends BaseContract.Presenter {
        void onLogin(String captcha, String phoneNumber);
        void onSendMessage(String phoneNumber);
    }

    interface View extends BaseContract.View<Presenter> {
        void onSendMessageSuccess(int code);
        void onRegisterSuccess(ApiResponse<RegisterByVCodeResponseModel> mResponse);

    }
}
