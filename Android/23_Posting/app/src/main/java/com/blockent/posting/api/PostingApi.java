package com.blockent.posting.api;

import com.blockent.posting.model.PostRes;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostingApi {

    @Multipart
    @POST("/posting")
    Call<PostRes> addPosting(@Header("Authorization") String token,
                             @Part MultipartBody.Part photo,
                             @Part("content")RequestBody content);

}
