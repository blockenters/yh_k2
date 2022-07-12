package com.blockent.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtTimer;
    TextView txtCount;
    Button button;

    // 타이머를 사용하기 위한, 멤버변수로 선언
    CountDownTimer timer;

    final int millisInFuture = 5000;
    final int countDownInterval = 1000;

    int count = 0;

    boolean isFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTimer = findViewById(R.id.txtTimer);
        txtCount = findViewById(R.id.txtCount);
        button = findViewById(R.id.button);

        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long l) {
                // 남은 시간을 계산해서
                // 텍스트뷰에 보여준다.
//                Log.i("MyTimer", "남은 시간 : " + l);

                long remain = l / 1000;
                // int remain = (int) l / 1000;

                txtTimer.setText("남은 시간 : " + remain);
            }

            @Override
            public void onFinish() {
                Log.i("MyTimer", "타이머 끝났다!!");

                isFinish = true;
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼 클릭시, 화면에 카운트 표시!

                if (isFinish == false){
                    count = count + 1;
                    txtCount.setText("탭 횟수 : " + count);
                }
           }
        });

        timer.start();

    }
}






