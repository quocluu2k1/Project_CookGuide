package com.example.cookguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextPass;
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

        editTextEmail = (EditText) findViewById(R.id.editTextEmaill);
        editTextPass = (EditText) findViewById(R.id.editTextTextPassword);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        textViewForgotPass = (TextView) findViewById(R.id.textViewForgotPass);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//                startActivity(intent);
//            }
//        });
        textViewForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onClick(View v) {
        Intent intent1 = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent1);
    }
    public void onClick1(View v) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}