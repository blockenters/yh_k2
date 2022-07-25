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

public class EditActivity extends AppCompatActivity {

    EditText editTitle;
    Button btnDate;
    Button btnTime;
    EditText editContent;
    Button btnSave;
    private String date;
    private String time;
    private ProgressDialog dialog;

    Memo memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        memo = (Memo) getIntent().getSerializableExtra("memo");

        editTitle = findViewById(R.id.editTitle);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);

        // 테이터를 화면에 셋팅
        editTitle.setText(memo.getTitle());

        // "2022-07-02T11:00:00"
        String[] dateArray = memo.getDate().substring(0, 15 + 1).split("T");
        btnDate.setText(dateArray[0]);
        btnTime.setText(dateArray[1]);

        editContent.setText(memo.getContent());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = editTitle.getText().toString().trim();
                if (title.isEmpty()) {
                    Toast.makeText(EditActivity.this, "제목은 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                date = btnDate.getText().toString().trim();

                if (date.isEmpty()) {
                    Toast.makeText(EditActivity.this, "날짜는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                time = btnTime.getText().toString().trim();

                if (time.isEmpty()) {
                    Toast.makeText(EditActivity.this, "시간은 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String content = editContent.getText().toString().trim();

                // 네트워크로 데이터 보내기.
                Retrofit retrofit = NetworkClient.getRetrofitClient(EditActivity.this);
                MemoApi api = retrofit.create(MemoApi.class);

                SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                String accessToken = sp.getString("accessToken","");

                memo.setTitle(title);
                memo.setDate(date+" "+time);
                memo.setContent(content);

                Call<PostRes> call = api.updateMemo("Bearer "+accessToken, memo.getId(), memo );

                showProgress("메모 수정중...");

                call.enqueue(new Callback<PostRes>() {
                    @Override
                    public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                        dismissProgress();
                        if(response.isSuccessful()){
                            // HTTP 상태 코드가 200일때
                            Toast.makeText(EditActivity.this, "수정완료 되었습니다.", Toast.LENGTH_SHORT).show();
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

                new DatePickerDialog(EditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String monthStr;
                                int month = i1 + 1;
                                if (month < 10) {
                                    monthStr = "0" + month;
                                } else {
                                    monthStr = "" + month;
                                }
                                String dayStr;
                                if (i2 < 10) {
                                    dayStr = "0" + i2;
                                } else {
                                    dayStr = "" + i2;
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
                        EditActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String hourStr;
                                if (i < 10) {
                                    hourStr = "0" + i;
                                } else {
                                    hourStr = "" + i;
                                }
                                String minStr;
                                if (i1 < 10) {
                                    minStr = "0" + i1;
                                } else {
                                    minStr = "" + i1;
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


    void showProgress(String message) {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }

    void dismissProgress() {
        dialog.dismiss();
    }
}
