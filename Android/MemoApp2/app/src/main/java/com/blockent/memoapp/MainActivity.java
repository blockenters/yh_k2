package com.blockent.memoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blockent.memoapp.adapter.MemoAdapter;
import com.blockent.memoapp.data.DatabaseHandler;
import com.blockent.memoapp.model.Memo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;

    EditText editSearch;
    ImageView imgSearch;
    ImageView imgDelete;

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

        editSearch = findViewById(R.id.editSearch);
        imgSearch = findViewById(R.id.imgSearch);
        imgDelete = findViewById(R.id.imgDelete);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 에디트 텍스트에서 검색어 가져온다.
                String keyword = editSearch.getText().toString().trim();

                // 2. 검색어로 DB에 쿼리한다.
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.searchMemo(keyword);

                // 3. 검색어에 매칭한 메모 결과들을 화면에 표시한다.
                adapter = new MemoAdapter(MainActivity.this, memoList);
                recyclerView.setAdapter(adapter);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 에디트 텍스트의 문자열을 지운다.
                editSearch.setText("");
                
                // 2. 디비에서 저장되어있는 모든 메모를 가져와야 한다.
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.getAllMemo();

                // 3. 가져온 메모를 화면에 표시
                adapter = new MemoAdapter(MainActivity.this, memoList);
                recyclerView.setAdapter(adapter);
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 키워드 검색 에디트텍스트에 글자를 쓸때마다,
                // 자동으로 해당 검색어를 가져와서, 디비에서 쿼리해서
                // 검색 결과를 화면에 표시해 주는 기능개발.

                String keyword = editSearch.getText().toString().trim();
                Log.i("MyMemoApp", keyword);

                if (keyword.length() < 2){
                    return;
                }

                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.searchMemo(keyword);
                adapter = new MemoAdapter(MainActivity.this, memoList);
                recyclerView.setAdapter(adapter);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        // 검색키워드 에디트 텍스트에 글자가 있으면, 없애도록한다.
        editSearch.setText("");

        // 데이터랑 연결!
        // 메모데이터가 없다!!!! 디비에서 가져오자.
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        memoList = db.getAllMemo();
        adapter = new MemoAdapter(MainActivity.this, memoList);
        recyclerView.setAdapter(adapter);
        db.close();

    }
}






