package com.example.cookguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {


    final String codeEmojiClapping = "&#128079;";
    final String codeEmojiHeart = "&#10084;";
    final String codeEmojiFace = "&#128523;";

    private Button buttonReactClap;
    private Button buttonReactHeart;
    private Button buttonReactFace;

    private int numberReactFace = 2;
    private int numberReactHeart = 7;
    private int numberReactClap = 5;
    private boolean statusReactFace = false;
    private boolean statusReactHeart = false;
    private boolean statusReactClap = false;

    private ProgressBar progressBarLoad;
    private Button buttonLoad;

    private TabLayout tabLayout;
    private ViewPager detailViewPager;
    
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout_detail_cook_guide);
        detailViewPager = findViewById(R.id.view_pager_detail_cook_guide);



        DetailCookGuideAdapter detailCookGuideAdapter = new DetailCookGuideAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        detailViewPager.setAdapter(detailCookGuideAdapter);
        tabLayout.setupWithViewPager(detailViewPager);

//        progressBarLoad = findViewById(R.id.progressBar);
//        buttonLoad = findViewById(R.id.button);

//        buttonLoad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                int current = progressBarLoad.getProgress();
////                progressBarLoad.setProgress(current+5);
//                setContentView(R.layout.detail_cook_guide);
////                for(int i=0;i<=1000;i++){
////                    int j=i;
////                    if(i>100){
////                        j=i%100;
////                    }
////                    final Handler handler = new Handler(Looper.getMainLooper());
////                    int finalI = 100-j;
////                    handler.postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
//////                            int current = progressBarLoad.getProgress();
//////                            int current = finalI;
////                            progressBarLoad.setProgress(finalI);
////                        }
////                    }, i*50);
////
////                }
//
//            }
//        });


        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);

//        View abc = new ViewPager.(R.layout.detail_cook_guide);
        photoAdapter = new PhotoAdapter(this, getListPhoto());
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        buttonReactClap = findViewById(R.id.buttonReactClap);
        buttonReactHeart = findViewById(R.id.buttonReactHeart);
        buttonReactFace = findViewById(R.id.buttonReactFace);

//        buttonReactFace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(statusReactFace == false){
//                    numberReactFace++;
//                }
//                else{
//                    numberReactFace--;
//                }
//                buttonReactFace.setText(codeEmojiFace+" "+numberReactFace);
//            }
//        });
    }
    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.tomnuong123));
        list.add(new Photo(R.drawable.abc2));
        list.add(new Photo(R.drawable.abc3));
        return list;
    }

    //event click react food

}