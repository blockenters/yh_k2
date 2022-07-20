package com.blockent.timer2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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
                if (timeStr.isEmpty()  || !isNumeric(timeStr)  ){
                    return;
                }

                millisInFuture = Long.valueOf(timeStr).longValue() * 1000 ;

                // 3. 타이머를 만들고, 실행시킨다.

                timer = new CountDownTimer(millisInFuture, countDownInterval) {
                    @Override
                    public void onTick(long l) {
                        
                        long remain = l / 1000;
                        // int remain = (int) l / 1000;

                        txtCount.setText(remain + "초");
                    }

                    @Override
                    public void onFinish() {
                        
                        // 1. 에니메이션 효과
                        YoYo.with(Techniques.Bounce).duration(400)
                                .repeat(4).playOn(imgAlarm);
                        
                        // 2. 알람음 효과
                        MediaPlayer mp =
                                MediaPlayer.create(MainActivity.this, R.raw.alarm);
                        mp.start();

                    }
                };
                timer.start();


            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 타이머 취소
                timer.cancel();
                // 텍스트뷰에 "타이머 취소되었음"
                txtCount.setText("타이머 취소되었음.");
            }
        });

    }


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // 기계의 백버튼을 눌렀을때 실행되는 함수.
    @Override
    public void onBackPressed() {

        // 알러트 다이얼로그 띄운다.
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("앱을 종료하시겠습니까?");
        alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.setNegativeButton("아니오", null);
        alert.show();

    }
}







