package com.example.cookguide.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cookguide.R;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.interfaces.IClickItemHistorySearch;
import com.example.cookguide.models.HistorySearch;

import java.sql.Timestamp;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorySearchAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<HistorySearch> historySearchList;
    IClickItemHistorySearch iClickItemHistorySearch;

    public HistorySearchAdapter(Context context, int layout, List<HistorySearch> historySearchList, IClickItemHistorySearch iClickItemHistorySearch) {
        this.context = context;
        this.layout = layout;
        this.historySearchList = historySearchList;
        this.iClickItemHistorySearch = iClickItemHistorySearch;
    }

    @Override
    public int getCount() {
        return historySearchList.size();
    }

    @Override
    public Object getItem(int i) {
        return historySearchList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        TextView textViewContentHistorySearch = view.findViewById(R.id.textViewContentHistorySearch);
        TextView textViewTimeHistorySearch = view.findViewById(R.id.textViewTimeHistorySearch);
        ImageButton buttonDeleteHistorySearch = view.findViewById(R.id.buttonDeleteHistorySearch);
        LinearLayout layoutContentHistorySearch = view.findViewById(R.id.layoutContentHistorySearch);

        textViewContentHistorySearch.setText(historySearchList.get(i).getContent());
        textViewTimeHistorySearch.setText(convertToStrTimeLine(historySearchList.get(i).getTime()));
        buttonDeleteHistorySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long hisId = historySearchList.get(i).getHisId();
                historySearchList.remove(i);
                HistorySearchAdapter.this.notifyDataSetChanged();
                ApiService.apiService.deleteHistorySearch(getToken(),hisId).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });
        HistorySearch historySearch = historySearchList.get(i);
        layoutContentHistorySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemHistorySearch.onClickItem(historySearch);
            }
        });


        return view;
    }
    protected String convertToStrTimeLine(Timestamp timestamp){
        String strTimeLine = null;
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Long diff = (currentTime.getTime()-timestamp.getTime())/1000;
        if(diff<=0){
            strTimeLine = "Vừa xong";
        }else if(diff<60){
            strTimeLine = diff + " giây trước";
        }else if(diff<3600){
            strTimeLine = diff/60 + " phút trước";
        }else if(diff<86400){
            strTimeLine = diff/3600 + " giờ trước";
        }else if(diff<2592000){
            strTimeLine = diff/86400 + " ngày trước";
        }else if(diff<31536000){
            strTimeLine = diff/2592000 + " tháng trước";
        }else {
            strTimeLine = diff/31536000 + " năm trước";
        }
        return strTimeLine;
    }
    private String getToken() {
        SharedPreferences prefs;
        prefs=context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
}
