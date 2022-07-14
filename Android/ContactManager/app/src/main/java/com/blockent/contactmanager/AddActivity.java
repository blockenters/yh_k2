package com.blockent.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blockent.contactmanager.data.DatabaseHandler;
import com.blockent.contactmanager.model.Contact;

public class AddActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 유저가 입력한 이름과 폰번 가져오기
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if(name.isEmpty() || phone.isEmpty() ) {
                    Toast.makeText(AddActivity.this, "이름과 전화번호는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 2. 컨택 객체로 만들기
                Contact contact = new Contact(name, phone);
                
                // 3. 디비에 저장하기
                // 3-1. 디비 핸들러 가져오기
                DatabaseHandler db = new DatabaseHandler(AddActivity.this);
                // 3-2. 주소록 저장하는 함수 호출
                db.addContact(contact);

                // 3-3. 토스트 보여주기
                Toast.makeText(AddActivity.this, "잘 저장되었습니다.", Toast.LENGTH_SHORT).show();
                
                // 4. 액티비티 종료하기
                finish();
            }
        });

    }
}








