package com.itbird.retrofit;

/**
 * Created by itbird on 2023/1/9
 */
public interface Call<T> {
    void enqueue(Callback<T> callback);
}
