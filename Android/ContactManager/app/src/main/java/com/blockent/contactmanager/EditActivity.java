package com.blockent.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blockent.contactmanager.data.DatabaseHandler;
import com.blockent.contactmanager.model.Contact;

public class EditActivity extends AppCompatActivity {

    // 넘어온 데이터에 대한 멤버변수
    int id;
    String name;
    String phone;

    Contact contact;

    // 화면 UI 에 대한 멤버변수
    EditText editName;
    EditText editPhone;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        
        // 다른액티비티로부터 넘겨받은 데이터가 있으면, 이 데이터를 먼저 처리하자.

        contact = (Contact) getIntent().getSerializableExtra("contact");

//        id = getIntent().getIntExtra("id", 0);
//        name = getIntent().getStringExtra("name");
//        phone = getIntent().getStringExtra("phone");
        
        // 화면과 변수 연결
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

        // 넘겨받은 데이터를 화면에 셋팅!!!!!
        editName.setText(contact.name);
        editPhone.setText(contact.phone);

        // 버튼 클릭시 처리할 코드 작성 : DB 에 저장하고, 액티비티 종료해서, 메인으로돌아감.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if(name.isEmpty() || phone.isEmpty()){
                    Toast.makeText(EditActivity.this, "이름이나 전화번호는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseHandler db = new DatabaseHandler(EditActivity.this);

                // 업데이트에 필요한 파라미터는, Contact  클래스의 객체다.
                // 따라서 Contact 클래스의 객체를 만들어서, 함수호출해준다.
//                Contact contact = new Contact(id, name, phone);

                contact.name = name;
                contact.phone = phone;

                db.updateContact(contact);

                // 액티비종료하면, 자동으로 메인액티비티가 나온다.
                finish();
            }
        });
    }
}





