package com.blockent.posting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.blockent.posting.adapter.MyPostingAdapter;
import com.blockent.posting.api.NetworkClient;
import com.blockent.posting.api.PostingApi;
import com.blockent.posting.api.UserApi;
import com.blockent.posting.config.Config;
import com.blockent.posting.model.Posting;
import com.blockent.posting.model.PostingList;
import com.blockent.posting.model.UserRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    RecyclerView recyclerView;
    MyPostingAdapter adapter;
    ArrayList<Posting> postingList = new ArrayList<>();
    ProgressBar progressBar;

    private ProgressDialog dialog;

    // 페이징 처리를 위한 멤버변수
    int count = 0;
    int offset = 0;
    int limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        if(accessToken.isEmpty()){

            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);

            finish();
            return;
        }

        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        progressBar = findViewById(R.id.progressBar);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                if( lastPosition + 1 == totalCount){
                    if(limit == count){
                        // 네트워크 통해서 남아있는 데이터 추가로 가져와라.
                        addNetworkData();
                    }
                }

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addNetworkData() {


        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<PostingList> call = api.getMyPosting("Bearer "+accessToken,
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

    @Override
    protected void onResume() {
        super.onResume();
        getNetworkData();
    }

    // 처음으로 데이터 가져올때
    void getNetworkData(){

        postingList.clear();
        count = 0;
        offset = 0;

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<PostingList> call = api.getMyPosting("Bearer "+accessToken,
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

                    adapter = new MyPostingAdapter(MainActivity.this, postingList);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.menuLogout){
            Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
            UserApi api = retrofit.create(UserApi.class);

            SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
            String accessToken = sp.getString("accessToken", "");

            Call<UserRes> call = api.logout("Bearer "+accessToken);

            showProgress("로그아웃 중...");
            call.enqueue(new Callback<UserRes>() {
                @Override
                public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                    dismissProgress();
                    if(response.isSuccessful()){

                        Log.i("MyPostingApp", response.body().getResult());

                        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("accessToken" , "");
                        editor.apply();

                        finish();

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<UserRes> call, Throwable t) {
                    dismissProgress();
                }
            });


        }
        else if (itemId == R.id.menuPosting){
            Intent intent = new Intent(MainActivity.this, PostingListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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






