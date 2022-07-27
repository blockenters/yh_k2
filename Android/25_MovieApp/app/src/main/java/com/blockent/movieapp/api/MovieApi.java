package com.blockent.movieapp.api;

import com.blockent.movieapp.model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("/movie")
    Call<MovieList> getMovieList(@Query("offset") int offset,
                                 @Query("limit") int limit,
                                 @Query("order") String order);

}
