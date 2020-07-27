package com.neuqer.fitornot.network.converter;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author YangZhaoxin.
 * @since 2019/5/1 21:22.
 * email yangzhaoxin@hrsoft.net.
 */


public class ResponseConverterFactory extends Converter.Factory{

    private final Gson gson;
    private static final String NULL_GSON = "gson == null";

    public ResponseConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public static ResponseConverterFactory create() {
        return create(new Gson());
    }

    public static ResponseConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException(NULL_GSON);
        return new ResponseConverterFactory(gson);
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new GsonResponseBodyConverter<>(gson, type);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[]
            methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }
}
