package com.example.cookguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookguide.adapters.ImplementationAdapter;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.models.Implementation;
import com.example.cookguide.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImplementationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImplementationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button buttonCountTime;
    private Button buttonSuccessFood;
    private Button buttonNextStep;
    private ListView listViewImplementation;
    private List<Implementation> implementationArrayList;
    int stepsProgress;
    private ImplementationAdapter implementationAdapter;

    private DetailCookGuideActivity detailCookGuideActivity;

    public ImplementationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImplementationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImplementationFragment newInstance(String param1, String param2) {
        ImplementationFragment fragment = new ImplementationFragment();
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
        View view = inflater.inflate(R.layout.fragment_implementation, container, false);
        detailCookGuideActivity = (DetailCookGuideActivity) getActivity();

        buttonCountTime = view.findViewById(R.id.buttonCountTime);
        buttonSuccessFood = view.findViewById(R.id.buttonSuccessFood);
        buttonNextStep = view.findViewById(R.id.buttonNextStep);

        stepsProgress=0;

        listViewImplementation = (ListView) view.findViewById(R.id.listViewImplementation);

        implementationArrayList = new ArrayList<Implementation>();

        ApiService.apiService.getImplementation(getToken(),detailCookGuideActivity.getFoodId()).enqueue(new Callback<List<Implementation>>() {
            @Override
            public void onResponse(Call<List<Implementation>> call, Response<List<Implementation>> response) {
                implementationArrayList = response.body();

                implementationAdapter = new ImplementationAdapter(view.getContext(), R.layout.item_implementation,implementationArrayList);
                listViewImplementation.setAdapter(implementationAdapter);
                if(implementationArrayList.size()>1){
                    buttonSuccessFood.setVisibility(View.GONE);
                }else{
                    buttonNextStep.setVisibility(View.GONE);
                    buttonSuccessFood.setVisibility(View.VISIBLE);
                }
                if(implementationArrayList.get(0).time==0){
                    buttonCountTime.setVisibility(View.GONE);
                }else{
                    buttonCountTime.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Implementation>> call, Throwable t) {
                Toast.makeText(getContext(),"Call API error", Toast.LENGTH_SHORT).show();
            }
        });

//        implementationArrayList.add(new Implementation(1,"abc1","null",300));
//        implementationArrayList.add(new Implementation(2,"abc2","null",0));
//        implementationArrayList.add(new Implementation(3,"abc3","null",500));




        buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepsProgress++;
                if(stepsProgress==(implementationArrayList.size()-1)){
                    buttonNextStep.setVisibility(View.GONE);
                    buttonSuccessFood.setVisibility(View.VISIBLE);
                }
                if(implementationArrayList.get(stepsProgress).time==0){
                    buttonCountTime.setVisibility(View.GONE);
                }else{
                    buttonCountTime.setVisibility(View.VISIBLE);
                }
                implementationAdapter.setFlag(stepsProgress-1);
                implementationAdapter.notifyDataSetChanged();
                listViewImplementation.setSelection(stepsProgress);
                
            }
        });

        buttonCountTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTimestamp = new Intent(getActivity(),Timestamp1Activity.class);
                //intentDetailCookGuide.setClass(DetailCookGuideActivity.this,TimestampActivity.class);

                int time = implementationArrayList.get(stepsProgress).time;
                String stepGuide = implementationArrayList.get(stepsProgress).impDetail;
                intentTimestamp.putExtra("time",time);
                intentTimestamp.putExtra("stepGuide",stepGuide);

                startActivity(intentTimestamp);
            }
        });

        buttonSuccessFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSuccessFood = new Intent(getActivity(),CookGuideCompleteActivity.class);

                Long foodId = detailCookGuideActivity.getFoodId();
                intentSuccessFood.putExtra("foodId",foodId);

                startActivity(intentSuccessFood);
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