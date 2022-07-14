package com.blockent.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blockent.contactmanager.data.DatabaseHandler;
import com.blockent.contactmanager.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btnAdd);
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
        printDBData();
    }


    // DB에 있는 모든 주소록 데이터를, 디버깅용으로 로그캣 출력하는 함수
    void printDBData(){
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        ArrayList<Contact> contactList = db.getAllContacts();
        for(Contact data : contactList){
            Log.i("MyContact",
            "id : " + data.id + " , name : "+data.name+" , phone : "+data.phone);
        }
    }

}





//    // 데이터베이스에 데이터 넣고, 가져오는것 테스트
//    DatabaseHandler db = new DatabaseHandler(MainActivity.this);
//
//    // 주소록 데이터를 디비에 저장하는 코드
//    Contact contact = new Contact("홍길동", "010-1111-2222");
//        db.addContact(contact);
//
//                // 주소록 데이터를 디비에서 가져와서, 로그 찍어보자.
//                ArrayList<Contact> contactList = db.getAllContacts();
//
//        for(Contact data : contactList){
//        Log.i("MyContact",
//        "id : " + data.id + " , name : "+data.name+" , phone : "+data.phone);
//        }
//
//        // 주소록 디비에서 id 가 1인 주소록 데이터 로그 찍어보자.
//        Contact contact1 = db.getContact(1);
//        Log.i("MyContact",
//        "디비에서 하나만 가져오기, id : "+contact1.id +" , name : " +contact1.name + " , phone : "+contact1.phone);



