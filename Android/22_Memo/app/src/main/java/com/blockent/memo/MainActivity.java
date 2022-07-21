package com.blockent.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blockent.memo.config.Config;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 쉐어드프리퍼런스에 억세스토큰을 가져온다.
        SharedPreferences sp = 
                getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");
        
        // 2. 만약 억세스토큰이 없으면, 회원가입 액티비티를 실행하고,
        //    그렇지 않으면, 메모가져오는 API 호출해서, 리사이클러뷰로 화면에 내 메모 보여준다.
        if(accessToken.isEmpty()){
            Intent intent = 
                    new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);            
        } else {
            // todo 내 메모 가져오는 api 호출
        }
    }
}





