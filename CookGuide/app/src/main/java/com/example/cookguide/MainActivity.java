package com.example.cookguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookguide.common.ImagePicker;
import com.google.android.material.tabs.TabLayout;
//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.normal.TedPermission;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private Button buttonDetail;
    private Button buttonCountTime;
    private TextView textView;
    protected ImageView imageSelected;
    private Button buttonHome;
    private Intent intentHome;
    private SlowTask slowTask;
    private Thread thread;
    ProgressDialog dialog;
//    private String getToken() {
//        SharedPreferences prefs;
//        prefs=MainActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
//        String token = prefs.getString("token","");
//        return token;
//    }
//    private void removeToken() {
//        SharedPreferences prefs;
//        SharedPreferences.Editor edit;
//        prefs=MainActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
//        edit=prefs.edit();
//        edit.remove("token");
//        edit.commit();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        buttonDetail = findViewById(R.id.buttonDetailCookGuide);
        buttonCountTime = findViewById(R.id.buttonCountTime);
        textView = findViewById(R.id.textviewTest);
        imageSelected = findViewById(R.id.imageSelected);
        buttonHome = findViewById(R.id.buttonHome);

        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs=MainActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit=prefs.edit();
        String saveToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxdW9jbHV1IiwiaWF0IjoxNjU0NTMyMDQ2LCJleHAiOjE2NTQ2MTg0NDZ9.YqMpazYh4lbJPz6H-xJxjtAKxireAC5fHbNdqwCAQyUo6UUgOmlfBB8HXm_cA_2dXKgPHwR2KVpuvLjvwpytiw";
        edit.putString("token",saveToken);
        String avatar = "/images/avatar/cbebb7189a9e4e939132c97600dd4d3b.png";
        edit.putString("avatar",avatar);
        edit.commit();
//
//
//        textView.setText(getToken());

        buttonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetailCookGuide = new Intent();
                intentDetailCookGuide.setClass(MainActivity.this,DetailCookGuideActivity.class);

                Long foodId = 1L;
                intentDetailCookGuide.putExtra("foodId",foodId);

                startActivity(intentDetailCookGuide);
            }
        });

        buttonCountTime.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
//                Intent intentTimestamp = new Intent();
//                intentTimestamp.setClass(MainActivity.this,Timestamp1Activity.class);
//
//                int time = 30;
//                intentTimestamp.putExtra("time",time);
//
//                startActivity(intentTimestamp);


//                MenuBuilder menuBuilder =new MenuBuilder(MainActivity.this);
//                MenuInflater inflater = new MenuInflater(MainActivity.this);
//                inflater.inflate(R.menu.popup_menu_comment, menuBuilder);
//                MenuPopupHelper optionsMenu = new MenuPopupHelper(MainActivity.this, menuBuilder, buttonCountTime);
//                optionsMenu.setForceShowIcon(true);
//                menuBuilder.setCallback(new MenuBuilder.Callback() {
//                    @Override
//                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
//                        Toast.makeText(MainActivity.this, "You Clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//
//                    @Override
//                    public void onMenuModeChange(@NonNull MenuBuilder menu) {
//
//                    }
//                });
//                optionsMenu.show();

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityIfNeeded(photoPickerIntent, 1);



            }
        });

        intentHome = new Intent();
        intentHome.setClass(MainActivity.this,MainActivity1.class);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intentHome);
                dialog=new ProgressDialog(MainActivity.this);
                dialog.setMessage("Vui lòng chờ...");
                dialog.show();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                            dialog.dismiss();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });


//                slowTask.execute();
            }
        });

    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                String a = imageUri.getPath();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageSelected.setImageBitmap(selectedImage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


    public class SlowTask extends AsyncTask<Void,Void,Void>{

        ProgressDialog dialog;
        Context context;
        Timer timer;

        public SlowTask() {
            super();
        }

        public SlowTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            dialog=new ProgressDialog(context);
            dialog.setMessage("Vui lòng chờ...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Intent intentHome = new Intent();
            intentHome.setClass(MainActivity.this,MainActivity1.class);
            startActivity(intentHome);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            //super.onPostExecute(unused);
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        void waitActivity() {
            if(timer==null){
                timer = new Timer();
            }

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            },500,4000);
        }

    }






}