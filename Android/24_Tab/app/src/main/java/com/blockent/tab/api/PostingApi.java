package com.blockent.tab.api;

import com.blockent.tab.model.PostRes;
import com.blockent.tab.model.PostingList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostingApi {

    // 포스팅 생성 API
    @Multipart
    @POST("/posting")
    Call<PostRes> addPosting(@Header("Authorization") String token,
                             @Part MultipartBody.Part photo,
                             @Part("content")RequestBody content);

    // 내 포스팅 리스트 가져오는 API
    @GET("/posting")
    Call<PostingList> getMyPosting(@Header("Authorization") String token,
                                   @Query("offset") int offset,
                                   @Query("limit") int limit);

    // 친구들 포스팅 가져오는 API
    @GET("/posting/follow")
    Call<PostingList> getFollowPosting(@Header("Authorization") String token,
                                       @Query("offset") int offset,
                                       @Query("limit") int limit);

    // 해당 포스팅에 좋아요 하는 API
    @POST("/like/{postingId}")
    Call<PostRes> setLike(@Header("Authorization") String token,
                          @Path("postingId") int postingId);

    @DELETE("/like/{postingId}")
    Call<PostRes> unsetLike(@Header("Authorization") String token,
                            @Path("postingId") int postingId);

    // 포스팅 삭제 API
    @DELETE("/posting/{postingId}")
    Call<PostRes> deletePosting(@Header("Authorization") String token,
                                @Path("postingId") int postingId);

}





