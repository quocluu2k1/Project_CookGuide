package com.example.cookguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.example.cookguide.adapters.ViewPagerMainAdapter;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.models.UserResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity1 extends AppCompatActivity {
    private BottomNavigationView bottomNavBarMain;
    private ViewPager viewPagerMain;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main1);
//        dialog=new ProgressDialog(this);
//        dialog.setMessage("please wait...");
//        dialog.show();

        bottomNavBarMain = findViewById(R.id.bottomNavBarMain);
        viewPagerMain = findViewById(R.id.viewpagerMain);

        setUpViewPager();

        bottomNavBarMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        viewPagerMain.setCurrentItem(0);
                        break;
                    case R.id.action_search:
                        viewPagerMain.setCurrentItem(1);
                        break;
                    case R.id.action_comment:
                        viewPagerMain.setCurrentItem(2);
                        break;
                    case R.id.action_profile:
                        viewPagerMain.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

        if(getToken()!=null){
            ApiService.apiService.getProfile(getToken()).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserResponse userResponse = response.body();
                    SharedPreferences prefs;
                    SharedPreferences.Editor edit;
                    prefs=MainActivity1.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    edit=prefs.edit();
                    String saveFullname = userResponse.getFullName();
                    edit.putString("Fullname",saveFullname);
                    String saveUsername = userResponse.getUsername();
                    edit.putString("username",saveUsername);
                    String savePhone = userResponse.getPhone();
                    edit.putString("Phone",savePhone);
                    String saveEmail = userResponse.getEmail();
                    edit.putString("Email",saveEmail);
                    String saveAvatar = userResponse.getAvatar();
                    edit.putString("Avatar",saveAvatar);
                    edit.commit();
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {

                }
            });
        }

        //Disable swipe pager
        viewPagerMain.beginFakeDrag();
//        dialog.dismiss();
    }

    private void setUpViewPager() {
        ViewPagerMainAdapter viewPagerMainAdapter = new ViewPagerMainAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerMain.setAdapter(viewPagerMainAdapter);
    }
    private String getToken() {
        SharedPreferences prefs;
        prefs=MainActivity1.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
}