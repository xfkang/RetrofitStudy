package com.itbird.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * Created by itbird on 2023/1/17
 */
public class GsonResponseConvert<ResponseBody, T> implements ConverterFactory<ResponseBody, T> {
    private Gson gson;
    private TypeAdapter<T> adapter;

    public GsonResponseConvert(Gson gson) {
        this.gson = gson;
    }

    @Override
    public T convert(okhttp3.ResponseBody value, Type type) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        TypeAdapter<T> adapter = (TypeAdapter<T>) gson.getAdapter(TypeToken.get(type));
        try {
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } finally {
            value.close();
        }
    }
}
