package com.example.cookguide.profile_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cookguide.DescriptionFragment;
import com.example.cookguide.DetailCookGuideActivity;
import com.example.cookguide.R;
import com.example.cookguide.adapters.BookmarkAdapter;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.interfaces.IClickItemBookmark;
import com.example.cookguide.models.Food;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarkbookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView gvList;
    List<Food> foodList;
    BookmarkAdapter bookmarkAdapter;
    public MarkbookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MarkbookFragment newInstance(String param1, String param2) {
        MarkbookFragment fragment = new MarkbookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View returnView= inflater.inflate(R.layout.fragment_markbook, container, false);
        gvList = (GridView) returnView.findViewById(R.id.gridview);

        ApiService.apiService.getMarkbook(getToken()).enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                foodList = response.body();
                bookmarkAdapter = new BookmarkAdapter(container.getContext(), foodList, new IClickItemBookmark() {
                    @Override
                    public void onClickItem(Food food) {
                        Intent intentDetailCookGuide = new Intent();
                        intentDetailCookGuide.setClass(getContext(), DetailCookGuideActivity.class);

                        Long foodId = food.getFoodId();
                        intentDetailCookGuide.putExtra("foodId",foodId);

                        startActivity(intentDetailCookGuide);
                    }
                });
                gvList.setAdapter(bookmarkAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {
                Toast.makeText(getContext(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return  returnView;

    }

    private String getToken() {
        SharedPreferences prefs;
        prefs=getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
}
