package com.blockent.papago;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioLang;
    EditText editText;
    Button button;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioLang = findViewById(R.id.radioLang);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        txtResult = findViewById(R.id.txtResult);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String source = "ko";

                // 1. 라디오버튼에서 무엇을 눌렀는지 데이터를 가져온다.
                int radioBtnId = radioLang.getCheckedRadioButtonId();
                String target;
                if (radioBtnId == R.id.radioBtn1){
                    target = "en";
                }else if (radioBtnId == R.id.radioBtn2){
                    target = "zh-CN";
                }else if (radioBtnId == R.id.radioBtn3){
                    target = "zh-TW";
                }else if (radioBtnId == R.id.radioBtn4){
                    target = "th";
                }else{
                    Toast.makeText(MainActivity.this, "언어를 선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 2. 에디트텍스트에서 문장가져온다.
                String text = editText.getText().toString().trim();

                if(text.isEmpty()){
                    Toast.makeText(MainActivity.this, "번역할 문장을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 3. 네이버 API 호출해서

                // 4. 결과를 텍스트뷰에 보여준다.
            }
        });

    }
}




