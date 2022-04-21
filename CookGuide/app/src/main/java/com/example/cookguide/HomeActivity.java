package com.example.cookguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.cookguide.PopularFood;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private ForgotPasswordActivity.PhotoAdapter photoAdapter;
    private List<PopularFood> mPopularFood;
    private ImageView imgPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);


        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circle_indicator);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);





        photoAdapter = new ForgotPasswordActivity.PhotoAdapter(this, getListPhoto());
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    private List<ForgotPasswordActivity.Photo> getListPhoto() {
        List<ForgotPasswordActivity.Photo> list = new ArrayList<>();
        list.add(new ForgotPasswordActivity.Photo(R.drawable.img_3));
        list.add(new ForgotPasswordActivity.Photo(R.drawable.img_4));
        list.add(new ForgotPasswordActivity.Photo(R.drawable.img_5));
        list.add(new ForgotPasswordActivity.Photo(R.drawable.img_6));

        return list;

    }
}