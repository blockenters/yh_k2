package com.blockent.lyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
            }
        });

    }
}










