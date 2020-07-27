package com.neuqer.fitornot.util.QnHelper;

import com.neuqer.fitornot.base.mvp.IDataCallBack;
import com.neuqer.fitornot.common.Config;
import com.neuqer.fitornot.util.LogUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.util.Auth;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author DuLong
 * @since 2019/9/5
 * email 798382030@qq.com
 * Note:在主线程调用
 */

public class QnHelper {
    //七牛域名
    public static final String DOMAIN_NAME = "pyimbkzvk.bkt.clouddn.com";
    //设置好账号的ACCESSKEY和SECRETKEY;
    public static final String ACCESSKEY = "6_c8lvi6F1RBTHhrdYqLBmmiezQyXE9djoh12_Go";
    public static final String SECRETKEY = "JX-Pv9IZDdVCS7cao5zC8-luRGgTb4EQnyHbBf4F";
    //空间名
    public static final String BUCKED_NAME = "fitornot2";
    private boolean isProgressCancel; //网络请求过程中是否取消上传或下载
    private UploadManager uploadManager; //七牛SDK的上传管理者
    private UploadOptions uploadOptions; //七牛SDK的上传选项
    private MyUpCompletionHandler mHandler; //七牛SDK的上传返回监听
    private UpProgressHandler upProgressHandler; //七牛SDK的上传进度监听
    private UpCancellationSignal upCancellationSignal; //七牛SDK的上传过程取消监听
    private final static String TOKEN_URL = "http://xxx.xxx.xxx/x/"; //服务器请求token的网址
    private String uptoken; //服务器请求Token值
    private IDataCallBack.Callback<String> mCallback; //上传数据的回调
    private String upKey;   //文件名

    public QnHelper() throws Exception {
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() throws Exception {
        uploadManager = new UploadManager();
        upProgressHandler = new UpProgressHandler() {
            /**
             * @param mS 上传时的upKey；
             * @param mV 上传进度；
             */
            @Override
            public void progress(String mS, double mV) {
            }
        };
        upCancellationSignal = new UpCancellationSignal() {
            public boolean isCancelled() {
                return isProgressCancel;
            }
        };
        //定义数据或文件上传时的可选项
        uploadOptions = new UploadOptions(
                null,
                "mime_type",    //指定上传文件的MimeType
                true,   //是否启用上传内容crc32校监
                upProgressHandler,  //上传内容进度处理
                upCancellationSignal    //取消上传信号
        );
    }

    /**
     * 通过文件路劲上传图片
     *
     * @param path      路劲
     * @param mCallback 上传成功后的回调
     */
    public void upLoadImageWithPath(final String path, IDataCallBack.Callback<String> mCallback) {
        //根据当前时间生成文件名
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        uptoken = auth.uploadToken(BUCKED_NAME);
        upKey = Config.TOKEN_VALUE + File.separator + generateFileName();
        this.mCallback = mCallback;
        mHandler = new MyUpCompletionHandler();
        uploadManager.put(path, generateFileName(), uptoken, mHandler, uploadOptions);
    }

    /**
     * 通过File对象上传图片
     *
     * @param path      路劲
     * @param mCallback 上传成功后的回调
     */
    public void upLoadImageWithFile(final File path, IDataCallBack.Callback<String> mCallback) {
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        uptoken = auth.uploadToken(BUCKED_NAME);
        upKey = generateFileName();
        this.mCallback = mCallback;
        mHandler = new MyUpCompletionHandler();
        uploadManager.put(path, upKey, uptoken, mHandler, uploadOptions);

    }

    /**
     * 通过文件路劲上传图片
     *
     * @param data      图片的字节数组
     * @param mCallback 上传成功后的回调
     */
    public void upLoadImageWithByteArray(final byte[] data, IDataCallBack.Callback<String> mCallback) {
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        uptoken = auth.uploadToken(BUCKED_NAME);
        upKey = generateFileName();
        this.mCallback = mCallback;
        mHandler = new MyUpCompletionHandler();
        uploadManager.put(data, upKey, uptoken, mHandler, uploadOptions);

    }


    /**
     * 自定义上传完成监听器
     * 实现七牛中UpCompletionHandler接口
     */
    public class MyUpCompletionHandler implements UpCompletionHandler {
        /**
         * @param mS            上传时的upkey
         * @param mResponseInfo Json传中表示的上传信息，包括使用版本，请求状态，请求Id等信息
         * @param mJSONObject   Json串表示的文件信息，包括文件Hash码，文件Mime类型，文件大小等信息
         */
        @Override
        public void complete(String mS, ResponseInfo mResponseInfo, JSONObject mJSONObject) {
            LogUtil.e("qiniu", "上传图片成功");
            if (mCallback != null && mResponseInfo.isOK()) {
                //生成上传图片的url并返回
                String imgUrl = "http://" + DOMAIN_NAME + "/" + mS;
                mCallback.onDataLoaded(imgUrl);
            } else {
                assert mCallback != null;
                mCallback.onFailedLoaded(mResponseInfo.error);
            }
        }
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private String generateFileName() {
        //通过时间为图片命名
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        return filename;
    }


    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        final String MAC_NAME = "HmacSHA1";
        final String ENCODING = "UTF-8";
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);

    }
}
