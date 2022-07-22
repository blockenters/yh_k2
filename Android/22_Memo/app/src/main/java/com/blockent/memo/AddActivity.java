package com.blockent.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.blockent.memo.api.MemoApi;
import com.blockent.memo.api.NetworkClient;
import com.blockent.memo.config.Config;
import com.blockent.memo.model.Memo;
import com.blockent.memo.model.PostRes;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddActivity extends AppCompatActivity {

    EditText editTitle;
    Button btnDate;
    Button btnTime;
    EditText editContent;
    Button btnSave;

    String date = "";
    String time = "";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTitle = findViewById(R.id.editTitle);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString().trim();
                if(title.isEmpty()){
                    Toast.makeText(AddActivity.this, "제목은 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(date.isEmpty()){
                    Toast.makeText(AddActivity.this, "날짜는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(time.isEmpty()){
                    Toast.makeText(AddActivity.this, "시간은 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String content = editContent.getText().toString().trim();

                // 네트워크로 데이터를 보낸다.
                showProgress("메모 저장중...");

                Retrofit retrofit = NetworkClient.getRetrofitClient(AddActivity.this);

                MemoApi api = retrofit.create(MemoApi.class);

                // 헤더에 셋팅할 토큰 가져온다.
                SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                String token = sp.getString("accessToken", "");
                // 보낼 데이터를 만든다.
                Memo memo = new Memo(title, date+" "+time, content );

                Call<PostRes> call = api.addMemo("Bearer "+token, memo);

                call.enqueue(new Callback<PostRes>() {
                    @Override
                    public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                        dismissProgress();

                        if(response.isSuccessful()){

                            Toast.makeText(AddActivity.this, "잘 저장되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();

                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<PostRes> call, Throwable t) {
                        dismissProgress();
                    }
                });

            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar current = Calendar.getInstance();

                new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String monthStr;
                                int month = i1 + 1;
                                if(month < 10){
                                    monthStr = "0"+month;
                                }else{
                                    monthStr = ""+month;
                                }
                                String dayStr;
                                if(i2 < 10){
                                    dayStr = "0"+i2;
                                }else{
                                    dayStr = ""+i2;
                                }

                                date = i + "-" + monthStr + "-" + dayStr;
                                btnDate.setText(date);
                            }
                        },
                        current.get(Calendar.YEAR),
                        current.get(Calendar.MONTH),
                        current.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar current = Calendar.getInstance();

                new TimePickerDialog(
                        AddActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String hourStr;
                                if(i < 10){
                                    hourStr = "0"+i;
                                }else{
                                    hourStr = ""+i;
                                }
                                String minStr;
                                if(i1 < 10){
                                    minStr = "0"+i1;
                                }else{
                                    minStr = ""+i1;
                                }

                                time = hourStr + ":" + minStr;
                                btnTime.setText(time);

                            }
                        },
                        current.get(Calendar.HOUR_OF_DAY),
                        current.get(Calendar.MINUTE),
                        true
                ).show();
            }
        });
    }

    void showProgress(String message){
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }

    void dismissProgress(){
        dialog.dismiss();
    }
}






