package com.blockent.multiple2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AvataActivity extends AppCompatActivity {

    ImageView imgAvata;
    Button btn1;
    Button btn2;
    Button btnSave;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avata);

        // 데이터를 넘겨받는 코드 작성.
        email = getIntent().getStringExtra("email");

        imgAvata = findViewById(R.id.imgAvata);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btnSave = findViewById(R.id.btnSave);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 토끼로 이미지를 셋팅한다.
                imgAvata.setImageResource(R.drawable.img1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 거북이로 이미지를 셋팅한다.
                imgAvata.setImageResource(R.drawable.img2);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 알러트 다이얼로그 띄운다.
                AlertDialog.Builder alert = new AlertDialog.Builder(AvataActivity.this);
                alert.setTitle("회원가입 완료");
                alert.setMessage("완료하시겠습니까?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 새로운 액티비티를 띄운다.
                        Intent intent = new Intent(AvataActivity.this, WelcomActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        
                        // 현재의 아바타 액티비티를 종료
                        finish();
                        
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alert.show();
            }
        });


    }
}