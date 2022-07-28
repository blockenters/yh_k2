package com.blockent.movieapp.api;

import com.blockent.movieapp.model.ReviewList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewApi {

    @GET("/movie/{movieId}/rating")
    Call<ReviewList> getReviewList(@Path("movieId") int movieId,
                                   @Query("offset") int offset,
                                   @Query("limit") int limit);


}










