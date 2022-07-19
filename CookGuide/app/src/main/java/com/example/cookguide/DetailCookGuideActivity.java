package com.example.cookguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.cookguide.adapters.DetailCookGuideAdapter;
import com.example.cookguide.adapters.PhotoAdapter;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.common.Constant;
import com.example.cookguide.models.Food;
import com.example.cookguide.models.Ingredient;
import com.example.cookguide.models.Photo;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCookGuideActivity extends AppCompatActivity {

    final String codeEmojiClapping = "&#128079;";
    final String codeEmojiHeart = "&#10084;";
    final String codeEmojiFace = "&#128523;";

    private Boolean statusBookmarkFood;

    private ProgressBar progressBarLoad;
    private Button buttonLoad;

    private TabLayout tabLayout;
    private ViewPager detailViewPager;

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;

    private ImageButton buttonBackDetail;
    private ImageButton buttonBookmarkFood;

    private TextView textViewNameFood;
    private TextView textViewNumberMaterial;
    private TextView textViewNumberMinutes;
    private TextView textViewLevel;

    private Long foodId;
    private String description;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.detail_cook_guide);

        statusBookmarkFood = true;





        //get data from other activity
        Intent intent = getIntent();
        foodId = intent.getLongExtra("foodId",1L);

        tabLayout = findViewById(R.id.tab_layout_detail_cook_guide);
        detailViewPager = findViewById(R.id.view_pager_detail_cook_guide);
        buttonBackDetail = findViewById(R.id.buttonBackDetail);
        buttonBookmarkFood = findViewById(R.id.buttonBookmarkFood);
        textViewNameFood = findViewById(R.id.textViewNameFood);
        textViewNumberMaterial = findViewById(R.id.textViewNumberMaterial);
        textViewNumberMinutes = findViewById(R.id.textViewNumberMinutes);
        textViewLevel = findViewById(R.id.textViewLevel);



        ApiService.apiService.getDetailCookGuide(getToken(),foodId).enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                Food food = response.body();
                textViewNameFood.setText(String.valueOf(food.getName()));
                textViewNumberMinutes.setText(String.valueOf(food.getTotalTime())+ " phút");

                List<Photo> listPhotoSlider = new ArrayList<>();
                listPhotoSlider.add(new Photo(Constant.DOMAIN_NAME +food.getFoodImage1()));
                listPhotoSlider.add(new Photo(Constant.DOMAIN_NAME+food.getFoodImage2()));
                listPhotoSlider.add(new Photo(Constant.DOMAIN_NAME+food.getFoodImage3()));
                viewPager = findViewById(R.id.viewpager);
                circleIndicator = findViewById(R.id.circle_indicator);

                photoAdapter = new PhotoAdapter(DetailCookGuideActivity.this, listPhotoSlider);
                viewPager.setAdapter(photoAdapter);

                circleIndicator.setViewPager(viewPager);
                photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

                description = food.getDescription();

                if(food.getLevel()==1){
                    textViewLevel.setText("Dễ");
                }else if(food.getLevel()==2){
                    textViewLevel.setText("Trung bình");
                }else {
                    textViewLevel.setText("Khó");
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Call API error", Toast.LENGTH_SHORT).show();
            }
        });

        ApiService.apiService.getIngredient(getToken(),foodId).enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                List<Ingredient> ingredientList = response.body();
                textViewNumberMaterial.setText(String.valueOf(ingredientList.size()));
            }

            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Call API error", Toast.LENGTH_SHORT).show();
            }
        });

        ApiService.apiService.getStatusBookmarkUser(getToken(),foodId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    statusBookmarkFood=true;
                }else {
                    statusBookmarkFood=false;
                }
                if(statusBookmarkFood){
                    buttonBookmarkFood.setImageResource(R.drawable.ic_baseline_bookmarked_24);
                }else{
                    buttonBookmarkFood.setImageResource(R.drawable.ic_baseline_bookmarks_24);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Call API error", Toast.LENGTH_SHORT).show();
            }
        });



        DetailCookGuideAdapter detailCookGuideAdapter = new DetailCookGuideAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        detailViewPager.setAdapter(detailCookGuideAdapter);
        tabLayout.setupWithViewPager(detailViewPager);



        buttonBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonBookmarkFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusBookmarkFood){
                    buttonBookmarkFood.setImageResource(R.drawable.ic_baseline_bookmarks_24);
                    ApiService.apiService.deleteBookmarkUser(getToken(),foodId).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Call API error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    buttonBookmarkFood.setImageResource(R.drawable.ic_baseline_bookmarked_24);
                    ApiService.apiService.addBookmarkUser(getToken(),foodId).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Call API error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                statusBookmarkFood = !statusBookmarkFood;
            }
        });


//        textViewNameFood.setText(getToken());
//        Toast.makeText(getApplicationContext(),getToken(), Toast.LENGTH_SHORT).show();


    }
    private String getToken() {
        SharedPreferences prefs;
        prefs=this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
    public Long getFoodId(){
        return foodId;
    }

    public String getDescription() {
        return description;
    }
}
