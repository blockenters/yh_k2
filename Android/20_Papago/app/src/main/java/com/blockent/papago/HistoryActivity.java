package com.blockent.papago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.blockent.papago.adapter.HistoryAdapter;
import com.blockent.papago.model.Papago;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryAdapter adapter;
    ArrayList<Papago> papagoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        papagoList = (ArrayList<Papago>) getIntent().getSerializableExtra("papagoList");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));

        adapter = new HistoryAdapter(HistoryActivity.this, papagoList);
        recyclerView.setAdapter(adapter);

    }
}