package com.example.cookguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText emailForgotPassword;
    Button btnGetNewPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailForgotPassword = (EditText) findViewById(R.id.editTextForgotPasswordEmail);
        btnGetNewPassword = (Button) findViewById(R.id.buttonGetNewPassword);
        btnGetNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, OTPVerifyActivity.class);
                startActivity(intent);
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