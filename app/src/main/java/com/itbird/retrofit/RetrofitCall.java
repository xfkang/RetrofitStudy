package com.itbird.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.itbird.bean.BaseResult;
import com.itbird.bean.Pet;
import com.itbird.utils.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

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
                //todo 这里开始使用convertfactory的能力

                //mainactvitiy->callback实现的impl interface

                T response1 = (T) retrofit.getConvertFactory().responseBodyConverter().convert(response.body(), ClassUtils.getGenericInterfaceType(callback.getClass()));
                if (response1 != null) {
                    Log.d(TAG, "response1 = " + response1.toString());
                    callback.onResponse(response1);
                } else {
                    callback.onFailure(-1, "response is null");
                }
            }
        });
    }
}
