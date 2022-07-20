package com.blockent.youtube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blockent.youtube.adapter.VideoAdapter;
import com.blockent.youtube.config.Config;
import com.blockent.youtube.model.Video;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    ImageView imgSearch;
    ProgressBar progressBar;

    // 리사이클러뷰 관련 멤버변수
    RecyclerView recyclerView;
    // 어댑터, 어레이리스트 필요
    VideoAdapter adapter;
    ArrayList<Video> videoList = new ArrayList<Video>();

    // 페이징에 필요한 변수
    String pageToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = findViewById(R.id.editSearch);
        imgSearch = findViewById(R.id.imgSearch);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        progressBar.setVisibility(View.INVISIBLE);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 검색 버튼 누르면, 네트워크로 데이터 요청해서
                // 정보를 받아온다.

                // 1. 에디트텍스트로부터 검색어를 가져온다.
                String keyword = editSearch.getText().toString().trim();

                // 2. 검색어가 없으면, 검색어를 넣으라고 한다.
                if(keyword.isEmpty()){
                    Toast.makeText(MainActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 3. 프로그레스바를 돌린다.
                progressBar.setVisibility(View.VISIBLE);

                // 4. URL을 조합한다.
                // ?part=snippet&key=[자신의 API KEY]&q=축구&maxResults=20
                String url = Config.BASE_URL + "?part=snippet&key="+
                        Config.GOOGLE_API_KEY + "&q="+keyword+"&maxResults=20";

                // 5. 네트워크 통신한다.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // 데이터를 받아오면, 프로그레스바를 안보이게 한다.
                                progressBar.setVisibility(View.INVISIBLE);

                                try {
                                    pageToken = response.getString("nextPageToken");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // 데이터를 받아오면, 프로그레스바를 안보이게 한다.
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                        }
                );
                queue.add(request);
            }
        });
    }
}




