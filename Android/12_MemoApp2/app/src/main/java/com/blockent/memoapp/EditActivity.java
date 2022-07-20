package com.blockent.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blockent.memoapp.data.DatabaseHandler;
import com.blockent.memoapp.model.Memo;

public class EditActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnSave;

    Memo memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // 다른 액티비티로부터 받아온 데이터 처리.
        memo = (Memo) getIntent().getSerializableExtra("memo");

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);

        // 데이터를 화면에 표시!!!!!!!!!!!!
        editTitle.setText(memo.title);
        editContent.setText(memo.content);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 에디트텍스트로부터 유저가 입력한 데이터 가져오기
                String title = editTitle.getText().toString().trim();
                String content = editContent.getText().toString().trim();
                
                // 2. DB 에 업데이트 하기
                DatabaseHandler db = new DatabaseHandler(EditActivity.this);
                db.updateMemo(memo.id, title, content);
                
                // 3. 액티비티 종료
                finish();
            }
        });

    }
}





