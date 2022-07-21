package com.blockent.memo.api;

import com.blockent.memo.model.RegisterRes;
import com.blockent.memo.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/users/register")
    Call<RegisterRes> register(@Body User user);

}




