package com.itbird.retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by itbird on 2023/1/9
 */
public interface ConverterFactory<T, F> {
    T convert(F value) throws IOException;

    abstract class Factory {
        ConverterFactory<ResponseBody, ?> responseBodyConverter(
                Type type, Annotation[] annotations, Retrofit retrofit) {
            return null;
        }

        ConverterFactory<?, RequestBody> requestBodyConverter(
                Type type,
                Annotation[] parameterAnnotations,
                Annotation[] methodAnnotations,
                Retrofit retrofit) {
            return null;
        }
    }
}
