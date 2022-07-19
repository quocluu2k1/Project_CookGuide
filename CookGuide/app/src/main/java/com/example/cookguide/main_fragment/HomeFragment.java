package com.example.cookguide.main_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.cookguide.DetailCookGuideActivity;
import com.example.cookguide.MainActivity;
import com.example.cookguide.R;
import com.example.cookguide.adapters.FoodHomeAdapter;
import com.example.cookguide.adapters.PhotoAdapter;
import com.example.cookguide.adapters.SliderHomeAdapter;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.interfaces.IClickItemHome;
import com.example.cookguide.models.Food;
import com.example.cookguide.models.Photo;
import com.example.cookguide.models.PhotoSliderHome;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private SliderHomeAdapter sliderHomeAdapter;
    private List<PhotoSliderHome> photoSliderHomeList;
    private Timer timer;
    private RecyclerView recyclerViewOutstandingFood, recyclerViewNewFood, recyclerViewFavoriteFood;
    private FoodHomeAdapter outstandingFoodAdapter, newFoodAdapter, favoriteFoodAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        recyclerViewOutstandingFood = view.findViewById(R.id.recyclerViewOutstandingFood);
        recyclerViewNewFood = view.findViewById(R.id.recyclerViewNewFood);
        recyclerViewFavoriteFood = view.findViewById(R.id.recyclerViewFavoriteFood);

        photoSliderHomeList = new ArrayList<>();
        photoSliderHomeList.add(new PhotoSliderHome(R.drawable.slide1));
        photoSliderHomeList.add(new PhotoSliderHome(R.drawable.slide2));
        photoSliderHomeList.add(new PhotoSliderHome(R.drawable.slide3));

        sliderHomeAdapter = new SliderHomeAdapter(getContext(),photoSliderHomeList);
        viewPager.setAdapter(sliderHomeAdapter);

        circleIndicator.setViewPager(viewPager);
        sliderHomeAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSliderImage();


        //Outstanding Food
        recyclerViewOutstandingFood.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewOutstandingFood.setLayoutManager(linearLayoutManager);

        ApiService.apiService.getOutstandingFood().enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                List<Food> foodOutstandingList = response.body();
                outstandingFoodAdapter = new FoodHomeAdapter(foodOutstandingList, getContext(), new IClickItemHome() {
                    @Override
                    public void onClickItem(Food food) {
                        if(food.getFoodId()!=null){
                            //Toast.makeText(getContext(),food.getFoodId().toString(),Toast.LENGTH_SHORT).show();
                            Intent intentDetailCookGuide = new Intent();
                            intentDetailCookGuide.setClass(getContext(),DetailCookGuideActivity.class);

                            Long foodId = food.getFoodId();
                            intentDetailCookGuide.putExtra("foodId",foodId);

                            startActivity(intentDetailCookGuide);
                        }
                    }
                });
                recyclerViewOutstandingFood.setAdapter(outstandingFoodAdapter);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(getContext(),"call api error",Toast.LENGTH_SHORT).show();
            }
        });




        //New Food
        recyclerViewNewFood.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerNewFood = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewNewFood.setLayoutManager(linearLayoutManagerNewFood);

        ApiService.apiService.getNewFood().enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                List<Food> foodNewList = response.body();
                newFoodAdapter = new FoodHomeAdapter(foodNewList, getContext(), new IClickItemHome() {
                    @Override
                    public void onClickItem(Food food) {
                        Intent intentDetailCookGuide = new Intent();
                        intentDetailCookGuide.setClass(getContext(),DetailCookGuideActivity.class);

                        Long foodId = food.getFoodId();
                        intentDetailCookGuide.putExtra("foodId",foodId);

                        startActivity(intentDetailCookGuide);
                    }
                });
                recyclerViewNewFood.setAdapter(newFoodAdapter);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(getContext(),"call api error",Toast.LENGTH_SHORT).show();
            }
        });




        //Favorite Food
        recyclerViewFavoriteFood.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerFavoriteFood = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewFavoriteFood.setLayoutManager(linearLayoutManagerFavoriteFood);

        ApiService.apiService.getFavoriteFood().enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                List<Food> foodFavoriteList = response.body();
                favoriteFoodAdapter = new FoodHomeAdapter(foodFavoriteList, getContext(), new IClickItemHome() {
                    @Override
                    public void onClickItem(Food food) {
                        Intent intentDetailCookGuide = new Intent();
                        intentDetailCookGuide.setClass(getContext(),DetailCookGuideActivity.class);

                        Long foodId = food.getFoodId();
                        intentDetailCookGuide.putExtra("foodId",foodId);

                        startActivity(intentDetailCookGuide);
                    }
                });
                recyclerViewFavoriteFood.setAdapter(favoriteFoodAdapter);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(getContext(),"call api error",Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

    private void autoSliderImage() {
        if(photoSliderHomeList == null || photoSliderHomeList.isEmpty() || viewPager == null){
            return;
        }
        if(timer==null){
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = photoSliderHomeList.size() - 1;
                        if(currentItem<totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,4000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
    }
}
