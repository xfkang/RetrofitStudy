package com.itbird.retrofit;


import com.itbird.annotation.GET;
import com.itbird.annotation.Path;
import com.itbird.bean.Pet;

/**
 * Created by itbird on 2022/12/20
 */
public interface SearchPat {
    @GET("pet/{petId}")
    Call<Pet> searchPat(@Path("petId") String petId);
}
