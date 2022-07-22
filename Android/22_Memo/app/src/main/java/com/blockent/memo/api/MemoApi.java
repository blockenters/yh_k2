package com.blockent.memo.api;

import com.blockent.memo.model.Memo;
import com.blockent.memo.model.PostRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MemoApi {

    // 메모 생성하는 API
    @POST("/memo")
    Call<PostRes> addMemo(@Header("Authorization") String token, @Body Memo memo);


}
