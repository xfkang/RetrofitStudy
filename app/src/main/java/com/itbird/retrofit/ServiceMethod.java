package com.itbird.retrofit;

import android.util.Log;

import com.itbird.annotation.GET;
import com.itbird.annotation.Path;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by itbird on 2023/1/11
 */
public class ServiceMethod {
    Builder builder;
    private static String TAG = ServiceMethod.class.getSimpleName();

    public ServiceMethod(Builder builder) {
        this.builder = builder;
    }

    public static class Builder {
        Retrofit retrofit;
        Method method;
        ParameterHandler<?>[] parameterHandlers;
        String mRelativeUrl;

        public Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            parameterHandlers = new ParameterHandler[method.getParameterAnnotations().length];
        }

        public ServiceMethod build() {
            parseAnnotation(method);
            return new ServiceMethod(this);
        }

        private void parseAnnotation(Method method) {
            parseMethodAnnotation(method.getDeclaredAnnotations());
            parseParamsAnnotation(method.getParameterAnnotations());
        }

        /**
         * 解析参数注解
         *
         * @param parameterAnnotations
         */
        private void parseParamsAnnotation(Annotation[][] parameterAnnotations) {
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation annotation = parameterAnnotations[i][0];
                Log.d(TAG, annotation.annotationType().getName());
                if (annotation instanceof Path) {
                    //不同的参数注解，选择不同的处理策略
                    parameterHandlers[i] = new ParameterHandler.PathParameterHandler<>(((Path) annotation).value());
                }
            }
        }

        /**
         * 解析方法注解
         *
         * @param annotations
         */
        private void parseMethodAnnotation(Annotation[] annotations) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof GET) {
                    mRelativeUrl = ((GET) annotation).value();
                }
            }
        }
    }
}
