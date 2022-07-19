package com.example.cookguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Timestamp1Activity extends AppCompatActivity {

    private TextView textTest;
    private ImageButton buttonPlay;
    private Boolean statusButtonPlay;
    private CountDownTimer countDownTimer;
    private TextView textviewTimer;
    private ProgressBar progressBar;
    private int timerPause;
    private ImageButton buttonCloseTimestamp;
    private TextView textViewStepGuide;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.timestamp1);

        mediaPlayer = MediaPlayer.create(this, R.raw.notificationsound);

        Intent intent = getIntent();
        int time = intent.getIntExtra("time",0);
        String stringStepGuide = intent.getStringExtra("stepGuide");
        timerPause = time;

        statusButtonPlay = false;

        textTest = findViewById(R.id.textTest);
        textviewTimer = findViewById(R.id.textviewTimer);
        textviewTimer.setText(updateTimer(time));
        buttonPlay = findViewById(R.id.buttonPlay);
        progressBar = findViewById(R.id.progressBar);
        buttonCloseTimestamp = findViewById(R.id.buttonCloseTimestamp);
        textViewStepGuide = findViewById(R.id.textViewStepGuide);

        textViewStepGuide.setText(stringStepGuide);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusButtonPlay){
                    buttonPlay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                    countDownTimer.cancel();
                }else {
                    buttonPlay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    countDownTimer = new CountDownTimer(timerPause*1000,1000) {
                        @Override
                        public void onTick(long l) {
                            updateTimer((int)l/1000);
                            timerPause = (int)l/1000;
                            progressBar.setProgress((int)l/time);
                        }

                        @Override
                        public void onFinish() {
                            buttonPlay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                            timerPause = time;
                            progressBar.setProgress(0);
                            mediaPlayer.start();

                        }
                    }.start();
                }
                statusButtonPlay = !statusButtonPlay;
            }
        });

        buttonCloseTimestamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public String updateTimer(int secondsLeft){
        int hours = secondsLeft/3600;
        int minutes = (secondsLeft-hours*3600)/60;
        int seconds = secondsLeft -hours*3600 - minutes*60;
        String secondString = Integer.toString(seconds);
        String minutesString = Integer.toString(minutes);
        String hoursString = Integer.toString(hours);
        if(seconds<=9){
            secondString = "0"+secondString;
        }
        if(minutes<=9){
            minutesString = "0"+minutesString;
        }
        if(hours<=9){
            hoursString = "0"+hoursString;
        }
        String timer = hoursString+":"+minutesString+":"+secondString;
        textviewTimer.setText(timer);
        return timer;
    }
}