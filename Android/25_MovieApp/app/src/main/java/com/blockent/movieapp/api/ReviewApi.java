package com.blockent.movieapp.api;

import com.blockent.movieapp.model.PostRes;
import com.blockent.movieapp.model.ReviewAddReq;
import com.blockent.movieapp.model.ReviewList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewApi {

    @GET("/movie/{movieId}/rating")
    Call<ReviewList> getReviewList(@Path("movieId") int movieId,
                                   @Query("offset") int offset,
                                   @Query("limit") int limit);

    // 리뷰 남기는 API
    @POST("/rating")
    Call<PostRes> addReview(@Header("Authorization") String token,
                            @Body ReviewAddReq reviewAddReq);
}










