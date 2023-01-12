package com.itbird.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itbird.bean.BaseResult;
import com.itbird.bean.Pet;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by itbird on 2023/1/12
 */
public class RetrofitCall<T> implements Call<T> {
    ServiceMethod serviceMethod;
    Object[] args;
    Retrofit retrofit;
    private String TAG = RetrofitCall.class.getSimpleName();

    public RetrofitCall(Retrofit retrofit, ServiceMethod serviceMethod, Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
        this.retrofit = retrofit;
    }

    @Override
    public void enqueue(Callback<T> callback) {
        // 真正发起请求的地方
        Log.d(TAG, "RetrofitCall enqueue");

        //创建requset
        Request request = serviceMethod.createRequest();
        Log.d(TAG, "url = " + request.url());

        //获取到okhttp的call对象
        okhttp3.Call okhttpCall = retrofit.getClient().newCall(request);

        //发起请求
        okhttpCall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                callback.onFailure(-1, e.getMessage());
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                //使用Gson转换
                Gson gson = new Gson();
                BaseResult<T> response1 = gson.fromJson(response.body().string(), BaseResult.class);
                Log.d(TAG, "response1.getData() = " + response1.getData().toString());
                Log.d(TAG, "response1.getCode() = " + response1.getCode());

                if (response1.getCode() == 0) {
                    //返回正确的信息
                    //这里有一个关键问题，如果使用gson进行转换，那么需要获取T的类型

                    Type[] type = callback.getClass().getGenericInterfaces();
                    Log.d(TAG, "type = " + type[0]); //type = com.itbird.retrofit.Callback<com.itbird.bean.Pet>

                    //将type强转成Parameterized
                    ParameterizedType pt = (ParameterizedType) type[0];
                    Type[] actualTypes = pt.getActualTypeArguments();
                    Log.d(TAG, "getActualTypeArguments = " + actualTypes[0]);//com.itbird.bean.Pet

                    T result = gson.fromJson(new Gson().toJson(response1.getData()), actualTypes[0]);
                    callback.onResponse(result);
                } else {
                    callback.onFailure(response1.getCode(), response1.getData().toString());
                }
            }
        });
    }
}
