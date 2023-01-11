package com.itbird.retrofit;


import com.itbird.annotation.GET;
import com.itbird.annotation.Path;
import okhttp3.Call;

/**
 * Created by itbird on 2022/12/20
 */
public interface SearchPat {
    @GET("pet/{petId}")
    Call searchPat(@Path("petId") String petId);
}
