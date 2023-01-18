package com.itbird.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.itbird.bean.BaseResult;
import com.itbird.bean.Tag;
import com.itbird.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by itbird on 2023/1/17
 */
public abstract class RetrofitCallBack<T> implements Callback<BaseResult<T>> {

    private static final String TAG = RetrofitCallBack.class.getSimpleName();

    @Override
    public void onResponse(BaseResult<T> response) {
        /**
         * 判断code
         */
        if (response.getCode() == 0) {
            //返回正确的信息
            //这里有一个关键问题，如果使用gson进行转换，那么需要获取T的类型
            Log.d(TAG, "getClass = " + getClass());
            Log.d(TAG, "actualType = " + ClassUtils.getGenericType(getClass()));
            Log.d(TAG, "response.getData().toString() = " + response.getData().toString());
            Gson gson = new Gson();
            onRResponse(gson.fromJson(gson.toJson(response.getData()), ClassUtils.getGenericType(getClass())));
        } else {
            onRFailure(response.getCode(), response.getData().toString());
        }
    }

    @Override
    public void onFailure(long errorCode, String msg) {
        onRFailure(errorCode, msg);
    }

    public abstract void onRResponse(T response);

    public abstract void onRFailure(long errorCode, String msg);
}
