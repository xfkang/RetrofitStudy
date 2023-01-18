package com.itbird.retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by itbird on 2023/1/9
 */
public interface ConverterFactory<ResponseBody, T> {
    T convert(okhttp3.ResponseBody value, Type type) throws IOException;

    abstract class Factory<ResponseBody, T> {
        ConverterFactory<okhttp3.ResponseBody, T> responseBodyConverter() {
            return null;
        }
    }
}
