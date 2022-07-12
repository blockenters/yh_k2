package com.blockent.timer2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imgAlarm;
    TextView txtCount;
    EditText editTime;
    Button btnCancel;
    Button btnStart;

    // 타이머를 사용하기 위한, 멤버변수로 선언
    CountDownTimer timer;

    // 타이머 전체 초 : 60초
    long millisInFuture = 60000;
    // 감소시킬 초 : 1초
    final int countDownInterval = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAlarm = findViewById(R.id.imgAlarm);
        txtCount = findViewById(R.id.txtCount);
        editTime = findViewById(R.id.editTime);
        btnCancel = findViewById(R.id.btnCancel);
        btnStart = findViewById(R.id.btnStart);

        // 타이머 시작 버튼 눌렀을때.
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. 에디트텍스트에서, 유저가 입력한 초 정보를 가져온다.
                String timeStr = editTime.getText().toString().trim();

                // 2. 에디트텍스트의 내용이 있는지 확인해서 없으면, 실행 안시킨다.
                if (timeStr.isEmpty()  ){
                    return;
                }

                millisInFuture = Long.valueOf(timeStr).longValue() * 1000 ;

                // 3. 타이머를 만들고, 실행시킨다.

                timer = new CountDownTimer(millisInFuture, countDownInterval) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {

                    }
                };
                timer.start();


            }
        });

    }
}







