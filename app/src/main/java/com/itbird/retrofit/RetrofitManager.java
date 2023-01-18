package com.itbird.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.itbird.annotation.BaseUrl;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by itbird on 2023/1/13
 */
public class RetrofitManager {
    private Map<String, Retrofit> retrofitMap;
    private String mDefaultBaseUrl;
    private String TAG = RetrofitManager.class.getSimpleName();

    private static class RetrofitManagerHolder {
        private static RetrofitManager mInstance = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return RetrofitManagerHolder.mInstance;
    }

    private RetrofitManager() {
        retrofitMap = new ConcurrentHashMap<>();
    }

    public void setBaseUrl(String baseUrl) {
        this.mDefaultBaseUrl = baseUrl;
    }

    /**
     * 通过map存储每个baseUrl的Retrofit对象
     *
     * @param source
     * @return
     */
    public Retrofit getRetrofit(Class<?> source) {
        Retrofit retrofit = retrofitMap.get(source);
        if (retrofit == null) {
            //todo 这里通过解析source类上面的特定注解，获取baseurl,当然如果没有的时候，就使用固定的url
            String baseUrl = paraseSourceClassBaseUrlAnnotation(source);
            //如果注解获取到的baseUrl为空，则看是有有基础的baseUrl
            if (TextUtils.isEmpty(baseUrl)) {
                if (TextUtils.isEmpty(mDefaultBaseUrl)) {
                    throw new IllegalArgumentException("You need to set Default BaseUrl");
                }
                baseUrl = mDefaultBaseUrl;
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitMap.put(baseUrl, retrofit);
        }
        return retrofit;
    }

    /**
     * 看这个类，是否有BaseUrl的自定义注解，如有，则创建不同的retrofit实例，如果没有，则使用默认的retrofit
     *
     * @param source
     * @return
     */
    private String paraseSourceClassBaseUrlAnnotation(Class<?> source) {
        //查看类上是否有注解BaseUrl
        Log.d(TAG, " source.isAnnotationPresent(BaseUrl.class) = " +  source.isAnnotationPresent(BaseUrl.class));
        if (source.isAnnotationPresent(BaseUrl.class)) {
            BaseUrl annotation = source.getAnnotation(BaseUrl.class);
            Log.d(TAG, "BaseUrl annotation= " + annotation.value());
            return annotation.value();
        }
        return null;
    }


    /**
     * 移除相应的retrofit对象
     *
     * @param baseUrl
     */
    public void removeRetrofit(String baseUrl) {
        retrofitMap.remove(baseUrl);
    }

}
