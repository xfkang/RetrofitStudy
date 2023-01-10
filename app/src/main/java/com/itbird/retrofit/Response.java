package com.itbird.retrofit;

/**
 * Created by itbird on 2023/1/10
 */
public class Response<T> {
    T body;

    public T body() {
        return body;
    }
}
