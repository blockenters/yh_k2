package com.blockent.youtube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    ImageView imgSearch;

    // 리사이클러뷰 관련 멤버변수
    RecyclerView recyclerView;
    // 어댑터, 어레이리스트 필요


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}