package com.itbird;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.itbird.retrofit.Call;
import com.itbird.retrofit.Callback;
import com.itbird.retrofit.GsonConverterFactory;
import com.itbird.retrofit.R;
import com.itbird.retrofit.Response;
import com.itbird.retrofit.Retrofit;
import com.itbird.retrofit.SearchPat;

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
                .baseUrl("http://192.168.60.7:4523/m1/2102862-0-default/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //第二步，SearchPat接口类的创建

        //第三步，使用retrofit的create方法，将SearchPat接口实例化为一个对象
        SearchPat service = retrofit.create(SearchPat.class);

        //第四步，滴啊用对象的相关请求方法
        service.searchPat("1").enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, com.itbird.retrofit.Response<Response> response) {
                Log.e(TAG, " service.searchPat Response = " + response.body().toString());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e(TAG, " service.searchPat Response onFailure");
            }
        });
    }
}