package com.neuqer.fitornot.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * @author YangZhaoxin.
 * @since 2019/5/1 21:31.
 * email yangzhaoxin@hrsoft.net.
 */

public class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    /** 设置Content-Type数据格式 */
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    /** 定义 Charset的默认Unicode编码是UTF-8 */
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson mGson;
    private final TypeAdapter<T> mAdapter;

    public GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        mAdapter = adapter;
    }

    /**
     * 将泛型转化成RequestBody
     *
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(),UTF_8);
        JsonWriter jsonWriter = mGson.newJsonWriter(writer);
        mAdapter.write(jsonWriter,value);
        jsonWriter.close();
        return RequestBody.create(MEDIA_TYPE,buffer.readByteString());
    }
}
