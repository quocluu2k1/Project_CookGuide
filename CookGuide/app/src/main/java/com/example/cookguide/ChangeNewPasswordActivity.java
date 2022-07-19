package com.example.cookguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cookguide.api.ApiService;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeNewPasswordActivity extends AppCompatActivity {
    Button btnChangeNewPassword;
    EditText textOldpassword;
    EditText textNewpassword;
    public String authToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiaW4xIiwiaWF0IjoxNjUxNTgxMDI5LCJleHAiOjE2NTE2Njc0Mjl9.alsEdz8AyboVoVhb3tGq4uN16ck6mXPFIybkgmUrmreTF9B1tDZOTkEkCwO9gDqjB2sExb4jx-dwQ_lQKP9Lcw";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_new_password);
        textOldpassword = findViewById(R.id.oldpw);
        textNewpassword = findViewById(R.id.newpw);
        btnChangeNewPassword = (Button) findViewById(R.id.buttonChangeNewPassword);
        btnChangeNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(textOldpassword.getText().toString()) || TextUtils.isEmpty(textNewpassword.getText().toString())) {
                    Toast.makeText(ChangeNewPasswordActivity.this, " Pls type in", Toast.LENGTH_LONG).show();
                }else {
                    //Toast.makeText(LoginActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    changePassword();
                /*Intent intent = new Intent(ChangeNewPasswordActivity.this, HomeActivity.class);
                startActivity(intent);*/
                }
            }
        });
    }
    private void changePassword(){
        //String oldpassword = textOldpassword.getText().toString();
        String strOldpassword =  textOldpassword.getText().toString().trim();
        String strNewpassword = textNewpassword.getText().toString().trim();

        RequestBody requestBodyoldpassword = RequestBody.create(MediaType.parse("multipart/form-data"),strOldpassword);
        RequestBody requestBodynewpassword = RequestBody.create(MediaType.parse("multipart/form-data"),strNewpassword);
        ApiService.apiService.changePassword(getToken(),requestBodyoldpassword,requestBodynewpassword).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean result = response.body();
                if(result){
                    Toast.makeText(ChangeNewPasswordActivity.this,"Succesfully changed password",Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //startActivity(new Intent(LoginActivity.this,UserProfileActivity.class).putExtra("data",token));
                            Intent intent = new Intent(ChangeNewPasswordActivity.this,LoginActivity.class);
                            //intent.putExtra("email1",forgotpwRequest.getEmail());
                            //Toast.makeText(LoginActivity.this,loginResponse.getAccessToken(),Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    },700);
                }else {
                    Toast.makeText(ChangeNewPasswordActivity.this,"Oldpassword is wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ChangeNewPasswordActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }
    private String getToken() {
        SharedPreferences prefs;
        prefs=ChangeNewPasswordActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }

}