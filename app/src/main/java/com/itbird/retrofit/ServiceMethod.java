package com.itbird.retrofit;

import com.itbird.annotation.GET;
import com.itbird.annotation.Path;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by itbird on 2023/1/11
 */
public class ServiceMethod {
    Builder builder;

    public ServiceMethod(Builder builder) {
        this.builder = builder;
    }

    public static class Builder<T> {
        Retrofit retrofit;
        Class<T> sourceClass;

        public Builder(Retrofit retrofit, Class<T> sourceClass) {
            this.retrofit = retrofit;
            this.sourceClass = sourceClass;
        }

        public ServiceMethod build() {
            parseAnnotation(sourceClass);
            return new ServiceMethod(this);
        }

        private <T> void parseAnnotation(Class<T> searchPatClass) {
            Method[] methods = searchPatClass.getDeclaredMethods();
            for (Method method : methods) {
                parseMethodAnnotation(method.getDeclaredAnnotations());
                parseParamsAnnotation(method.getParameterAnnotations());
            }
        }

        private void parseParamsAnnotation(Annotation[][] parameterAnnotations) {
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Path annotation = (Path) parameterAnnotations[i][0];
                retrofit.add(annotation.value());
            }
        }

        private void parseMethodAnnotation(Annotation[] annotations) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof GET) {
                    parseGetAnnotation(annotation);
                }
            }
        }

        private void parseGetAnnotation(Annotation annotation) {
            //   "pet/{petId}"
            String value = ((GET) annotation).value();
            // 我们首先清楚目的，我们这里是想要把{petId}替换为参数path中，具体变量值，所以这里只能将其保持
            retrofit.setRelativeUrl(value);
        }
    }
}
