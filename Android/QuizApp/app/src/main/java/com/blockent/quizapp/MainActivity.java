package com.blockent.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blockent.quizapp.model.Quiz;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtQuiz;
    TextView txtResult;
    Button btnTrue;
    Button btnFalse;
    ArrayList<Quiz> quizArrayList = new ArrayList<Quiz>();

    int quizIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 화면연결
        txtQuiz = findViewById(R.id.txtQuiz);
        txtResult = findViewById(R.id.txtResult);
        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        // 2. 퀴즈를 만들어 준다.
        setQuizData();

        // 3. 첫번째 퀴즈문제를 화면에 낸다.
        txtQuiz.setText( quizArrayList.get(quizIndex).question );

        // 4. 버튼을 누르면 정답인지 아닌지 알려주는 코드 작성.
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 현재 문제의 정답을 가져온다.
                boolean answer = false;
                if(quizIndex < quizArrayList.size()){
                    answer = quizArrayList.get(quizIndex).answer;
                } else {
                    return;
                }

                // 2. 이버튼은 트루이므로, 정답이 트루이면
                //    결과 텍스트뷰에 "정답입니다." 출력,
                //    그렇지 않으면 "틀렸습니다." 출력
                if (answer == true) {
                    txtResult.setText("정답입니다.");
                } else{
                    txtResult.setText("틀렸습니다.");
                }

                // 3. 그다음 문제를 출제한다.
                quizIndex = quizIndex + 1;
                if (quizIndex < quizArrayList.size()){
                    txtQuiz.setText( quizArrayList.get(quizIndex).question );
                } else {
                    txtQuiz.setText("문제가 더이상 없습니다.");
                }

            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 현재 문제의 정답을 가져온다.
                boolean answer = false;
                if(quizIndex < quizArrayList.size()){
                    answer = quizArrayList.get(quizIndex).answer;
                } else {
                    return;
                }

                // 2. 이버튼은 폴스 이므로, 정답이 폴스 이면
                //    결과 텍스트뷰에 "정답입니다." 출력,
                //    그렇지 않으면 "틀렸습니다." 출력
                if (answer == false) {
                    txtResult.setText("정답입니다.");
                } else{
                    txtResult.setText("틀렸습니다.");
                }

                // 3. 그다음 문제를 출제한다.
                quizIndex = quizIndex + 1;
                if (quizIndex < quizArrayList.size()){
                    txtQuiz.setText( quizArrayList.get(quizIndex).question );
                } else {
                    txtQuiz.setText("문제가 더이상 없습니다.");
                }

            }
        });

    }

    void setQuizData(){
        Quiz q1 = new Quiz(R.string.q1, true);
        quizArrayList.add(q1);
        Quiz q2 = new Quiz(R.string.q2, false);
        quizArrayList.add(q2);
        Quiz q3 = new Quiz(R.string.q3, true);
        quizArrayList.add(q3);
        Quiz q4 = new Quiz(R.string.q4, false);
        quizArrayList.add(q4);
        Quiz q5 = new Quiz(R.string.q4, true);
        quizArrayList.add(q5);
        Quiz q6 = new Quiz(R.string.q6, false);
        quizArrayList.add(q6);
        Quiz q7 = new Quiz(R.string.q7, true);
        quizArrayList.add(q7);
        Quiz q8 = new Quiz(R.string.q8, false);
        quizArrayList.add(q8);
        Quiz q9 = new Quiz(R.string.q9, true);
        quizArrayList.add(q9);
        Quiz q10 = new Quiz(R.string.q10, false);
        quizArrayList.add(q10);
    }

}







