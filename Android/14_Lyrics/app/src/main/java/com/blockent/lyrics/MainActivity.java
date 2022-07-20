package com.blockent.lyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editName;
    EditText editSong;
    Button btnLyrics;
    TextView txtLyrics;

    final String URL = "https://api.lyrics.ovh/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editSong = findViewById(R.id.editSong);
        btnLyrics = findViewById(R.id.btnLyrics);
        txtLyrics = findViewById(R.id.txtLyrics);

        // 가사가져오기 버튼 누르면
        // 네트워크를 틍해 API 를 호출하고,
        // 호출한 결과를 화면에 표시한다.

        btnLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 버튼을 누르면, 에디트텍스트에서 유저가 입력한 문자열을
                //    가지고 온다.
                String name = editName.getText().toString().trim();
                String song = editSong.getText().toString().trim();

                // 1-1. 이름과 노래는 데이터가 꼭 있어야 한다. 둘중에 하나라도
                // 데이터가 없으면, 유저한테 둘다 필수로 입력하라고 알려준다.
                if(name.isEmpty() || song.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "이름이나 노래제목은 필수로 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 2. 해당 데이터들을 조합하여 API를 호출한다.
                String apiUrl = URL + name + "/" + song;

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                // 네트워크 통신을 위한 Request 를 만들어야 하는데,
                // JSON 으로 통신하기 때문에
                // JsonObjectRequest 클래스를 객체 생성한다.
                // 생성자는 :  http 메소드, API URL, 전달할 데이터, 응답받으면 실행할 코드, 에러를 받으면 실행할 코드
                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                // 3. 서버로부터 응답(Response)를 받아서, 텍스트뷰에 표시한다.

                                // API를 호출한 결과가 여기서 실행된다.
                                // 따라서 우리는 여기에다가, 가사를 화면에 보여주는 코드를 작성.
                                // 즉, txtLyrics 에 가사를 보여준다.

                                Log.i("MyLyrics", response.toString());

                                // JSON 객체에서, 우리가 원하는 정보를 빼내야 된다.

                                try {
                                    // JSONObject 에서, 내가 가져올 데이터가 문자열이면, getString("키")
                                    // 내가 가져올 데이터가 정수이면, getInt("키")
                                    // 내가 가져올 데이터가 float 이면, getFloat("키")
                                    // 내가 가져올 데이터가 double 이면, getDouble("키")
                                    // 내가 가져올 데이터가 리스트이면, getJsonArray("키")
                                    // 내가 가져올 데이터가 딕셔너리형태이면, getJsonObject("키")
                                    // 로 데이터를 가져올수 있다.
                                    String lyrics = response.getString("lyrics");
                                    txtLyrics.setText(lyrics);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),
                                            "네트워크 에러입니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("MyLyrics", error.toString());
                            }
                        });

                // 네트워크를 통해서 데이터를 가져올때
                // 시간이 오래 걸리면,
                // 타임아웃값을 늘려준다.
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonObjectRequest);
            }
        });

    }
}










