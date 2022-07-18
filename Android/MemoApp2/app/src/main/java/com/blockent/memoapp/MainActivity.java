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

    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoList;
    
    // 깃허브 연동 완료


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        // 데이터랑 연결!
        // 메모데이터가 없다!!!! 디비에서 가져오자.
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        memoList = db.getAllMemo();
        adapter = new MemoAdapter(MainActivity.this, memoList);
        recyclerView.setAdapter(adapter);
        db.close();

    }
}






