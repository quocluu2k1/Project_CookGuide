package com.example.cookguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OTPVerifyActivity extends AppCompatActivity {
    Button btnConfirmOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);
        btnConfirmOTP = (Button) findViewById(R.id.buttonConfirmOTP);
        btnConfirmOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTPVerifyActivity.this, ChangeNewPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}