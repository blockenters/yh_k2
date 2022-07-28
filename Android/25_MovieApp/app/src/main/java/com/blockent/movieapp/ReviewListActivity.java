package com.blockent.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.blockent.movieapp.adapter.ReviewAdapter;
import com.blockent.movieapp.api.NetworkClient;
import com.blockent.movieapp.api.ReviewApi;
import com.blockent.movieapp.model.Movie;
import com.blockent.movieapp.model.Review;
import com.blockent.movieapp.model.ReviewList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewListActivity extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    // adapter , list
    ReviewAdapter adapter;
    ArrayList<Review> reviewList = new ArrayList<>();

    // 페이징에 필요한 멤버변수
    int offset = 0;
    int count = 0;
    int limit = 25;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        
        getSupportActionBar().setTitle( movie.getTitle() + " 리뷰");


        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReviewListActivity.this));

        // 네트워크로 부터 데이터 가져온다.
        getNetworkData();

    }

    private void getNetworkData() {

        reviewList.clear();
        count = 0;
        offset = 0;

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(ReviewListActivity.this);
        ReviewApi api = retrofit.create(ReviewApi.class);

        Call<ReviewList> call = api.getReviewList(movie.getId(), offset, limit);

        call.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){

                    count = response.body().getCount();

                    reviewList.addAll( response.body().getItems() );

                    offset = offset + count;

                    adapter = new ReviewAdapter(ReviewListActivity.this, reviewList);

                    recyclerView.setAdapter(adapter);


                }else{

                }

            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}




