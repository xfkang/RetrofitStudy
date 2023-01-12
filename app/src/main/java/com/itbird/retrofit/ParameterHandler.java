package com.itbird.retrofit;

/**
 * Created by itbird on 2023/1/12
 */
public interface ParameterHandler<T> {

    void apply(ServiceMethod serviceMethod, Object arg);


    class PathParameterHandler<T> implements ParameterHandler<T> {
        /**
         * key就是参数注解的具体值
         */
        String key;

        public PathParameterHandler(String key) {
            this.key = key;
        }

        @Override
        public void apply(ServiceMethod serviceMethod, Object arg) {
            //pet/{petId}  key为petId，所以这里很明显，就是想要使用正则表达式，将相对路径中的{petId}，匹配到petID字段，替换为arg，并且设置会serviceMethod
            serviceMethod.builder.mRelativeUrl.replace("{" + key + "}", String.valueOf(arg));
        }
    }
}
