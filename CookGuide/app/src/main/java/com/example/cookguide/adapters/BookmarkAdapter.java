package com.example.cookguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cookguide.R;
import com.example.cookguide.common.Constant;
import com.example.cookguide.interfaces.IClickItemBookmark;
import com.example.cookguide.models.Food;

import java.util.List;

public class BookmarkAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Food> foodList;
    IClickItemBookmark iClickItemBookmark;


    public BookmarkAdapter(Context context, List<Food> foodList, IClickItemBookmark iClickItemBookmark) {
        this.context = context;
        this.foodList = foodList;
        this.iClickItemBookmark = iClickItemBookmark;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class ViewHolder{
        ImageView foodImage1;
        TextView foodName;
        TextView foodTime;
        TextView foodLevel;
        Button reactFace;
        Button reactHeart;
        Button reactClap;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            //view = layoutInflater.inflate(R.layout.bookmark_item,null);
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.bookmark_item,null);
            viewHolder.foodImage1 = view.findViewById(R.id.imageFoodHome);
            viewHolder.foodName = view.findViewById(R.id.textViewNameFoodHome);
            viewHolder.foodTime = view.findViewById(R.id.textViewTotalTimeHome);
            viewHolder.foodLevel = view.findViewById(R.id.textViewLevelHome);
            viewHolder.reactFace = view.findViewById(R.id.buttonReactFaceHome);
            viewHolder.reactHeart = view.findViewById(R.id.buttonReactHeartHome);
            viewHolder.reactClap = view.findViewById(R.id.buttonReactClapHome);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Food food = this.foodList.get(i);
        viewHolder.foodName.setText(food.getName());
        viewHolder.reactFace.setText("\uD83D\uDE0B "+food.getnSavoring());
        viewHolder.reactHeart.setText("❤ "+food.getnHearts());
        viewHolder.reactClap.setText("\uD83D\uDC4F "+food.getnClaps());
        if(food.getLevel()==1){
            viewHolder.foodLevel.setText("Dễ");
        }else if(food.getLevel()==2){
            viewHolder.foodLevel.setText("Trung bình");
        }else {
            viewHolder.foodLevel.setText("Khó");
        }
        viewHolder.foodTime.setText(food.getTotalTime()+" phút");

        String url = Constant.DOMAIN_NAME+food.getFoodImage1();
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.image_load)
                .error(R.drawable.image_load)
                .centerCrop()
                .into(viewHolder.foodImage1);

        viewHolder.foodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemBookmark.onClickItem(food);
            }
        });

        return view;
    }
}
