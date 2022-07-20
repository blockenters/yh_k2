package com.blockent.glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blockent.glide.adapter.PhotoAdapter;
import com.blockent.glide.model.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PhotoAdapter adapter;
    ArrayList<Photo> photoList = new ArrayList<>();

    ProgressBar progressBar;

    public static final String URL = "https://block1-image-test.s3.ap-northeast-2.amazonaws.com/photos.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

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
                                int albumId = data.getInt("albumId");
                                String title = data.getString("title");
                                String url = data.getString("url");
                                String thumbnailUrl = data.getString("thumbnailUrl");

                                Photo photo = new Photo(id, albumId, title, url, thumbnailUrl);
                                photoList.add(photo);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }

                        adapter = new PhotoAdapter(MainActivity.this, photoList);
                        recyclerView.setAdapter(adapter);

                        progressBar.setVisibility(View.INVISIBLE);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(request);

    }
}




