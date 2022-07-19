package com.example.cookguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookguide.DetailCookGuideActivity;
import com.example.cookguide.R;
import com.example.cookguide.common.Constant;
import com.example.cookguide.models.Food;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ResultSearchAdapter extends RecyclerView.Adapter<ResultSearchAdapter.ViewHolder>{

    List<Food> foodList;
    Context context;

    public ResultSearchAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_result_search,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewNameFoodResult.setText(foodList.get(position).getName());
        holder.textViewTotalTimeResult.setText(foodList.get(position).getTotalTime()+" phút");
        if(foodList.get(position).getLevel()==1){
            holder.textViewLevelResult.setText("Dễ");
        }else if(foodList.get(position).getLevel()==2) {
            holder.textViewLevelResult.setText("Trung bình");
        }else {
            holder.textViewLevelResult.setText("Khó");
        }

        String url = Constant.DOMAIN_NAME+foodList.get(position).getFoodImage1();
        Glide.with(holder.itemView.getContext())
                .load(url)
                .placeholder(R.drawable.image_load)
                .error(R.drawable.image_load)
                .centerCrop()
                .into(holder.imageFoodResult);
        int i = position;
        Food food = foodList.get(i);
        holder.layoutItemResultSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetailCookGuide = new Intent();
                intentDetailCookGuide.setClass(context, DetailCookGuideActivity.class);
                Long foodId = food.getFoodId();
                intentDetailCookGuide.putExtra("foodId",foodId);

                context.startActivity(intentDetailCookGuide);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView imageFoodResult;
        TextView textViewNameFoodResult, textViewTotalTimeResult, textViewLevelResult;
        LinearLayout layoutItemResultSearch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFoodResult = itemView.findViewById(R.id.imageFoodResult);
            textViewNameFoodResult = itemView.findViewById(R.id.textViewNameFoodResult);
            textViewTotalTimeResult = itemView.findViewById(R.id.textViewTotalTimeResult);
            textViewLevelResult = itemView.findViewById(R.id.textViewLevelResult);
            layoutItemResultSearch = itemView.findViewById(R.id.layoutItemResultSearch);
        }
    }
}
