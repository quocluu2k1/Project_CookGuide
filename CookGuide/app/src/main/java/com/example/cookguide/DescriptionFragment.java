package com.example.cookguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookguide.api.ApiService;
import com.example.cookguide.models.Food;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button buttonReactFace;
    private Button buttonReactHeart;
    private Button buttonReactClap;
    private Button buttonCommentFood;

    private Boolean statusReactFace;
    private Boolean statusReactHeart;
    private Boolean statusReactClap;

    private int nReactFace;
    private int nReactHeart;
    private int nReactClap;

    private TextView textViewDescription;

    private DetailCookGuideActivity detailCookGuideActivity;



    public DescriptionFragment() {
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
    public static DescriptionFragment newInstance(String param1, String param2) {
        DescriptionFragment fragment = new DescriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_description, container, false);

        detailCookGuideActivity = (DetailCookGuideActivity) getActivity();

        statusReactFace = false;
        statusReactHeart = true;
        statusReactClap = false;

        nReactFace = 2;
        nReactHeart = 7;
        nReactClap = 5;

        buttonCommentFood = view.findViewById(R.id.buttonCommentFood);
        buttonReactFace = view.findViewById(R.id.buttonReactFace);
        buttonReactClap = view.findViewById(R.id.buttonReactClap);
        buttonReactHeart = view.findViewById(R.id.buttonReactHeart);
        textViewDescription = view.findViewById(R.id.textViewDescription);


        ApiService.apiService.getDetailCookGuide(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                Food food = response.body();
                textViewDescription.setText(food.getDescription());
                nReactFace = food.getnSavoring();
                nReactHeart = food.getnHearts();
                nReactClap = food.getnClaps();
                buttonReactFace.setText("\uD83D\uDE0B "+nReactFace);
                buttonReactHeart.setText("❤ "+nReactHeart);
                buttonReactClap.setText("\uD83D\uDC4F "+nReactClap);
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Toast.makeText(getContext(),"Call API error", Toast.LENGTH_SHORT).show();
            }
        });

        ApiService.apiService.getStatusReactSavoring(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                statusReactFace = response.body();
                if(statusReactFace){
                    buttonReactFace.setBackground(getResources().getDrawable(R.drawable.custom_button_reactioned));
                }else{
                    buttonReactFace.setBackground(getResources().getDrawable(R.drawable.custom_button_reaction));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(),"Call API error", Toast.LENGTH_SHORT).show();
            }
        });

        ApiService.apiService.getStatusReactHeart(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                statusReactHeart = response.body();
                if(statusReactHeart){
                    buttonReactHeart.setBackground(getResources().getDrawable(R.drawable.custom_button_reactioned));
                }else{
                    buttonReactHeart.setBackground(getResources().getDrawable(R.drawable.custom_button_reaction));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });


        ApiService.apiService.getStatusReactClap(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                statusReactClap = response.body();
                if(statusReactClap){
                    buttonReactClap.setBackground(getResources().getDrawable(R.drawable.custom_button_reactioned));
                }else{
                    buttonReactClap.setBackground(getResources().getDrawable(R.drawable.custom_button_reaction));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });


        buttonCommentFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCommentFood = new Intent(getActivity(),CommentFoodActivity.class);

                Long foodId = detailCookGuideActivity.getFoodId();
                intentCommentFood.putExtra("foodId",foodId);
                startActivity(intentCommentFood);
            }
        });

        buttonReactFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                buttonReactFace.setBackground(Drawable.createFromPath("#FF0000"));
                if(statusReactFace==true){
//                    statusReactFace = false;
                    buttonReactFace.setBackground(getResources().getDrawable(R.drawable.custom_button_reaction));
                    nReactFace--;
                    ApiService.apiService.deleteReactSavoring(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });

                }else{
                    buttonReactFace.setBackground(getResources().getDrawable(R.drawable.custom_button_reactioned));
                    nReactFace++;
                    ApiService.apiService.addReactSavoring(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }

                buttonReactFace.setText("\uD83D\uDE0B "+nReactFace);
                statusReactFace = !statusReactFace;
            }
        });

        buttonReactHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusReactHeart){
                    buttonReactHeart.setBackground(getResources().getDrawable(R.drawable.custom_button_reaction));
                    nReactHeart--;
                    ApiService.apiService.deleteReactHeart(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }else{
                    buttonReactHeart.setBackground(getResources().getDrawable(R.drawable.custom_button_reactioned));
                    nReactHeart++;
                    ApiService.apiService.addReactHeart(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }
                buttonReactHeart.setText("❤ "+nReactHeart);
                statusReactHeart = !statusReactHeart;
            }
        });

        buttonReactClap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusReactClap==true){
                    buttonReactClap.setBackground(getResources().getDrawable(R.drawable.custom_button_reaction));
                    nReactClap--;
                    ApiService.apiService.deleteReactClap(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });

                }else{
                    buttonReactClap.setBackground(getResources().getDrawable(R.drawable.custom_button_reactioned));
                    nReactClap++;
                    ApiService.apiService.addReactClap(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }

                buttonReactClap.setText("\uD83D\uDC4F "+nReactClap);
                statusReactClap = !statusReactClap;
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
    private String getToken() {
        SharedPreferences prefs;
        prefs=getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
}