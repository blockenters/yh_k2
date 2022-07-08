package com.blockent.calculate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editPercent;
    EditText editNumber;
    Button button;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 화면과 연결
        editPercent = findViewById(R.id.editPercent);
        editNumber = findViewById(R.id.editNumber);
        button = findViewById(R.id.button);
        txtResult = findViewById(R.id.txtResult);

        // 버튼 눌렀을때 동작하는 코드 정의
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. 에디트 텍스트에 적힌 문자열을 가져오기
                String percentStr = editPercent.getText().toString();
                String numberStr = editNumber.getText().toString();

                // 2. 계산한다.
                // 2-1. 먼저 숫자로 바꿔주자.
                double percent = Double.valueOf(percentStr).doubleValue();
                double number = Double.valueOf(numberStr).doubleValue();

                double result = percent * number / 1000;

                // 3. 결과를 텍스트뷰에 보여준다.

                txtResult.setText( ""+result );

            }
        });

    }
}