package com.example.cookguide.main_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookguide.R;
import com.example.cookguide.adapters.HistorySearchAdapter;
import com.example.cookguide.adapters.ResultSearchAdapter;
import com.example.cookguide.adapters.SuggestionSearchAdapter;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.interfaces.IClickItemHistorySearch;
import com.example.cookguide.interfaces.IClickItemSuggestionSearch;
import com.example.cookguide.models.Food;
import com.example.cookguide.models.HistorySearch;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private View viewSearch;
    private LinearLayout layoutHistorySearch,layoutMainSearch, layoutFilterSearch;
    private ScrollView layoutDefaultSearch;
    private ImageButton buttonBackSearch, buttonFilterSearch;

    private ListView listViewHistorySearch;
    private HistorySearchAdapter historySearchAdapter;

    private RecyclerView recyclerViewSuggestionSearch, recyclerViewSuggestionFood, recyclerViewResultSearch;
    private SuggestionSearchAdapter suggestionSearchAdapter;
    private ResultSearchAdapter suggestionFoodAdapter, resultSearchAdapter;

    private RadioGroup radioGroupFilterSearch;
    private RadioButton buttonSelectNameFood, buttonSelectMaterial, buttonSelectDescription;
    private TextView textViewContentSearch;

    private Boolean statusFilterSearch;
    private Animation animation;

    List<HistorySearch> suggestionSearchList;
    List<Food> suggestionFoodList;
    List<HistorySearch> historySearchList;
    List<Food> resultSearchFoodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        searchView = view.findViewById(R.id.searchViewFood);
        layoutHistorySearch = view.findViewById(R.id.layoutHistorySearch);
        layoutMainSearch = view.findViewById(R.id.layoutMainSearch);
        viewSearch = view.findViewById(R.id.root_layout);
        buttonBackSearch = view.findViewById(R.id.buttonBackSearch);
        buttonFilterSearch = view.findViewById(R.id.buttonFilterSearch);
        layoutFilterSearch = view.findViewById(R.id.layoutFilterSearch);
        listViewHistorySearch = view.findViewById(R.id.listViewHistorySearch);
        recyclerViewSuggestionSearch = view.findViewById(R.id.recyclerViewSuggestionSearch);
        layoutDefaultSearch = view.findViewById(R.id.layoutDefaultSearch);
        radioGroupFilterSearch = view.findViewById(R.id.radioGroupFilterSearch);
        buttonSelectNameFood = view.findViewById(R.id.buttonSelectNameFood);
        buttonSelectMaterial = view.findViewById(R.id.buttonSelectMaterial);
        buttonSelectDescription = view.findViewById(R.id.buttonSelectDescription);
        recyclerViewSuggestionFood = view.findViewById(R.id.recyclerViewSuggestionFood);
        recyclerViewResultSearch = view.findViewById(R.id.recyclerViewResultSearch);
        textViewContentSearch = view.findViewById(R.id.textViewContentSearch);

        statusFilterSearch = false;


        resultSearchFoodList = new ArrayList<>();
        recyclerViewResultSearch.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerResultSearch = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewResultSearch.setLayoutManager(linearLayoutManagerResultSearch);



        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    layoutHistorySearch.setVisibility(View.VISIBLE);
                    layoutMainSearch.setVisibility(View.GONE);
                    buttonBackSearch.setVisibility(View.VISIBLE);
                }

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                HistorySearch historySearchCurrent = new HistorySearch();
                historySearchCurrent.setContent(s);
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                historySearchCurrent.setTime(currentTime);
                if(radioGroupFilterSearch.getCheckedRadioButtonId()==R.id.buttonSelectNameFood){
                    historySearchCurrent.setType(1);
                    historySearchList.add(0,historySearchCurrent);
                    historySearchAdapter.notifyDataSetChanged();
                    ApiService.apiService.searchByNameFood(getToken(),1,s).enqueue(new Callback<List<Food>>() {
                        @Override
                        public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                            resultSearchFoodList = response.body();
                            resultSearchAdapter = new ResultSearchAdapter(resultSearchFoodList, getContext());
                            recyclerViewResultSearch.setAdapter(resultSearchAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<Food>> call, Throwable t) {
                            Toast.makeText(getContext(),"Call API error",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(radioGroupFilterSearch.getCheckedRadioButtonId()==R.id.buttonSelectMaterial){
                    historySearchCurrent.setType(2);
                    historySearchList.add(0,historySearchCurrent);
                    historySearchAdapter.notifyDataSetChanged();
                    ApiService.apiService.searchByNameMaterial(getToken(),1,s).enqueue(new Callback<List<Food>>() {
                        @Override
                        public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                            resultSearchFoodList = response.body();
                            resultSearchAdapter = new ResultSearchAdapter(resultSearchFoodList, getContext());
                            recyclerViewResultSearch.setAdapter(resultSearchAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<Food>> call, Throwable t) {
                            Toast.makeText(getContext(),"Call API error",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    historySearchCurrent.setType(3);
                    historySearchList.add(0,historySearchCurrent);
                    historySearchAdapter.notifyDataSetChanged();
                    ApiService.apiService.searchByDescription(getToken(),1,s).enqueue(new Callback<List<Food>>() {
                        @Override
                        public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                            resultSearchFoodList = response.body();
                            resultSearchAdapter = new ResultSearchAdapter(resultSearchFoodList, getContext());
                            recyclerViewResultSearch.setAdapter(resultSearchAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<Food>> call, Throwable t) {
                            Toast.makeText(getContext(),"Call API error",Toast.LENGTH_SHORT).show();
                        }
                    });
                }



                layoutHistorySearch.setVisibility(View.GONE);
                layoutMainSearch.setVisibility(View.VISIBLE);
                buttonBackSearch.setVisibility(View.GONE);
                layoutDefaultSearch.setVisibility(View.GONE);
                String contentSearch = "<i>Kết quả tìm kiếm cho: </i>"+"<b>"+searchView.getQuery()+"</b>";
                textViewContentSearch.setText(Html.fromHtml(contentSearch));
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        buttonBackSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutHistorySearch.setVisibility(View.GONE);
                layoutMainSearch.setVisibility(View.GONE);
                buttonBackSearch.setVisibility(View.GONE);
                layoutDefaultSearch.setVisibility(View.VISIBLE);
                searchView.clearFocus();
            }
        });



        buttonFilterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusFilterSearch){
                    layoutFilterSearch.setVisibility(View.GONE);
                }else{
                    layoutFilterSearch.setVisibility(View.VISIBLE);
                    animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_slide_down_filter);
                    animation.setDuration(200);
                    layoutFilterSearch.setAnimation(animation);
                    layoutFilterSearch.animate();
                    animation.start();
                }
                statusFilterSearch = !statusFilterSearch;
            }
        });


        //History Search

        historySearchList = new ArrayList<>();
        ApiService.apiService.getHistorySearch(getToken()).enqueue(new Callback<List<HistorySearch>>() {
            @Override
            public void onResponse(Call<List<HistorySearch>> call, Response<List<HistorySearch>> response) {
                historySearchList = response.body();
                historySearchAdapter = new HistorySearchAdapter(view.getContext(), R.layout.item_history_search, historySearchList, new IClickItemHistorySearch() {
                    @Override
                    public void onClickItem(HistorySearch historySearch) {
                        if(historySearch.getType()==1){
                            buttonSelectNameFood.setChecked(true);
                        }else if(historySearch.getType()==2){
                            buttonSelectMaterial.setChecked(true);
                        }else {
                            buttonSelectDescription.setChecked(true);
                        }
                        searchView.setQuery(historySearch.getContent(),true);
                    }
                });
                listViewHistorySearch.setAdapter(historySearchAdapter);
            }

            @Override
            public void onFailure(Call<List<HistorySearch>> call, Throwable t) {
                Toast.makeText(getContext(),"Call API error",Toast.LENGTH_SHORT).show();
            }
        });

//        Timestamp timestamp1 = Timestamp.valueOf("2022-06-05 23:09:10");
//        HistorySearch historySearch1 = new HistorySearch(1L,"Tim mon an 1",timestamp1,1,true);
//        Timestamp timestamp2 = Timestamp.valueOf("2022-05-29 23:09:10");
//        HistorySearch historySearch2 = new HistorySearch(1L,"Tim mon an 2",timestamp2,2,true);
//        Timestamp timestamp3 = Timestamp.valueOf("2022-05-17 23:09:10");
//        HistorySearch historySearch3 = new HistorySearch(1L,"Tim mon an 3",timestamp3,3,true);
//        historySearchList.add(historySearch1);
//        historySearchList.add(historySearch2);
//        historySearchList.add(historySearch3);






        //set Suggestion Search
        recyclerViewSuggestionSearch.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerSuggestionSearch = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewSuggestionSearch.setLayoutManager(linearLayoutManagerSuggestionSearch);
        suggestionSearchList = new ArrayList<>();
        ApiService.apiService.getSuggestionSearch(getToken()).enqueue(new Callback<List<HistorySearch>>() {
            @Override
            public void onResponse(Call<List<HistorySearch>> call, Response<List<HistorySearch>> response) {
                suggestionSearchList = response.body();
                suggestionSearchAdapter = new SuggestionSearchAdapter(suggestionSearchList, getContext(), new IClickItemSuggestionSearch() {
                    @Override
                    public void onClickItem(HistorySearch historySearch) {
                        if(historySearch.getType()==1){
                            buttonSelectNameFood.setChecked(true);
                        }else if(historySearch.getType()==2){
                            buttonSelectMaterial.setChecked(true);
                        }else {
                            buttonSelectDescription.setChecked(true);
                        }
                        searchView.setQuery(historySearch.getContent(),true);
                    }
                });
                recyclerViewSuggestionSearch.setAdapter(suggestionSearchAdapter);
            }

            @Override
            public void onFailure(Call<List<HistorySearch>> call, Throwable t) {
                Toast.makeText(getContext(),"Call API error",Toast.LENGTH_SHORT).show();
            }
        });


        //set Suggestion Food
        recyclerViewSuggestionFood.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerSuggestionFood = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewSuggestionFood.setLayoutManager(linearLayoutManagerSuggestionFood);
        suggestionFoodList = new ArrayList<>();
        ApiService.apiService.getSuggestionFood(getToken()).enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                suggestionFoodList = response.body();
                suggestionFoodAdapter = new ResultSearchAdapter(suggestionFoodList, getContext());
                recyclerViewSuggestionFood.setAdapter(suggestionFoodAdapter);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(getContext(),"Call API error",Toast.LENGTH_SHORT).show();
            }
        });
