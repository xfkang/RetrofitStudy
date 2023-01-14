package com.itbird.retrofit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by itbird on 2023/1/13
 */
public class RetrofitManager {
    private Map<String, Retrofit> retrofitMap;

    private static class RetrofitManagerHolder {
        private static RetrofitManager mInstance = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return RetrofitManagerHolder.mInstance;
    }


    private RetrofitManager() {
        retrofitMap = new ConcurrentHashMap<>();
    }

    /**
     * 通过map存储每个baseUrl的Retrofit对象
     *
     * @param baseUrl
     * @return
     */
    public Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = retrofitMap.get(baseUrl);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).build();
            retrofitMap.put(baseUrl, retrofit);
        }
        return retrofit;
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
