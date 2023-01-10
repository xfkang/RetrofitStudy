package com.itbird.retrofit;


/**
 * Created by itbird on 2023/1/9
 */
public interface Callback<T> {
    void onResponse(Call<T> call, Response<T> response);

    void onFailure(Call<T> call, Throwable t);
}
