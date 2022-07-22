package com.blockent.memo.api;

import com.blockent.memo.model.UserRes;
import com.blockent.memo.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/users/register")
    Call<UserRes> register(@Body User user);

    @POST("/users/login")
    Call<UserRes> login(@Body User user);

}




