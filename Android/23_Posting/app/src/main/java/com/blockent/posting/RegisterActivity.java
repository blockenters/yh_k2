package com.blockent.posting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blockent.posting.api.NetworkClient;
import com.blockent.posting.api.UserApi;
import com.blockent.posting.config.Config;
import com.blockent.posting.model.User;
import com.blockent.posting.model.UserRes;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    EditText editName;
    Button btnRegister;
    TextView txtLogin;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editName = findViewById(R.id.editName);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmail.getText().toString().trim();
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(pattern.matcher(email).matches() == false){
                    Toast.makeText(RegisterActivity.this, "이메일 제대로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = editPassword.getText().toString().trim();
                if(password.length() < 4 || password.length() > 12){
                    Toast.makeText(RegisterActivity.this, "비번길이는 4자이상 12자 이하로만 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = editName.getText().toString().trim();
                if(name.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit retrofit = NetworkClient.getRetrofitClient(RegisterActivity.this);
                UserApi api = retrofit.create(UserApi.class);

                User user = new User(email, password, name);

                Call<UserRes> call = api.register(user);
                
                // 네트워크에 보내기전.
                showProgress(getString(R.string.dailog_main_register));
                
                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 네트워크로부터 데이터를 받았을때
                        dismissProgress();

                        if(response.isSuccessful()){

                           UserRes userRes = response.body();
                           String accessToken = userRes.getAccess_token();

                           SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                           SharedPreferences.Editor editor = sp.edit();
                           editor.putString("accessToken", accessToken);
                           editor.apply();

                           Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                           startActivity(intent);

                           finish();

                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        // 네트워크로부터 데이터를 받았을때
                        dismissProgress();
                    }
                });


            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();

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