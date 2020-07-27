package com.neuqer.fitornot.business.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.activity.NoBarPresenterActivity;
import com.neuqer.fitornot.business.account.contract.LoginContract;
import com.neuqer.fitornot.business.account.model.response.QQLoginInfoModel;
import com.neuqer.fitornot.business.account.model.response.RegisterByVCodeResponseModel;
import com.neuqer.fitornot.business.account.presenter.LoginActivityPresenter;
import com.neuqer.fitornot.business.main.MainActivity;
import com.neuqer.fitornot.common.Config;
import com.neuqer.fitornot.network.response.ApiResponse;
import com.neuqer.fitornot.util.SharedPreferenceUtil;
import com.neuqer.fitornot.util.StatusBarUtils;
import com.neuqer.fitornot.util.ToastUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import butterknife.BindView;

public class LoginActivity extends NoBarPresenterActivity<LoginContract.Presenter> implements LoginContract.View,View.OnClickListener,TextWatcher {

    @BindView(R.id.edit_phonenumber)
    EditText phoneNumberEdit;
    @BindView(R.id.edit_captcha)
    EditText captchaEdit;
    @BindView(R.id.txt_login)
    TextView loginTxt;
    @BindView(R.id.img_weixin)
    ImageView weixinImg;
    @BindView(R.id.img_qq)
    ImageView qqImg;
    @BindView(R.id.txt_getmessage)
    TextView getMessageTxt;

    //QQ登陆的工具类
    private Tencent mTencent;
    //自定义的登陆之后的回调接口
    private BaseUiListener mIUiListener;
    //用户的信息
    private UserInfo mUserInfo;
    //请求访问QQAPI的全部权限
    private final static String SCOPE = "all";

    /**
     * 更新发送信息的等待时间
     */

      final Handler handler = new Handler(){
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    getMessageTxt.setText(message.arg1 + "s");
                    if(message.arg1 == 0) {
                        getMessageTxt.setEnabled(true);
                        getMessageTxt.setText(R.string.label_captcha);
                    }
            }
            super.handleMessage(message);
        }
    };

    /**
     * 每隔一秒发送msg,从而实现倒计时
     */
    public class MyThread implements Runnable {
        @Override
        public void run() {
            for(int i = 60; i >= 0; i--) {
                Message message = new Message();
                message.what = 1;
                message.arg1 = i;
                handler.sendMessage(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SharedPreferenceUtil.getSP().getBoolean("isLogined", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.gray);


    }

    @Override
    public LoginContract.Presenter initPresenter() {
        return new LoginActivityPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initVariable() {
        getMessageTxt.setOnClickListener(this);
        loginTxt.setOnClickListener(this);
        qqImg.setOnClickListener(this);
        weixinImg.setOnClickListener(this);
        phoneNumberEdit.addTextChangedListener(this);

        //QQ登陆初始化
        mTencent = Tencent.createInstance(Config.QQ_API_ID, this.getApplicationContext());
        mIUiListener = new BaseUiListener();
        /*
        微信登陆初始化
        wxApi = WXAPIFactory.createWXAPI(this, ContentUtils.WEIXIN_APP_ID, true);
        wxApi.registerApp(ContentUtils.WEIXIN_APP_ID);
        */
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //设置当输入为空时，按钮无法点击,且改变透明度
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0) {
            getMessageTxt.setEnabled(false);
            getMessageTxt.setAlpha(0.5f);
        } else {
            getMessageTxt.setEnabled(true);
            getMessageTxt.setAlpha(1f);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //获取验证码
            case R.id.txt_getmessage:
                String phoneNumber = phoneNumberEdit.getText().toString();
                if (phoneNumber.length() != 11) {
                    ToastUtil.showToast("手机号码不正确");
                } else {
                    mPresenter.onSendMessage(phoneNumber);
                }
                break;
            //通过输入验证码登陆
            case R.id.txt_login:
                String inputCaptcha = captchaEdit.getText().toString();
                String inputPhoneNumber = phoneNumberEdit.getText().toString();
                if (!inputCaptcha.isEmpty() && inputPhoneNumber.length() == 11) {
                        mPresenter.onLogin(inputCaptcha, inputPhoneNumber);
                    } else {
                    ToastUtil.showToast("请输入正确的手机号和验证码");
                }
//              startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            //通过QQ登陆
            case R.id.img_qq:
                ToastUtil.showToast("敬请期待");
                break;
            case R.id.img_weixin:
                ToastUtil.showToast("敬请期待");
                break;
            default:
                break;
        }
    }

    /**
     * 创建QQ登陆的回调接口
     */
    private class BaseUiListener implements IUiListener {

        //登陆成功后，object为JSONObject的对象
        @Override
        public void onComplete(Object o) {
            ToastUtil.showToast("登陆成功");
            //获得用户信息
            JSONObject data = (JSONObject) o;
            QQLoginInfoModel qqLoginInfo = new Gson().fromJson(data.toString(), QQLoginInfoModel.class);
            //下次登陆时校监登陆态
            mTencent.setAccessToken(qqLoginInfo.getAccess_token(), qqLoginInfo.getExpires_in());
            mTencent.setOpenId(qqLoginInfo.getOpenid());
            //保存用户登陆状态
            SharedPreferenceUtil.put("token", qqLoginInfo.getAccess_token());
            SharedPreferenceUtil.put("isLogined", true);
        }

        //登录失败后
        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            Log.e("LoginError:", uiError.toString());
        }

        //登陆取消后
        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "登录取消", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * 根据返回码，进行信息发送成功与否的操作
     * @param code
     */
    @Override
    public void onSendMessageSuccess(int code) {
        switch (code) {
            case 0:
                //发送成功，限制一分钟内无法发送信息
                getMessageTxt.setEnabled(false);
                new Thread(new MyThread()).start();
                break;
            default:
               ToastUtil.showToast("获取短信失败");
               break;
        }
    }

    @Override
    public void onRegisterSuccess(ApiResponse<RegisterByVCodeResponseModel> mResponse) {
        int code = mResponse.getErrorCode();
        int userId = mResponse.getData().getUser_Id();
        String token = mResponse.getData().getToken();
        String errorMsg = mResponse.getErrorMsg();
        switch (code) {
            case -1:
                ToastUtil.showToast(errorMsg);
                break;
            case 0:
                ToastUtil.showToast("注册成功");
                startActivity(new Intent(this, MainActivity.class));
                //保存用户的id
                SharedPreferenceUtil.put("uerId", userId);
                //登陆标记，使下一次直接跳过此界面
                SharedPreferenceUtil.put("isLogined", true);
                //保存用户的token
                SharedPreferenceUtil.put("token", token);
                finish();
                break;
            default:
                ToastUtil.showToast("登陆失败");
                break;
        }
    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法(QQapi要求）
     * 不然没有回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
