package com.blockent.multiple2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPasswd1;
    EditText editPasswd2;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editPasswd1 = findViewById(R.id.editPasswd1);
        editPasswd2 = findViewById(R.id.editPasswd2);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. 이메일 형식 체크

                String email = editEmail.getText().toString().trim();

                Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;

                // 1-1. 유저가 아무것도 입력안했거나, 이메일 형식이 틀리면,
                // 유저한테 이메일 제대로 입력해 주세요. 알려준다.

                if( email.isEmpty() == true || pattern.matcher(email).matches() == false){
                    Toast.makeText(MainActivity.this, "이메일 제대로 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 2. 비밀번호를 체크한다.
                // 비밀번호의 길이가 6자리 이상, 12자리 이하인지 체크한다.
                // 비밀번호가 서로 같은지 체크한다.

                String passwd1 = editPasswd1.getText().toString().trim();
                String passwd2 = editPasswd2.getText().toString().trim();

                if( passwd1.length() < 6 || passwd1.length() > 12 ){
                    Toast.makeText(MainActivity.this, "비밀번호 길이는 6자이상 12자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if( !passwd1.equals(passwd2)) {
                    Toast.makeText(MainActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 정상적으로 수행됬을 경우, 새로운 액티비티를 실행한다.
                Intent intent = new Intent(MainActivity.this, AvataActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);

                // 이 액티비티는, 위의 새로운 액티비티를 실행시키고 나서, 종료한다.
                finish();

            }
        });

    }
}