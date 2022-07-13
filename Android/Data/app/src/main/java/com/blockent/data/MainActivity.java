package com.blockent.data;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editName;
    EditText editAge;

    Button button;


    ActivityResultLauncher<Intent> startActivityResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            
                        }
                    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmail.getText().toString().trim();
                String name = editName.getText().toString().trim();
                String ageStr = editAge.getText().toString().trim();

                int age = Integer.valueOf(ageStr).intValue();

                double hiddenData = 3.1414;

                // 새로운 액티비티를 띄우고,
                // 데이터를 전달한다.
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                intent.putExtra("email", email);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("hidden", hiddenData);

                // 데이터만 전달하는 경우
//                startActivity(intent);

                // 실행한 액티비티로부터 데이터를 받아오는 것이 있는 경우!!
                startActivityForResult(intent, 0);
            }
        });


    }
}






