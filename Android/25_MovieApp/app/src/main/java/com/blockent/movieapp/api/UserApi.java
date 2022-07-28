package com.blockent.movieapp.api;

import com.blockent.movieapp.model.User;
import com.blockent.movieapp.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/users/register")
    Call<UserRes> register(@Body User user);

    @POST("/users/login")
    Call<UserRes> login(@Body User user);

}
