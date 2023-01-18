package com.itbird.retrofit;

import com.itbird.bean.Pet;

/**
 * Created by itbird on 2023/1/9
 */
public interface Call<T> {
    void enqueue(Callback<T> callback);
}
