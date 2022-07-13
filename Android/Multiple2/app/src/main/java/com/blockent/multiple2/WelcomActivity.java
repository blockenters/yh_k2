package com.blockent.multiple2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WelcomActivity extends AppCompatActivity {

    TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        // 넘겨받은 데이터 처리
        String email = getIntent().getStringExtra("email");

        // 화면과 연결
        txtWelcome = findViewById(R.id.txtWelcom);

        // 데이터를 화면에 셋팅
        txtWelcome.setText(email + "님 환영합니다.");

    }
}