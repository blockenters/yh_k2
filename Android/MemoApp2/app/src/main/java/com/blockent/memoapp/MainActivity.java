package com.blockent.memoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blockent.memoapp.adapter.MemoAdapter;
import com.blockent.memoapp.data.DatabaseHandler;
import com.blockent.memoapp.model.Memo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;

    // 리사이클러 뷰를 처리하기 위한 멤버변수들
    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // 메모 리스트에는 데이터가 없습니다.
        // DB에 있습니다.
        // 따라서 먼저, 디비에 저장된 메모리스트를 가져옵니다.
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        memoList = db.getAllmemos();
        adapter = new MemoAdapter(MainActivity.this, memoList);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
}






