package com.blockent.parsing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView txtUserId;
    TextView txtId;
    TextView txtTitle;
    TextView txtBody;

    final String URL = "https://jsonplaceholder.typicode.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserId = findViewById(R.id.txtUserId);
        txtId = findViewById(R.id.txtId);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);

        // volley 로 네트워크 통신
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL + "/posts/1",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // 네트워크로 데이터를 받아오면, 처리할 함수.
                        // json 데이터는 response 에 담겨져서 온다.
                        try {
                            int userId = response.getInt("userId");
                            int id = response.getInt("id");
                            String title = response.getString("title");
                            String body = response.getString("body");

                            txtUserId.setText("user id : "+userId);
                            txtId.setText("id : "+id);
                            txtTitle.setText(title);
                            txtBody.setText(body);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "파싱 에러", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 네트워크 통신 중 에러가 발생하면,
                        // 이 함수에서 처리.
                        Toast.makeText(MainActivity.this, "네트워크에러", Toast.LENGTH_SHORT).show();
                    }
                });

        // 아래 코드가 실제로, 네트워크로 호출하라는 코드!
        // 이 코드가 없으면, 네트워크 호출 안합니다.!!!!!
        requestQueue.add(request);

    }
}







