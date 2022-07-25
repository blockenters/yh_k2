package com.blockent.memo.api;

import com.blockent.memo.model.PostRes;
import com.blockent.memo.model.UserRes;
import com.blockent.memo.model.User;

import org.intellij.lang.annotations.JdkConstants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {

    // 회원가입
    @POST("/users/register")
    Call<UserRes> register(@Body User user);

    // 로그인
    @POST("/users/login")
    Call<UserRes> login(@Body User user);

    // 로그아웃
    @POST("/users/logout")
    Call<PostRes> logout(@Header("Authorization") String token);

}




