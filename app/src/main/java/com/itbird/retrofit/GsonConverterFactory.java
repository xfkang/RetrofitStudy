package com.itbird.retrofit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 解析Gson数据转换
 * Created by itbird on 2023/1/9
 */
public class GsonConverterFactory<ResponseBody, T> extends ConverterFactory.Factory<ResponseBody, T> {
    private Gson gson;

    private GsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public static GsonConverterFactory create() {
        return new GsonConverterFactory(new Gson());
    }


    /**
     * 这里是将服务端返回的数据，进行转换，转换为我们期望的数据类型
     *
     * @return
     */
    @Override
    ConverterFactory<okhttp3.ResponseBody, T> responseBodyConverter() {
        return new GsonResponseConvert(gson);
    }
}
