package com.itbird;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.itbird.retrofit.GsonConverterFactory;
import com.itbird.retrofit.R;
import com.itbird.retrofit.Retrofit;
import com.itbird.retrofit.SearchPat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testRetrofit();
    }

    private void testRetrofit() {
        //第一步，通过建造者模式，创建了Retrofit对象，对于baseurl、convert等功能进行了封装
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.251.7:4523/m1/2102862-0-default/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //第二步，SearchPat接口类的创建

        //第三步，使用retrofit的create方法，将SearchPat接口实例化为一个对象
        SearchPat service = retrofit.create(SearchPat.class);

        //第四步，滴啊用对象的相关请求方法
        Call call = service.searchPat("1");

        //第五步，借助okhttp的call，发起异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, e.getMessage()) ;
                Log.e(TAG, " service.searchPat Response onFailure ") ;
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                Log.e(TAG, " service.searchPat Response = " + response.body().string());
                Gson gson = new Gson();
                com.itbird.bean.Response response1  = gson.fromJson(response.body().string(), com.itbird.bean.Response.class);
                Log.e(TAG, " service.searchPat Response = " + response1.toString());
            }
        });
    }
}