package com.example.cookguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookguide.api.ApiService;
import com.example.cookguide.models.SignupRequest;
import com.example.cookguide.models.SignupResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    TextView txt3 ;
    EditText username;
    EditText email;
    EditText password;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txt3 = (TextView) findViewById(R.id.textView3);
        username = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail2);
        password = findViewById(R.id.editTextPassword2);
        txt3.setTypeface(txt3.getTypeface(), Typeface.BOLD);
        btnSignUp = (Button) findViewById(R.id.buttonRegister);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty((email.getText().toString()))) {
                    Toast.makeText(SignUpActivity.this, "Username and password and Email are Required", Toast.LENGTH_LONG).show();
                }else {
                    //Toast.makeText(LoginActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    SignUp();
                }
            }
        });
    }
    private void SignUp(){
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(username.getText().toString());
        signupRequest.setEmail(email.getText().toString());
        signupRequest.setPassword(password.getText().toString());

        ApiService.apiService.userSignup(signupRequest).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(SignUpActivity.this,"Username or Email already registered",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}