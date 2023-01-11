package com.itbird.retrofit;

import android.util.Log;

import com.itbird.annotation.GET;
import com.itbird.annotation.Path;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by itbird on 2023/1/9
 */
public class Retrofit {
    private Builder builder;
    OkHttpClient okHttpClient;
    private String relativeUrl;
    private Set<String> set;
    private String TAG = Retrofit.class.getSimpleName();

    private Retrofit(Builder builder) {
        this.builder = builder;
        okHttpClient = new OkHttpClient().newBuilder().build();
        set = new HashSet<>();
    }

    public SearchPat create(Class<SearchPat> searchPatClass) {
        parseAnnotation(searchPatClass);
        return (SearchPat) Proxy.newProxyInstance(searchPatClass.getClassLoader(), new Class[]{searchPatClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //这里将相应的信息拼装
                String url = builder.url + "pet/" + args[0];
                Log.d(TAG, "url = " + url);
                //新建okhttp requset对象
                Request request = new Request.Builder().url(url).build();
                //然后返回okhttp的call
                return okHttpClient.newCall(request);
            }
        });
    }

    private void parseAnnotation(Class<SearchPat> searchPatClass) {
        Method[] methods = searchPatClass.getDeclaredMethods();
        for (Method method : methods) {
            parseMethodAnnotation(method.getDeclaredAnnotations());
            parseParamsAnnotation(method.getParameterAnnotations());
        }
    }

    private void parseParamsAnnotation(Annotation[][] parameterAnnotations) {
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Path annotation = (Path) parameterAnnotations[i][0];
            set.add(annotation.value());
        }
    }

    private void parseMethodAnnotation(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof GET) {
                parseGetAnnotation(annotation);
            }
        }
    }

    private void parseGetAnnotation(Annotation annotation) {
        //   "pet/{petId}"
        String value = ((GET) annotation).value();
        // 我们首先清楚目的，我们这里是想要把{petId}替换为参数path中，具体变量值，所以这里只能将其保持
        relativeUrl = value;
    }

    public static class Builder {
        private String url;
        private ConverterFactory.Factory factory;

        public Builder baseUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder addConverterFactory(ConverterFactory.Factory factory) {
            this.factory = factory;
            return this;
        }

        public Retrofit build() {
            return new Retrofit(this);
        }
    }
}
