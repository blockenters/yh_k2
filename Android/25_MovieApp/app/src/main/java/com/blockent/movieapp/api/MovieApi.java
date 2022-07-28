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

    // 키워드로 영화리스트 가져오는 API
    @GET("/movie/search")
    Call<MovieList> searchMovieList(@Query("keyword") String keyword,
                                    @Query("offset") int offset,
                                    @Query("limit") int limit);

}









