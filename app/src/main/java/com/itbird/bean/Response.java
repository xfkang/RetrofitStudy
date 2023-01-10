package com.itbird.bean;

/**
 * Created by itbird on 2022/12/20
 */
public class Response {
    /**
     * 状态码
     */
    private long code;
    /**
     * 宠物信息
     */
    private Pet data;

    public long getCode() {
        return code;
    }

    public void setCode(long value) {
        this.code = value;
    }

    public Pet getData() {
        return data;
    }

    public void setData(Pet value) {
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
