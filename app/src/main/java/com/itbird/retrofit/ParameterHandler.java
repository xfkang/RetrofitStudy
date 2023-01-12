package com.itbird.retrofit;

/**
 * Created by itbird on 2023/1/12
 */
public interface ParameterHandler<T> {
    void apply(T t);


    class PathParameterHandler<T> implements ParameterHandler<T> {
        /**
         * key就是参数注解的具体值
         */
        String key;

        public PathParameterHandler(String key) {
            this.key = key;
        }

        @Override
        public void apply(T t) {

        }
    }
}
