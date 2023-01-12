package com.itbird.retrofit;

import android.util.Log;

import com.itbird.annotation.GET;
import com.itbird.annotation.Path;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Request;

/**
 * Created by itbird on 2023/1/11
 */
public class ServiceMethod {
    Builder builder;
    private static String TAG = ServiceMethod.class.getSimpleName();

    public ServiceMethod(Builder builder) {
        this.builder = builder;
    }


    public Request createRequest() {
        //todo 这里需要将注解的值、args拼接，我们这里仅仅处理 Path和Get特殊的
        //todo 需要把 pet/{petId} 中的{petId} 替换为 args对应的值
        //todo 当然针对于不同的注解添加方式肯定不同，例如query，所以这里才使用策略模式
        //         retrofit.baseUrl() + "pet/" + args[0]
        String baseUrl = builder.retrofit.baseUrl();
        Log.d(TAG, "baseUrl = " + baseUrl); //http://192.168.30.7:4523/m1/2102862-0-default/
        Log.d(TAG, "mRelativeUrl = " + builder.mRelativeUrl); //pet/{petId}

        //处理相对路径
        for (int i = 0; i < builder.parameterHandlers.length; i++) {
            ParameterHandler<?> parameterHandler = builder.parameterHandlers[i];
            Object arg = builder.args[i];
            parameterHandler.apply(this, arg);
        }

        //然后将baseurl + mRelativeUrl即为请求路径
        String httpRealUrl = baseUrl + builder.mRelativeUrl;
        Log.d(TAG, "httpRealUrl = " + httpRealUrl);

        //新建okhttp requset对象
        return new Request.Builder().url(httpRealUrl).build();
    }


    public static class Builder {
        Retrofit retrofit;
        Method method;
        ParameterHandler<?>[] parameterHandlers;
        String mRelativeUrl;
        Object[] args;

        public Builder(Retrofit retrofit, Method method, Object[] args) {
            this.retrofit = retrofit;
            this.method = method;
            this.args = args;
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
