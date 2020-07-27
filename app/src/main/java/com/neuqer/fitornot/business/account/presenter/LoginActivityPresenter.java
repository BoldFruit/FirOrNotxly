package com.neuqer.fitornot.business.account.presenter;


import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.base.mvp.presenter.BasePresenter;
import com.neuqer.fitornot.business.account.contract.LoginContract;
import com.neuqer.fitornot.business.account.model.AcountHelper;
import com.neuqer.fitornot.business.account.model.response.RegisterByVCodeResponseModel;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.LogUtil;
import com.neuqer.fitornot.util.ToastUtil;

/**
 * @author DuLong
 * @since 2019/7/31
 * email 798382030@qq.com
 */

public class LoginActivityPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    /**
     * P层构造方法;
     * 创建P层时就进行双向绑定
     *
     * @param mView V层的引用
     */
    public LoginActivityPresenter(LoginContract.View mView) {
        super(mView);
    }

    /**
     *短信登陆验证
     *
     * @param captcha 验证码
     * @param phoneNumber 电话号码
     */
    @Override
    public void onLogin(String captcha, String phoneNumber) {
        AcountHelper.rigisterByVCode(captcha, phoneNumber, new IDataCallBack.Callback<ApiResponse<RegisterByVCodeResponseModel>>() {
            @Override
            public void onFailedLoaded(String error) {
            LogUtil.e("短信登陆请求失败", "");
            ToastUtil.showToast("获取短信失败");
        }

            @Override
            public void onDataLoaded(ApiResponse<RegisterByVCodeResponseModel> mMessageResponseModel) {

                mView.onRegisterSuccess(mMessageResponseModel);
        }
    });
    }

    /**
     * 发送短信
     *
     * @param phoneNumber 输入的电话号码
     */
    @Override
    public void onSendMessage(String phoneNumber) {
        AcountHelper.sendMessage(phoneNumber, new IDataCallBack.Callback<Integer>() {

            @Override
            public void onFailedLoaded(String error) {
                ToastUtil.showToast("获取短信失败");
            }

            @Override
            public void onDataLoaded(Integer code) {
                mView.onSendMessageSuccess(code);
            }
        });
    }

}
