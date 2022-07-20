package com.blockent.uitest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle;
    Button button;
    EditText editName;
    EditText editPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 화면에서 뷰를 가져온다.
        txtTitle = findViewById(R.id.txtTitle);
        button = findViewById(R.id.button);
        editName = findViewById(R.id.editName);
        editPasswd = findViewById(R.id.editPasswd);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setBackgroundColor(Color.parseColor("#FF0000"));

                // 에디트 텍스트에 유저가 입력한 문자열을 가져온다.
                String name  = editName.getText().toString();
                String password = editPasswd.getText().toString();

                Log.i("MyUIApp", "name : " + name);
                Log.i("MyUIApp", "password : " + password);

                // 유저의 이름을, 텍스트뷰에 표시하기
                txtTitle.setText(name);

                // 토스트  :  유저에게 메세지를 보여준다. 짧은시간동안만 나타났다 사라지는 UI
                // 토스트를 이용해서, 유저의 이름을 화면에 보여준다.
                Toast.makeText(getApplicationContext(), "이름은 " + name, Toast.LENGTH_LONG).show();

            }
        });


    }
}