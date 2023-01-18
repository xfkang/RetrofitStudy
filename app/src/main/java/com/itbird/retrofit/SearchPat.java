package com.itbird.retrofit;


import com.itbird.annotation.BaseUrl;
import com.itbird.annotation.GET;
import com.itbird.annotation.Path;
import com.itbird.bean.BaseResult;
import com.itbird.bean.Pet;

/**
 * Created by itbird on 2022/12/20
 */
@BaseUrl("http://192.168.30.7:4523/m1/2102862-0-default/")
public interface SearchPat {
    @GET("pet/{petId}")
    Call<BaseResult<Pet>> searchPat(@Path("petId") String petId);
}
