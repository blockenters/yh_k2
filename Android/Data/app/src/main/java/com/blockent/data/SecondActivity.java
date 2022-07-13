package com.blockent.data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView txtEmail;
    TextView txtName;
    EditText editAge;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 넘어오는 데이터가 있으면 처리해 준다.
        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");
        int age = getIntent().getIntExtra("age", 0);
        double hiddenData = getIntent().getDoubleExtra("hidden", 0);

        // 화면과 연결
        txtEmail = findViewById(R.id.txtEmail);
        txtName = findViewById(R.id.txtName);
        editAge = findViewById(R.id.editAge);
        button = findViewById(R.id.button);

        // 데이터를 화면에 셋팅!!!!
        txtEmail.setText(email);
        txtName.setText(name);
        editAge.setText(age+"");


    }
}



