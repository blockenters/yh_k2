package com.blockent.diceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView imgDice1;
    ImageView imgDice2;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 화면에서 만든 버튼과 이미지들을 가져온다.
        Button button = findViewById(R.id.button);
        imgDice1 = findViewById(R.id.imgDice1);
        imgDice2 = findViewById(R.id.imgDice2);
        // 사운드 파일도 가져온다.
        mp = MediaPlayer.create(this, R.raw.dice_sound);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MyDiceApp", "버튼 눌렀음!!");

                // 0. 사운드를 튼다.
                mp.start();

                // 1. 랜덤으로 숫자를 하나 가져온다.
                Random random = new Random();
                int number = random.nextInt(6);
                int number2 = random.nextInt(6);

                Log.i("MyDiceApp", "number : " + number + ", number2 : " + number2);

                // 2. 그 숫자로 화면의 이미지를 바꿔준다
                if (number == 0){
                    imgDice1.setImageResource(R.drawable.dice1);
                }else if(number == 1){
                    imgDice1.setImageResource(R.drawable.dice2);
                }else if(number == 2){
                    imgDice1.setImageResource(R.drawable.dice3);
                }else if(number == 3){
                    imgDice1.setImageResource(R.drawable.dice4);
                }else if(number == 4){
                    imgDice1.setImageResource(R.drawable.dice5);
                }else if(number == 5){
                    imgDice1.setImageResource(R.drawable.dice6);
                }

                if (number2 == 0){
                    imgDice2.setImageResource(R.drawable.dice1);
                }else if(number2 == 1){
                    imgDice2.setImageResource(R.drawable.dice2);
                }else if(number2 == 2){
                    imgDice2.setImageResource(R.drawable.dice3);
                }else if(number2 == 3){
                    imgDice2.setImageResource(R.drawable.dice4);
                }else if(number2 == 4){
                    imgDice2.setImageResource(R.drawable.dice5);
                }else if(number2 == 5){
                    imgDice2.setImageResource(R.drawable.dice6);
                }

            }
        });

    }
}






