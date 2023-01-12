package com.itbird.retrofit;

import android.util.Log;

import com.itbird.annotation.GET;
import com.itbird.annotation.Path;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by itbird on 2023/1/9
 */
public class Retrofit {
    private Builder builder;
    OkHttpClient okHttpClient;
    private String TAG = Retrofit.class.getSimpleName();
    private Map<Method, ServiceMethod> cacheMap;

    private Retrofit(Builder builder) {
        this.builder = builder;
        okHttpClient = new OkHttpClient().newBuilder().build();
        cacheMap = new ConcurrentHashMap<>();
    }

    public <T> T create(Class<T> sourceclass) {
        //检验是否是一个接口，检验是否继承自接口
        checkIsForInterface(sourceclass);

        //动态代理创建
        return (T) Proxy.newProxyInstance(sourceclass.getClassLoader(), new Class[]{sourceclass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //如果本身就是object的方法，则直接返回，不用经过动态代理创建
                if (method.getDeclaringClass() instanceof Object) {
                    return method.invoke(proxy, args);
                }

                //这里将相应的信息拼装
                ServiceMethod serviceMethod = loadServiceMethod(method);

                //注解处理完成，这里使用ServiceMethod相关参数注解和方法注解信息，进行请求的url拼接
                String url = builder.url + "pet/" + args[0];
                Log.d(TAG, "url = " + url);

                //新建okhttp requset对象
                Request request = new Request.Builder().url(url).build();

                //然后返回okhttp的call
                return okHttpClient.newCall(request);
            }
        });
    }

    /**
     * 创建ServiceMethod，进行注解处理
     *
     * @param method
     * @return
     */
    private <T> ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod serviceMethod = cacheMap.get(method);
        if (serviceMethod == null) {
            serviceMethod = new ServiceMethod.Builder(this, method).build();
            cacheMap.put(method, serviceMethod);
        }
        return serviceMethod;
    }

    /**
     * class检测，检测是否为一个接口，是否为单一的接口，保证无继承
     *
     * @param sourceclass
     * @param <T>
     */
    private <T> void checkIsForInterface(Class<T> sourceclass) {
        if (!sourceclass.isInterface()) {
            throw new IllegalArgumentException("sourceclass is not a interface");
        }

        if (sourceclass.getInterfaces().length > 1) {
            throw new IllegalArgumentException("sourceclass has too much interfaces");
        }
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
