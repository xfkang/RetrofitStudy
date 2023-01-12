package com.itbird.bean;

/**
 * Created by itbird on 2022/12/20
 */
public class BaseResult<T> {
    /**
     * 状态码
     */
    private long code;
    /**
     * 宠物信息
     */
    private T data;

    public long getCode() {
        return code;
    }

    public void setCode(long value) {
        this.code = value;
    }

    public T getData() {
        return data;
    }

    public void setData(T value) {
        this.data = value;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
