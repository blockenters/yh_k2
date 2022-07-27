package com.blockent.posting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.blockent.posting.adapter.FollowPostingAdapter;
import com.blockent.posting.adapter.MyPostingAdapter;
import com.blockent.posting.api.NetworkClient;
import com.blockent.posting.api.PostingApi;
import com.blockent.posting.config.Config;
import com.blockent.posting.model.PostRes;
import com.blockent.posting.model.Posting;
import com.blockent.posting.model.PostingList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostingListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FollowPostingAdapter adapter;
    ArrayList<Posting> postingList = new ArrayList<>();

    ProgressBar progressBar;

    // 페이징에 필요한 멤버변수
    int count = 0;
    int offset = 0;
    int limit = 8;
    
    // 좋아요 눌렀을때 선택된 포스팅 정보
    private Posting selectedPosting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_list);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PostingListActivity.this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                if(  lastPosition+1  == totalCount  ){

                    if(count == limit){
                        // 네트워크 통해서, 데이터를 더 불러오면 된다.
                        addNetworkData();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetworkData();
    }

    void getNetworkData(){

        postingList.clear();
        count = 0;
        offset = 0;

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(PostingListActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<PostingList> call = api.getFollowPosting("Bearer "+accessToken,
                offset,
                limit);
        call.enqueue(new Callback<PostingList>() {
            @Override
            public void onResponse(Call<PostingList> call, Response<PostingList> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){

                    count = response.body().getCount();

                    postingList.addAll( response.body().getItems() );

                    offset = offset + count;

                    adapter = new FollowPostingAdapter(PostingListActivity.this, postingList);

                    recyclerView.setAdapter(adapter);

                }else{

                }
            }

            @Override
            public void onFailure(Call<PostingList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    void addNetworkData(){

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(PostingListActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<PostingList> call = api.getFollowPosting("Bearer "+accessToken,
                offset,
                limit);
        call.enqueue(new Callback<PostingList>() {
            @Override
            public void onResponse(Call<PostingList> call, Response<PostingList> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){

                    count = response.body().getCount();

                    postingList.addAll( response.body().getItems() );

                    offset = offset + count;

                    adapter.notifyDataSetChanged();

                }else{

                }
            }

            @Override
            public void onFailure(Call<PostingList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void setLike(int index){

        Log.i("aaaaa", "thumb img click, index : "+index);

        selectedPosting = postingList.get(index);

        // 포스팅 아이디가 들어있다.
        int postingId = selectedPosting.getId();

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Retrofit retrofit = NetworkClient.getRetrofitClient(PostingListActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        int isLike = selectedPosting.getIsLike();

        Call<PostRes> call;
        if(isLike == 0){
            // setLike API 를 호출
            call = api.setLike("Bearer "+accessToken, selectedPosting.getId());

        } else {
            // unsetLike API 를 호출
            call = api.unsetLike("Bearer "+accessToken, selectedPosting.getId());
        }

        call.enqueue(new Callback<PostRes>() {
            @Override
            public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                // 서버에 반영이 잘 된것이다.
                // 따라서, 화면에 보여주는 방식은 2가지 방식이 있는데,
                // 기획에 따라서 선택하면 된다.
                if(response.isSuccessful()){
                    // 첫번째 방법은, 전체 데이터를 다시 가져오는 방법!
//                    getNetworkData();
                    // 두번째 방법은, 서버에는 반영이 되어있으니까, 클라이언트쪽만 따로
                    //              바뀐부분을 반영해 주는 방법!

                    if(selectedPosting.getIsLike() == 0 ){
                        selectedPosting.setIsLike(1);
                        selectedPosting.setLikeCnt(selectedPosting.getLikeCnt() + 1);
                    } else {
                        selectedPosting.setIsLike(0);
                        selectedPosting.setLikeCnt(selectedPosting.getLikeCnt() - 1);
                    }
                    adapter.notifyDataSetChanged();

                }else{

                }
            }

            @Override
            public void onFailure(Call<PostRes> call, Throwable t) {

            }
        });

    }

}






