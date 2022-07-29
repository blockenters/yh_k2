package com.blockent.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.blockent.movieapp.api.NetworkClient;
import com.blockent.movieapp.api.ReviewApi;
import com.blockent.movieapp.config.Config;
import com.blockent.movieapp.model.Movie;
import com.blockent.movieapp.model.PostRes;
import com.blockent.movieapp.model.ReviewAddReq;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewAddActivity extends AppCompatActivity {

    private Movie movie;

    RatingBar ratingBar;
    Button btnSave;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_add);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        ratingBar = findViewById(R.id.ratingBar);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rating = ratingBar.getRating();
                int movieId = movie.getId();
                SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                String accessToken = sp.getString("accessToken", "");

                Retrofit retrofit = NetworkClient.getRetrofitClient(ReviewAddActivity.this);
                ReviewApi api = retrofit.create(ReviewApi.class);

                ReviewAddReq reviewAddReq = new ReviewAddReq();
                reviewAddReq.setMovieId(movieId);
                reviewAddReq.setRating(rating);

                Call<PostRes> call = api.addReview("Bearer "+accessToken, reviewAddReq);

                // 리뷰 작성중이라고, 화면에 보여주는게 좋다.
                showProgress("리뷰 작성중...");
                call.enqueue(new Callback<PostRes>() {
                    @Override
                    public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                        dismissProgress();
                        if(response.isSuccessful()){
                            finish();
                        }else{

                            if(response.code() == 401){
                                //회원가입 액티비티 띄운다.
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<PostRes> call, Throwable t) {
                        dismissProgress();
                    }
                });

            }
        });

    }

    void showProgress(String message){
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }

    void dismissProgress(){
        dialog.dismiss();
    }

}