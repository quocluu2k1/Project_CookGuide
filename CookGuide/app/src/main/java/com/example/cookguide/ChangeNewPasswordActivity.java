package com.example.cookguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangeNewPasswordActivity extends AppCompatActivity {
    Button btnChangeNewPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_new_password);
        btnChangeNewPassword = (Button) findViewById(R.id.buttonChangeNewPassword);
        btnChangeNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeNewPasswordActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}