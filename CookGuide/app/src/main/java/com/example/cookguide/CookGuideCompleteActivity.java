package com.example.cookguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.common.Constant;
import com.example.cookguide.databinding.ActivityMainBinding;
import com.example.cookguide.models.Food;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CookGuideCompleteActivity extends AppCompatActivity {

    private ImageButton buttonCloseComplete;
    private CircleImageView imageFood;
    private Long foodId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.cook_guide_complete);

        Intent intent = getIntent();
        foodId = intent.getLongExtra("foodId",1L);

        buttonCloseComplete = findViewById(R.id.buttonCloseComplete);
        imageFood = findViewById(R.id.imageFood);

        ApiService.apiService.getDetailCookGuide(getToken(),foodId).enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                Food food = response.body();
                String url = Constant.DOMAIN_NAME +food.getFoodImage1();
//                Glide.with(CookGuideCompleteActivity.this)
//                        .load(url)
//                        .placeholder(R.drawable.image_load)
//                        .error(R.drawable.image_load)
//                        .centerCrop()
//                        .into(imageFood);
                LoadImage loadImage = new LoadImage(imageFood);
                loadImage.execute(url);
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {

            }
        });

        buttonCloseComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private class LoadImage extends AsyncTask<String,Void,Bitmap>{
        ImageView imageView;
        public LoadImage(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private String getToken() {
        SharedPreferences prefs;
        prefs=CookGuideCompleteActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
}
