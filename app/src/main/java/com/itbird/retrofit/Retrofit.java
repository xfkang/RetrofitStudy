package com.itbird.retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.OkHttpClient;

/**
 * Created by itbird on 2023/1/9
 */
public class Retrofit {
    private Builder builder;
    OkHttpClient okHttpClient;

    public Retrofit(Builder builder) {
        this.builder = builder;
        okHttpClient = new OkHttpClient().newBuilder().build();
    }

    public SearchPat create(Class<SearchPat> searchPatClass) {
        return (SearchPat) Proxy.newProxyInstance(searchPatClass.getClassLoader(), new Class[]{searchPatClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(proxy, args);
            }
        });
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
