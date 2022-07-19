package com.example.cookguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.models.ForgotpwRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText emailForgotPassword;
    Button btnGetNewPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);
        emailForgotPassword = (EditText) findViewById(R.id.editTextForgotPasswordEmail);
        btnGetNewPassword = (Button) findViewById(R.id.buttonGetNewPassword);
        btnGetNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(emailForgotPassword.getText().toString())) {
                    Toast.makeText(ForgotPasswordActivity.this, "Email Required", Toast.LENGTH_LONG).show();
                }else{
                    ForgotPW();
                }
            }
        });
    }
    private void ForgotPW(){
        ForgotpwRequest forgotpwRequest = new ForgotpwRequest();
        forgotpwRequest.setEmail(emailForgotPassword.getText().toString());

        ApiService.apiService.forgotPassword(forgotpwRequest.getEmail()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(ForgotPasswordActivity.this,"Done",Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //startActivity(new Intent(LoginActivity.this,UserProfileActivity.class).putExtra("data",token));
                        Intent intent = new Intent(ForgotPasswordActivity.this,OTPVerifyActivity.class);
                        intent.putExtra("email1",forgotpwRequest.getEmail());
                        //Toast.makeText(LoginActivity.this,loginResponse.getAccessToken(),Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                },700);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public static class PhotoAdapter extends PagerAdapter {

        private Context mContext;
        private List<Photo> mListPhoto;

        public PhotoAdapter(Context mContext, List<Photo> mListPhoto) {
            this.mContext = mContext;
            this.mListPhoto = mListPhoto;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo,container,false);
            ImageView imgPhoto = view.findViewById(R.id.imgPhoto);
            Photo photo = mListPhoto.get(position);
            if (mListPhoto != null) {
                Glide.with(mContext).load(photo.getResourceId()).into(imgPhoto);
            }
            container.addView(view);
            return view;
        }

        @Override

        public int getCount() {
            if (mListPhoto != null) {
                return mListPhoto.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    public static class Photo {

        private int resourceId;

        public Photo(int resourceId) {
            this.resourceId = resourceId;
        }

        public int getResourceId() {
            return resourceId;
        }

        public void setResourceId(int resourceId) {
            this.resourceId = resourceId;
        }
    }
}