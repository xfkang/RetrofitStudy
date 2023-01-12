package com.itbird.retrofit;


/**
 * Created by itbird on 2023/1/9
 */
public interface Callback<T> {
    void onResponse(T response);

    void onFailure(long errorCode, String msg);
}
