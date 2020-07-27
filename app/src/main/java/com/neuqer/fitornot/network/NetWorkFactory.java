package com.neuqer.fitornot.network;

import android.content.SharedPreferences;

import com.neuqer.fitornot.common.Config;
import com.neuqer.fitornot.network.converter.ResponseConverterFactory;
import com.neuqer.fitornot.util.SharedPreferenceUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


public  final class NetWorkFactory {

    private static OkHttpClient sOkHttpClient;
    private static Retrofit sRetrofit;
    private static ApiService sApiService;
    private static String sToken;

    /**
     * 生成service接口
     *
     * @retrun RetrofitService
     */
    public static ApiService getApiService() {
        sApiService = getsRetrofit().create(ApiService.class);
        return sApiService;
    }

    /**
     * 构造Retrofit，设置相关参数
     *
     * @return Retrofit
     */
    private static Retrofit getsRetrofit() {

            sRetrofit = new Retrofit.Builder()
                    .client(getsOkHttpClient())
                    .baseUrl(Config.BASE_URI)
                    .addConverterFactory(ResponseConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        return sRetrofit;
    }

    public static OkHttpClient getsOkHttpClient() {

            sOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(Config.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addNetworkInterceptor(getNetworkInterceptor())
                    .build();


        return sOkHttpClient;
    }

    /**
     * 统一添加通用header:Token
     * @return
     */
    public static Interceptor getNetworkInterceptor(){
        final SharedPreferences sp = SharedPreferenceUtil.getSP();
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                sToken = SharedPreferenceUtil.getSP().getString("token", Config.TOKEN_VALUE);
                Request request = chain.request().newBuilder()
                        .header(Config.TOKEN_KEY, sToken)
                        .build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * 生成service接口(带有两个Header的情况）
     *
     * @retrun RetrofitService
     */
    public static ApiService getApiServiceWithHeaders(){
        sApiService = getsRetrofitWithHeaders().create(ApiService.class);
        return sApiService;
    }

    /**
     * 构造Retrofit，设置相关参数
     *
     * @return Retrofit
     */
    private static Retrofit getsRetrofitWithHeaders() {

            sRetrofit = new Retrofit.Builder()
                    .client(getsOkHttpClientWithHeaders())
                    .baseUrl(Config.BASE_URI)
                    .addConverterFactory(ResponseConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        return sRetrofit;
    }

    public static OkHttpClient getsOkHttpClientWithHeaders() {

            sOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(Config.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addNetworkInterceptor(getNetworkInterceptorWithHeaders())
                    .build();


        return sOkHttpClient;
    }

    /**
     * 统一添加通用header:Token和Content-Type
     * @return
     */
    public static Interceptor getNetworkInterceptorWithHeaders(){
        final SharedPreferences sp = SharedPreferenceUtil.getSP();
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                sToken = SharedPreferenceUtil.getSP().getString("token", Config.TOKEN_VALUE);
                Request request = chain.request().newBuilder()
                        .addHeader(Config.TOKEN_KEY, sToken)
                        .addHeader(Config.CONTENT_TYPE_KEY, Config.CONTENT_TYPE_VALUE)
                        .build();
                return chain.proceed(request);
            }
        };
    }


}
