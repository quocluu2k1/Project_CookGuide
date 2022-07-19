package com.example.cookguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OTPVerifyActivity extends AppCompatActivity {
    Button btnConfirmOTP;
    TextView emailTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);
        btnConfirmOTP = (Button) findViewById(R.id.buttonConfirmOTP);
        emailTextview = findViewById(R.id.textView8);

        Intent intent = getIntent();
        String str = intent.getStringExtra("email1");
        emailTextview.setText(str);
        Toast.makeText(OTPVerifyActivity.this, str, Toast.LENGTH_SHORT).show();
        btnConfirmOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTPVerifyActivity.this, ChangeNewPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
