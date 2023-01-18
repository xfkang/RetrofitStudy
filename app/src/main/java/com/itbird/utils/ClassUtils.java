package com.itbird.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.itbird.retrofit.Callback;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Objects;

/**
 * Created by itbird on 2023/1/17
 */
public class ClassUtils {
    private static final String TAG = ClassUtils.class.getSimpleName();

    /**
     * 获取所继承类的泛型实际类型
     *
     * @param declaredClass 传入Class实例
     * @return 泛型实际类型
     */
    public static Type getGenericType(Class declaredClass) {
        ParameterizedType parameterizedType = (ParameterizedType) declaredClass.getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return actualTypeArguments[0];
    }

    public static Type getGenericInterfaceType(Class<?> aClass) {
        for (Type genericInterface : aClass.getSuperclass().getGenericInterfaces()) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                    Log.d(TAG, "type = " + actualTypeArgument);
                    return actualTypeArgument;
                }
            }
            Log.d(TAG, "type = " + genericInterface); //type = com.itbird.retrofit.Callback<com.itbird.bean.BaseResult<T>>
        }
        return null;
    }
}
