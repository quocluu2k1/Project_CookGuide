package com.example.cookguide.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookguide.R;
import com.example.cookguide.common.Constant;
import com.example.cookguide.interfaces.IClickItemHome;
import com.example.cookguide.models.Food;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class FoodHomeAdapter extends RecyclerView.Adapter<FoodHomeAdapter.ViewHolder> {

    List<Food> foodList;
    Context context;
    IClickItemHome iClickItemHome;

    public FoodHomeAdapter(List<Food> foodList, Context context, IClickItemHome iClickItemHome) {
        this.foodList = foodList;
        this.context = context;
        this.iClickItemHome = iClickItemHome;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_food_home,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set data
        String url = Constant.DOMAIN_NAME+foodList.get(position).getFoodImage1();
//        Glide.with(holder.itemView.getContext())
//                .load(url)
//                .placeholder(R.drawable.image_load)
//                .error(R.drawable.image_load)
//                .centerCrop()
//                .into(holder.imageFoodHome);
        LoadImage loadImage = new LoadImage(holder.imageFoodHome);
        loadImage.execute(url);
        holder.textViewNameFoodHome.setText(foodList.get(position).getName());
        holder.textViewTotalTimeHome.setText(foodList.get(position).getTotalTime()+" phút");
        if(foodList.get(position).getLevel()==1){
            holder.textViewLevelHome.setText("Dễ");
        }else if(foodList.get(position).getLevel()==2){
            holder.textViewLevelHome.setText("Trung bình");
        }else{
            holder.textViewLevelHome.setText("Khó");
        }
        holder.buttonReactFaceHome.setText("\uD83D\uDE0B "+foodList.get(position).getnSavoring());
        holder.buttonReactHeartHome.setText("❤ "+foodList.get(position).getnHearts());
        holder.buttonReactClapHome.setText("\uD83D\uDC4F "+foodList.get(position).getnClaps());

        Food food = foodList.get(position);
        holder.textViewNameFoodHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemHome.onClickItem(food);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView imageFoodHome;
        TextView textViewNameFoodHome, textViewTotalTimeHome, textViewLevelHome;
        Button buttonReactFaceHome, buttonReactHeartHome, buttonReactClapHome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFoodHome = itemView.findViewById(R.id.imageFoodHome);
            textViewNameFoodHome = itemView.findViewById(R.id.textViewNameFoodHome);
            textViewTotalTimeHome = itemView.findViewById(R.id.textViewTotalTimeHome);
            textViewLevelHome = itemView.findViewById(R.id.textViewLevelHome);
            buttonReactFaceHome = itemView.findViewById(R.id.buttonReactFaceHome);
            buttonReactHeartHome = itemView.findViewById(R.id.buttonReactHeartHome);
            buttonReactClapHome = itemView.findViewById(R.id.buttonReactClapHome);
        }
    }

    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
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
}
