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
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookguide.api.ApiService;
import com.example.cookguide.models.LoginRequest;
import com.example.cookguide.models.LoginResponse;
import com.example.cookguide.models.UserResponse;
import com.google.android.gms.common.api.Api;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    TextView textViewForgotPass;
    TextView textViewRegister;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);


        username = (EditText) findViewById(R.id.editTextEmaill);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        textViewForgotPass = (TextView) findViewById(R.id.textViewForgotPass);
        btnLogin = (Button) findViewById(R.id.buttonLogin);

        if(getToken()!=null){
            ApiService.apiService.verifyToken(getToken()).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){
                        Intent intent = new Intent(LoginActivity.this,MainActivity1.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        }

        textViewForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Username and password Required", Toast.LENGTH_LONG).show();
                }else {
                    login();
                }
            }

        });
    }
    private void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        ApiService.apiService.userLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Login successfully",Toast.LENGTH_LONG).show();
                    //token = response.body().getAccessToken();
                    SharedPreferences prefs;
                    SharedPreferences.Editor edit;
                    prefs=LoginActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    edit=prefs.edit();
                    String saveToken = response.body().getAccessToken();
                    edit.putString("token",saveToken);
                    edit.commit();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoginResponse loginResponse = response.body();
                            //startActivity(new Intent(LoginActivity.this,UserProfileActivity.class).putExtra("data",token));
                            Intent intent = new Intent(LoginActivity.this,MainActivity1.class);
                            intent.putExtra("token",loginResponse.getAccessToken());
                            //Toast.makeText(LoginActivity.this,loginResponse.getAccessToken(),Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    },700);
                }else {
                    Toast.makeText(LoginActivity.this,"Username or password is invalid",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,""+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    private String getToken() {
        SharedPreferences prefs;
        prefs=LoginActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
    public void onClick(View v) {
        Intent intent1 = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent1);
    }
}