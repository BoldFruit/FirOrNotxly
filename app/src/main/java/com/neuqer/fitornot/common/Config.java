package com.neuqer.fitornot.common;

/**
 * @author DuLong
 * @since 2019/7/30
 * email 798382030@qq.com
 */

public class Config {
    /**
     * 设置BASE_URI
     */
    public static final String BASE_URI = "https://ssl.lyzwhh.top/";
    /**
     * 设置网络请求连接超时时间。单位秒
     */
    public static final int CONNECT_TIME_OUT = 15;
    /**
     * 返回正确码
     */
    public static final int[] NET_CORRECT_CODE = {0};
    /**
     * token
     */
    public static final String TOKEN_KEY = "token";

    public static final String TOKEN_VALUE = "88ead18ce57eda1e109111c38f67a64b";

    /**
     * Header Content-Type ,请求的与实体对应的MIME信息
     */
    public static final String CONTENT_TYPE_KEY = "Content-Type";

    public static final String CONTENT_TYPE_VALUE="application/json";
    /**
     * 短信模板Id，作为短信验证网络请求的参数
     */
    public static final int MESSAGE_ID = 176498;
    /**
     * 短信验证的uri地址
     */
    public static final String MESSAGE_URI = "http://v.juhe.cn/sms/send/";
    /**
     * 短信认证Api
     */
    public static final String API = "db6d04beef7505632d589094118b94d9";
    /**
     * QQ登陆授权的Api Id
     */
    public static final String QQ_API_ID = "1109644341";

    /**
     * 七牛域名
     */
    public static final String DOMAIN_NAME = "pwzj3ism5.bkt.clouddn.com";
    public static final String ACCESSKEY = "6_c8lvi6F1RBTHhrdYqLBmmiezQyXE9djoh12_Go";
    public static final String SECRETKEY = "JX-Pv9IZDdVCS7cao5zC8-luRGgTb4EQnyHbBf4F";
}
