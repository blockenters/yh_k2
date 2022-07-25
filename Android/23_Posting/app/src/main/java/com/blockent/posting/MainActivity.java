package com.blockent.posting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.blockent.posting.config.Config;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        if(accessToken.isEmpty()){

            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);

            finish();
            return;
        }


    }
}