//
//        List<Food> foodList = new ArrayList<>();
//        Food food = new Food();
//        food.setName("Mon an 1");
//        food.setTotalTime(90);
//        food.setLevel(2);
//        food.setnClaps(2);
//        food.setnHearts(3);
//        food.setnSavoring(6);
//        food.setFoodImage1("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
//        foodList.add(food);
//
//        Food food1 = new Food();
//        food1.setName("Mon an 2");
//        food1.setTotalTime(80);
//        food1.setLevel(1);
//        food1.setnClaps(2);
//        food1.setnHearts(3);
//        food1.setnSavoring(6);
//        food1.setFoodImage1("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
//        foodList.add(food1);
//
//        Food food2 = new Food();
//        food2.setName("Mon an 3");
//        food2.setTotalTime(120);
//        food2.setLevel(3);
//        food2.setnClaps(0);
//        food2.setnHearts(9);
//        food2.setnSavoring(1);
//        food2.setFoodImage1("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
//        foodList.add(food2);
//
//        Food food3 = new Food();
//        food3.setName("Mon an 4");
//        food3.setTotalTime(30);
//        food3.setLevel(1);
//        food3.setnClaps(2);
//        food3.setnHearts(3);
//        food3.setnSavoring(6);
//        food3.setFoodImage1("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
//        foodList.add(food3);
//
//        Food food4 = new Food();
//        food4.setName("Mon an 5");
//        food4.setTotalTime(100);
//        food4.setLevel(2);
//        food4.setnClaps(2);
//        food4.setnHearts(2);
//        food4.setnSavoring(6);
//        food4.setFoodImage1("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
//        foodList.add(food4);




        //set Result Search



        return view;
    }

    private String getToken() {
        SharedPreferences prefs;
        prefs=getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
}
