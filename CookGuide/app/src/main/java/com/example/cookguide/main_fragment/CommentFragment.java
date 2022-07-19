package com.example.cookguide.main_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookguide.R;
import com.example.cookguide.adapters.HistoryCommentAdapter;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.models.HistoryComment;
import com.example.cookguide.models.HistoryCommentDetail;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {

    private TextView textViewNameFood,textViewNameFood1,textViewNameFood2;
    private LinearLayout layoutRoot;
    private HistoryCommentAdapter historyCommentAdapter;
    List<HistoryComment> historyCommentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment,container,false);

        layoutRoot = view.findViewById(R.id.layoutRoot);

//        List<HistoryComment> historyCommentList = new ArrayList<>();
//        HistoryComment historyComment = new HistoryComment();
//        Date date = Date.valueOf("2022-05-22");
//        historyComment.setDate(date);
//        List<HistoryCommentDetail> historyCommentDetailList = new ArrayList<>();
//
//        HistoryCommentDetail historyCommentDetail = new HistoryCommentDetail();
//        historyCommentDetail.setCmtId(1L);
//        historyCommentDetail.setFoodId(1L);
//        historyCommentDetail.setNameFood("Mon an 1");
//        historyCommentDetail.setContentCmt("content comment");
//        historyCommentDetail.setImageCmt("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
//        Timestamp timestamp = Timestamp.valueOf("2022-05-22 11:23:47");
//        historyCommentDetail.setTimeCmt(timestamp);
//        historyCommentDetailList.add(historyCommentDetail);
//
//        HistoryCommentDetail historyCommentDetail1 = new HistoryCommentDetail();
//        historyCommentDetail1.setCmtId(2L);
//        historyCommentDetail1.setFoodId(2L);
//        historyCommentDetail1.setNameFood("Mon an 2");
//        historyCommentDetail1.setContentCmt("content comment 2311");
//        historyCommentDetail1.setImageCmt("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
//        Timestamp timestamp1 = Timestamp.valueOf("2022-05-22 13:27:27");
//        historyCommentDetail1.setTimeCmt(timestamp1);
//        historyCommentDetailList.add(historyCommentDetail1);
//        historyComment.setList(historyCommentDetailList);
//
//        historyCommentList.add(historyComment);
//
//
//
//        HistoryComment historyComment1 = new HistoryComment();
//        Date date1 = Date.valueOf("2022-05-20");
//        historyComment1.setDate(date1);
//        List<HistoryCommentDetail> historyCommentDetailList1 = new ArrayList<>();
//
//        HistoryCommentDetail historyCommentDetail3 = new HistoryCommentDetail();
//        historyCommentDetail3.setCmtId(3L);
//        historyCommentDetail3.setFoodId(3L);
//        historyCommentDetail3.setNameFood("Mon an 3");
//        historyCommentDetail3.setContentCmt("content comment sfd");
//        historyCommentDetail3.setImageCmt("https://beptueu.vn/hinhanh/tintuc/top-15-hinh-anh-mon-an-ngon-viet-nam-khien-ban-khong-the-roi-mat-1.jpg");
//        Timestamp timestamp2 = Timestamp.valueOf("2022-05-20 11:23:47");
//        historyCommentDetail3.setTimeCmt(timestamp2);
//        historyCommentDetailList1.add(historyCommentDetail3);
//        historyComment1.setList(historyCommentDetailList1);
//
//        historyCommentList.add(historyComment1);


        historyCommentList = new ArrayList<>();
        ApiService.apiService.getHistoryComment(getToken()).enqueue(new Callback<List<HistoryComment>>() {
            @Override
            public void onResponse(Call<List<HistoryComment>> call, Response<List<HistoryComment>> response) {
                historyCommentList = response.body();
                List<HistoryCommentDetail> list = new ArrayList<>();
                for(int i=0;i<historyCommentList.size();i++){
                    TextView textViewHistoryComment = new TextView(getContext());
                    textViewHistoryComment.setTextAppearance(R.style.fontForTextDateHistoryComment);
                    textViewHistoryComment.setPadding(0,40,0,0);

                    RecyclerView recyclerView = new RecyclerView(getContext());
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(historyCommentList.get(i).getDate());
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    textViewHistoryComment.setText(day+" tháng "+month+", "+year);

                    layoutRoot.addView(textViewHistoryComment);
                    list = historyCommentList.get(i).getList();

                    historyCommentAdapter = new HistoryCommentAdapter(view.getContext(),historyCommentList.get(i).getList());
                    recyclerView.setAdapter(historyCommentAdapter);

                    layoutRoot.addView(recyclerView);

                }
            }

            @Override
            public void onFailure(Call<List<HistoryComment>> call, Throwable t) {
                Toast.makeText(getContext(),"Call API error",Toast.LENGTH_SHORT).show();
            }
        });





//        View itemView = inflater.inflate(R.layout.item_food_home,container,false);


        return view;
    }

    private String getToken() {
        SharedPreferences prefs;
        prefs=getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
}
