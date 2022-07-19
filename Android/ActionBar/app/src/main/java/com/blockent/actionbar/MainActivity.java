package com.blockent.actionbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blockent.actionbar.adapter.PostingAdapter;
import com.blockent.actionbar.model.Posting;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://block1-image-test.s3.ap-northeast-2.amazonaws.com/posting.json";
    RecyclerView recyclerView;
    // adapter , arraylist
    PostingAdapter adapter;
    ArrayList<Posting> postingList = new ArrayList<>();

    FloatingActionButton fab;

    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        String title = result.getData().getStringExtra("title");
                        String body = result.getData().getStringExtra("body");

                        Posting posting = new Posting(1, 1, title, body);

                        postingList.add(0, posting);

                        adapter.notifyDataSetChanged();

                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getSupportActionBar().setTitle(R.string.action_main_title);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 플로팅 액션 버튼 클릭했을때, 하고 싶은 일은 여기에
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
//                startActivity(intent);
                launcher.launch(intent);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataList = response.getJSONArray("data");
                            for(int i = 0; i < dataList.length(); i++){
                                JSONObject data = dataList.getJSONObject(i);
                                int id = data.getInt("id");
                                int userId = data.getInt("userId");
                                String title = data.getString("title");
                                String body = data.getString("body");

//                                Log.i("MyFAB", body);

                                Posting posting = new Posting(id, userId, title, body);
                                postingList.add(posting);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }

                        adapter = new PostingAdapter(MainActivity.this, postingList);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(request);
    }

    // xml로 만든 메뉴를, 액티비티의 화면에 나타나게 해주는 함수!
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.menuAdd){
            Intent intent = new Intent(MainActivity.this,
                    AddActivity.class);

//            startActivity(intent);
            launcher.launch(intent);

        } else if(itemId == R.id.menuAbout){
            // todo : About 클릭했을때 하고 싶은 일 코드작성.
        }

        return super.onOptionsItemSelected(item);
    }
}



