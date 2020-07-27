package com.neuqer.fitornot.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.neuqer.fitornot.App;
import com.neuqer.fitornot.common.Config;
import com.tencent.connect.UserInfo;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

/**
 * @author DuLong
 * @since 2019/8/4
 * email 798382030@qq.com
 */

public class QQLoginUtil {


    //权限
    private final static String SCOPE = "all";

    /**
     * 获得Tencent实例
     * @return
     */
    public static Tencent getTencent() {
        Tencent mTencent = Tencent.createInstance(Config.QQ_API_ID, App.getInstance());
        return mTencent;
    }

    /**
     * 登陆QQ
     *
     * @param mcontext  调用者Activity中的context
     * @param qqCallBack    QQ回调
     */
    public static void login(Context mcontext, IUiListener qqCallBack){
        Tencent mTencent = getTencent();
        mTencent.login((Activity) mcontext, SCOPE, qqCallBack);
    }

    /**
     * 注销QQ
     *
     * @param context 调用此函数的Activity对应的context
     */
    public static void logout(Context context) {
        Tencent mTencent = getTencent();
        mTencent.logout(context);
    }

    /**
     * 获得用户信息
     *
     * @param mContext
     * @param mTencent
     * @param callBack  回调函数（处理用户信息）
     */
    public static void getQQUserInfo(Context mContext, Tencent mTencent, IUiListener callBack) {
        UserInfo mUserInfo = new UserInfo(mContext, mTencent.getQQToken());
        mUserInfo.getUserInfo(callBack);
    }

    /**
     * 分享图文消息
     *
     * @param mContext 所在活动
     * @param targetURL 点击后跳转的url
     * @param image_URL 分享的图片url
     * @param mListener 回调
     */
    public static void sharePictureWithMessageToQQ(Context mContext, String targetURL, String image_URL, IUiListener mListener) {
        final Bundle params = new Bundle();
        //分享类型为图文类型
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        //被点击后跳转的URL
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://blog.csdn.net/k571039838k/article/details/82754445");
        //分享的标题
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "搭不搭");
        //分享的消息摘要
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "我搭配了一套衣物，分享给你看看搭不搭");
        //分享的图片URL
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, image_URL);
        //隐藏分享到Qzone的按钮
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        //手Q客户端顶部，替换”返回“按钮
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "替换返回按钮");
        getTencent().shareToQQ((Activity) mContext, params, mListener);
    }

    /**
     * 分享纯图片
     *
     * @param activity 所在活动
     * @param imageUrl 分享的图片url地址
     * @param listener 回调
     */
    public static void sharePictureToQQ(Activity activity, String imageUrl, IUiListener listener) {
        final Bundle params = new Bundle();
        //分享类型为纯图片
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        //分享图片的url
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageUrl);
        getTencent().shareToQQ(activity, params, listener);
    }





}
