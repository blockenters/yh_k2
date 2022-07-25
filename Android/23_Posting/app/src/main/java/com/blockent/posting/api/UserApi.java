package com.blockent.posting.api;

import com.blockent.posting.model.User;
import com.blockent.posting.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {

    // 회원가입 API
    @POST("/users/register")
    Call<UserRes> register(@Body User user);

    // 로그인 API
    @POST("/users/login")
    Call<UserRes> login(@Body User user);

    // 로그아웃 API
    @POST("/users/logout")
    Call<UserRes> logout(@Header("Authorization") String token);

}










